package com.cs.roomdbapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
*
* This is to ensure controller response is an exception, you do not want to standardising the response
* in situation like sending csv or downloading an file by sending blob data,or manually chunking large amount of data.
*
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IgnoreResponseBinding {
}
