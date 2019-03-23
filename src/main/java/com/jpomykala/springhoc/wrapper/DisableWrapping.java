package com.jpomykala.springhoc.wrapper;

import java.lang.annotation.*;

/**
 * @author Jakub Pomykala on 2/26/18.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DisableWrapping
{

}
