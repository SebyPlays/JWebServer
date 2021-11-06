package com.github.sebyplays.jwebserver.utils.annotations;

import com.github.sebyplays.jwebserver.utils.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {

    Priority priority();

}
