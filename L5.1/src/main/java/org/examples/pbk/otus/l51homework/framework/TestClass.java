package org.examples.pbk.otus.l51homework.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class TestClass {
    private Class<?> clazz;
    private Map<Class<? extends Annotation>, List<Method>> annotatedMethods;

    public TestClass(Class<?> clazz) {
        if (clazz != null && isEligibleClass(clazz)) {
            this.clazz = clazz;
        } else {
            throw new IllegalArgumentException("Test class must have only one default public constructor without parameters");
        }
        this.annotatedMethods = new HashMap<>();
        for (Class<? extends Annotation> annotationClass : TestFramework.getRegisteredAnnotations()) {
            scanForAnnotatedMethods(annotationClass);
        }
    }

    private boolean isEligibleClass(Class<?> clazz) {
        return clazz.getConstructors().length == 1 && clazz.getConstructors()[0].getParameterCount() == 0;
    }

    private boolean isEligibleMethod(Method method) {
        return method.getReturnType().equals(Void.TYPE) && method.getParameterCount() == 0;
    }

    private void scanForAnnotatedMethods(Class<? extends Annotation> annotationClass) {
        if (!annotatedMethods.containsKey(annotationClass)) {
            annotatedMethods.put(annotationClass, new ArrayList<>());
        }
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                if (isEligibleMethod(method)) {
                    annotatedMethods.get(annotationClass).add(method);
                } else {
                    throw new IllegalArgumentException("Test method can't have any parameters and must return void");
                }
            }
        }
    }

    public Constructor<?> getConstructor() {
        return clazz.getConstructors()[0];
    }

    public Map<Class<? extends Annotation>, List<Method>> getAnnotatedMethods() {
        return annotatedMethods;
    }
}
