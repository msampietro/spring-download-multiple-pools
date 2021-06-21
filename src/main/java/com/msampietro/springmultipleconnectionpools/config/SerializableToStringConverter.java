package com.msampietro.springmultipleconnectionpools.config;

import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;

//Workaround for opened issue https://github.com/spring-projects/spring-hateoas/issues/118
public class SerializableToStringConverter implements Converter<Serializable, String> {

    @Override
    public String convert(Serializable source) {
        return source.toString();
    }
}
