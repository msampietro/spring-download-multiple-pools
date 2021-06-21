package com.msampietro.springmultipleconnectionpools.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.support.WebStack;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;

import static java.lang.reflect.Modifier.FINAL;
import static org.springframework.util.ReflectionUtils.*;

@Log4j2
@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL, stacks = WebStack.WEBMVC)
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String APPA$BMP_CLASS = "org.springframework.hateoas.server.core.WebHandler$HandlerMethodParameter";
    private static final String APPA$BMP_CONVERSION_SERVICE = "CONVERSION_SERVICE";

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Register a Serializable To String Converter
        workaround((ConversionService) registry);
        registry.addConverter(new SerializableToStringConverter());
    }

    //Workaround for opened issue https://github.com/spring-projects/spring-hateoas/issues/118
    private void workaround(ConversionService conversionService) {
        try {
            ReflectionUtils.doWithFields(
                    Class.forName(APPA$BMP_CLASS),
                    it -> setValue(it, conversionService),
                    it -> APPA$BMP_CONVERSION_SERVICE.equals(it.getName())
            );
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    //WARNING: An illegal reflective access operation has occurred
    private void setValue(Field field, Object value) {
        // Remove final modifier
        Field modifiersField = findField(Field.class, "modifiers");
        makeAccessible(modifiersField);
        setField(modifiersField, field, field.getModifiers() & ~FINAL);

        // Set field value
        makeAccessible(field);
        setField(field, null, value);
    }

}