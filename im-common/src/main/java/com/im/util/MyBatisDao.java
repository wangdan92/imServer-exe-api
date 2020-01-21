package com.im.util;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
@Mapper
public @interface MyBatisDao {
    String value() default "";
}
