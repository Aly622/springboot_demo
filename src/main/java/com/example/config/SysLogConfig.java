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

package com.example.config;

import com.example.syslog.SysLogAspect;
import com.example.syslog.event.AbstractSysLogListener;
import com.example.syslog.event.DefaultSysLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/*
 * @Author Oliver.Liu
 * @Desc //TODO 日志自动配置
 * @Date 2019/6/11 22:11
 * @Param
 * @return
 **/
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class SysLogConfig {
    @Bean
    public AbstractSysLogListener sysLogListener() {
        return new DefaultSysLogListener();
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}
