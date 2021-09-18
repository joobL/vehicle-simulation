package com.muyu.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 牧鱼
 * @Classname KeyAnn
 * @Description TODO
 * @Date 2021/9/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KeyAnn {

    public String key() default "" ;

    public String unit() default "";
}
