package com.example.xianliu;

import com.example.syslog.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Oliver.liu
 * @version 1.0.0
 * @ClassName AccessLimitController.java
 * @Description TODO
 * @createTime 2022年11月16日 11:02:00
 */
@RestController
@RequestMapping("access")
public class AccessLimitController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 限流测试
     */
    @GetMapping
    @AccessLimit(maxCount = 3,second = 60)
    @SysLog("Oliver测试")
    public String limit(HttpServletRequest request) {
        logger.error("Access Limit Test");
        return "限流测试";
    }
}
