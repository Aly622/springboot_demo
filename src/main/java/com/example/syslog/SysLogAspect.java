/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.syslog;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.common.IResponseCode;
import com.example.common.ServiceException;
import com.example.common.SpringAppContextUtil;
import com.example.syslog.event.SysLogEvent;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Objects;

/*
 * @Author Oliver.Liu
 * @Desc //操作日志异步入库  环绕通知
 * @Date 2019/6/10 13:44
 * @Param
 * @return
 **/
@Aspect
public class SysLogAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    //使用切点增强的时机注解:@Before,@Around,@AfterReturning,@AfterThrowing,@After
    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String exceptionStr = null;
        Object obj = null;
        LogBo logVo = null;
        Long startTime = null;
        try {
            String strClassName = point.getTarget().getClass().getName();
            String strMethodName = point.getSignature().getName();
            log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
            HttpServletRequest request = ((ServletRequestAttributes) Objects
                    .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            logVo = new LogBo();
            logVo.setServiceType(URLUtil.getPath(request.getRequestURI()));
            logVo.setRequestParam(HttpUtil.toParams(request.getParameterMap()));
            logVo.setRequestId(request.getHeader("requestId"));

            logVo.setServiceId(
                    String.format("%s-%s", SpringAppContextUtil.getApplicationContext().getApplicationName(), sysLog.value()));
            if(null == logVo.getRequestParam()) {
                request = ((ServletRequestAttributes) Objects
                        .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                if(!request.getRequestURI().contains("download")) {
                    logVo.setRequestParam(JSONObject.toJSONString(point.getArgs()));
                }
                if(null != request.getHeader("requestId")) {
                    logVo.setRequestId(request.getHeader("requestId"));
                }
            }
            logVo.setRequestDateTime(LocalDateTime.now());
            startTime = System.currentTimeMillis();
            obj = point.proceed();
        } catch (ServiceException e) {
            exceptionStr = getStackTrace(e);
            IResponseCode errorCode = ((ServiceException) e).getResponseCode();
            throw new ServiceException(errorCode.getCode(), errorCode.getMessage());
        } catch (Exception e) {
            exceptionStr = getStackTrace(e);
            throw new ServiceException(-1, e.getMessage());
        } finally {
            Long endTime = System.currentTimeMillis();
            log.info("[对象信息]:{}", JSONObject.toJSONString(logVo));
            logVo.setResponseResult(String.format("[执行时间]：%s %s", Convert.toStr(endTime - startTime), obj != null?obj.toString():exceptionStr));
            // 发送异步日志事件
            SpringAppContextUtil.publishEvent(new SysLogEvent(logVo));
        }
        return obj;
    }

    private String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try
        {
            throwable.printStackTrace(pw);
            return sw.toString().substring(0, 1536);
        }
        finally
        {
            pw.close();
        }
    }
}
