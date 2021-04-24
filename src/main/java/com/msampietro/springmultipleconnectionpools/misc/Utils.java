package com.msampietro.springmultipleconnectionpools.misc;

import com.msampietro.springmultipleconnectionpools.model.BaseEntity;
import com.msampietro.springmultipleconnectionpools.projection.BaseProjection;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.EntityModel;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Log4j2
public final class Utils {

    private static final String MODULE_PACKAGE_NAME = "com.msampietro.springmultipleconnectionpools";

    private static final String HAL_PACKAGE = "hal";
    private static final String RESOURCE_PREFIX = "Resource";

    private static final String PROJECTION_PACKAGE = "projection";
    private static final String PROJECTION_PREFIX = "Projection";

    private Utils() {
    }

    @SuppressWarnings("unchecked")
    public static <P extends BaseProjection<I>, I extends Serializable, T extends BaseEntity<I>> Class<P> getEntityProjectionClass(Class<T> modelClazz) {
        String projectionClassName = StringUtils.join(MODULE_PACKAGE_NAME, ".", PROJECTION_PACKAGE, ".", modelClazz.getSimpleName(), PROJECTION_PREFIX);
        Class<P> projectionClass = null;
        try {
            projectionClass = (Class<P>) Class.forName(projectionClassName);
        } catch (ClassNotFoundException e) {
            log.debug("Object Projection ({}) not implemented - trying projectionClassName:({})", modelClazz, projectionClassName);
        }
        log.debug("getEntityProjectionClass - args modelClass:({}) - return: ({})", modelClazz, projectionClass);
        return projectionClass;
    }

    @SuppressWarnings("unchecked")
    public static <P extends BaseProjection<I>, I extends Serializable, T extends BaseEntity<I>> EntityModel<P> projectionToResourceMapping(Class<T> modelClazz, Object source) {
        String resourceClassName = StringUtils.join(MODULE_PACKAGE_NAME, ".", HAL_PACKAGE, ".", modelClazz.getSimpleName(), RESOURCE_PREFIX);
        EntityModel<P> resource = null;
        try {
            Class<EntityModel<P>> resourceSupportClass = (Class<EntityModel<P>>) Class.forName(resourceClassName);
            Constructor<EntityModel<P>> constructor = resourceSupportClass.getConstructor(source.getClass().getInterfaces()[0]);
            resource = constructor.newInstance(source);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {
            log.debug("Object Resource not implemented - modelClass:({})", modelClazz);
        }
        log.debug("projectionToResourceMapping - args: modelClass:({}), source:({}) - return: ({})", modelClazz, resourceClassName, resource);
        return resource;
    }

}
