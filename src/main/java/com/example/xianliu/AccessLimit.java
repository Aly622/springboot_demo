package com.example.xianliu;

import java.lang.annotation.*;

/**
 * @author Oliver.liu
 * @version 1.0.0
 * @ClassName AccessLimit.java
 * @Description TODO
 * @createTime 2022年11月16日 10:45:00
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    /**
     * 指定second 时间内 API请求次数
     */
    int maxCount() default 5;
    /**
     * 请求次数的指定时间范围  秒数(redis数据过期时间)
     */
    int second() default 60;
}
