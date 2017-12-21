package org.examples.pbk.otus.l51homework.framework;

import org.examples.pbk.otus.l51homework.annotations.After;
import org.examples.pbk.otus.l51homework.annotations.Before;
import org.examples.pbk.otus.l51homework.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TestCase {
    Method testMethod;
    Constructor testObjectConstructor;
    Set<Class<? extends Throwable>> expectedExceptions;
    List<Method> warmupMethods;
    List<Method> teardownMethods;

    private TestClass testClass;

    private boolean stopTest = false;

    private TestCase() {}

    TestCase(Method testMethod, TestClass testClass) {
        this.testMethod = testMethod;
        this.testClass = testClass;
        initializeExpectedExceptions();
        initializeWarmupMethods();
        initializeTeardownMethods();
        this.testObjectConstructor = testClass.getConstructor();
    }

    public String getName() {
        return testObjectConstructor.getDeclaringClass().getName() + (!stopTest ? " - " + testMethod.getName() : "");
    }

    public TestResult run(TestResult result) {
        Object testObject = getTestObjectInstance(result);
        if (stopTest) return result;
        warmup(testObject, result);
        if (!stopTest) runTestMethod(testObject, result);
        teardown(testObject, result);
        return result;
    }

    private void initializeExpectedExceptions() {
        expectedExceptions = new HashSet<>();
        Test testAnnotation = testMethod.getAnnotation(Test.class);
        Class<? extends Throwable> expected = testAnnotation.expected();
        if (!expected.equals(Test.NoExceptionExpected.class)) {
            expectedExceptions.add(expected);
        }
    }

    private void initializeWarmupMethods() {
        warmupMethods = new ArrayList<>();
        warmupMethods.addAll(testClass.getAnnotatedMethods().get(Before.class));
    }

    private void initializeTeardownMethods() {
        teardownMethods = new ArrayList<>();
        teardownMethods.addAll(testClass.getAnnotatedMethods().get(After.class));
    }

    private Object getTestObjectInstance(TestResult result) {
        Object testObject = null;
        try {
            testObject = testObjectConstructor.newInstance();
        } catch (Throwable error) {
            if (error.getClass().equals(InvocationTargetException.class)) {
                error = error.getCause();
            }
            result.addError(new TestFailure(this, error));
            stopTest = true;
        }
        return testObject;
    }

    private void runTestMethod(Object testObject, TestResult result) {
        try {
            testMethod.invoke(testObject);
        } catch (Throwable t) {
            if (t.getClass().equals(InvocationTargetException.class)) {
                t = t.getCause();
            }
            if (t.getClass().equals(AssertionError.class)) {
                result.addFailure(new TestFailure(this, t));
            } else if (!expectedExceptions.contains(t.getClass())) {
                UnexpectedException error = new UnexpectedException(t);
                result.addError(new TestFailure(this, error));
            }
        }
    }

    private void warmup(Object testObject, TestResult result) {
        for (Method method : warmupMethods) {
            try {
                method.invoke(testObject);
            } catch (Throwable error) {
                if (error.getClass().equals(InvocationTargetException.class)) {
                    error = error.getCause();
                }
                result.addError(new TestFailure(this, error));
                stopTest = true;
            }
        }
    }

    private void teardown(Object testObject, TestResult result) {
        for (Method method : teardownMethods) {
            try {
                method.invoke(testObject);
            } catch (Throwable error) {
                if (error.getClass().equals(InvocationTargetException.class)) {
                    error = error.getCause();
                }
                result.addError(new TestFailure(this, error));
            }
        }
    }
}
