package com.msampietro.springmultipleconnectionpools.misc;

import com.msampietro.springmultipleconnectionpools.module.base.model.BaseEntity;
import com.msampietro.springmultipleconnectionpools.module.base.projection.BaseProjection;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.EntityModel;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

@Log4j2
public final class Utils {

    private static final String MODULE_PACKAGE_NAME = "com.msampietro.springmultipleconnectionpools.module";
    private static final String HAL_PACKAGE = "hal";
    private static final String RESOURCE_PREFIX = "Resource";
    private static final String PROJECTION_PACKAGE = "projection";
    private static final String PROJECTION_PREFIX = "Projection";
    private static final String DOT = ".";

    private Utils() {
    }

    @SuppressWarnings("unchecked")
    public static <P extends BaseProjection<I>, I extends Serializable, T extends BaseEntity<I>> Class<P> getEntityProjectionClass(Class<T> modelClazz) {
        String projectionClassName = buildModuleSearchPath(modelClazz, PROJECTION_PACKAGE, PROJECTION_PREFIX);
        Class<P> projectionClass = null;
        try {
            projectionClass = (Class<P>) Class.forName(projectionClassName);
        } catch (ClassNotFoundException e) {
            log.debug("Object Projection ({}) not implemented - trying projectionClassName:({})", modelClazz, projectionClassName);
        }
        log.debug("getEntityProjectionClass - args modelClass:({}) - return: ({})", modelClazz, projectionClass);
        return projectionClass;
    }

    private static <I extends Serializable, T extends BaseEntity<I>> String buildModuleSearchPath(Class<T> modelClazz,
                                                                                                  String packageName,
                                                                                                  String prefix) {
        String moduleName = parsePackageModuleName(modelClazz.getName());
        return StringUtils.join(MODULE_PACKAGE_NAME,
                DOT,
                moduleName,
                DOT,
                packageName,
                DOT,
                modelClazz.getSimpleName(),
                prefix);
    }

    private static String parsePackageModuleName(String packageName) {
        var pattern = Pattern.compile("(?<=.module.)(.*)(?=.model.)");
        var matcher = pattern.matcher(packageName);
        if (matcher.find())
            return matcher.group(0);
        return "";
    }

    @SuppressWarnings("unchecked")
    public static <P extends BaseProjection<I>, I extends Serializable, T extends BaseEntity<I>> EntityModel<P> projectionToResourceMapping(Class<T> modelClazz, Object source) {
        String resourceClassName = buildModuleSearchPath(modelClazz, HAL_PACKAGE, RESOURCE_PREFIX);
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
