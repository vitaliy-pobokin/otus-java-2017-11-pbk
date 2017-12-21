package org.examples.pbk.otus.l51homework.framework;

import org.examples.pbk.otus.l51homework.annotations.After;
import org.examples.pbk.otus.l51homework.annotations.Before;
import org.examples.pbk.otus.l51homework.annotations.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestFramework {
    private static final List<Class<? extends Annotation>> REGISTERED_ANNOTATIONS = Arrays.asList(Before.class, Test.class, After.class);

    public static void main(String[] args) {
        run(org.examples.pbk.otus.l51homework.testpackage.Tests.class);
//        run("org.examples.pbk.otus.l51homework.testpackage.testinnerpackage");
    }

    public static void run(Class<?> clazz) {
        TestClass testClass = null;
        try {
            testClass = new TestClass(clazz);
        } catch (IllegalArgumentException e) {
            System.err.format("%s%n", e);;
            return;
        }
        for (TestCase test : prepareTestCases(testClass)) {
            TestResult result = new TestResult(test);
            test.run(result);

            ResultPrinter printer = new ResultPrinter(System.out);
            printer.print(result);
        }
    }

    public static void run(String packageName) {
        List<Class<?>> classes = null;
        try {
            classes = getClassesFromPackage(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Class<?> clazz : classes) {
            run(clazz);
        }
    }

    private static List<TestCase> prepareTestCases(TestClass testClass) {
        List<TestCase> testCaseList = new ArrayList<>();
        for (Method testMethod : testClass.getAnnotatedMethods().get(Test.class)) {
            TestCase testCase = new TestCase(testMethod, testClass);
            testCaseList.add(testCase);
        }
        return testCaseList;
    }

    public static List<Class<? extends Annotation>> getRegisteredAnnotations() {
        return REGISTERED_ANNOTATIONS;
    }



    private static List<Class<?>> getClassesFromPackage(String packageName)
            throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String dir = packageName.replace('.', '/');
        URL resource = classLoader.getResource(dir);
        ClassFileFinder classFinder = new ClassFileFinder();
        Files.walkFileTree(Paths.get(resource.toURI()), classFinder);
        List<Class<?>> classes = new ArrayList<>();
        for (Path file : classFinder.getFiles()) {
            String fileName = file.getFileName().toString();
            String newPackageName = file.getParent().toUri().toString().replace('/', '.');
            newPackageName = newPackageName.substring(newPackageName.indexOf(packageName), newPackageName.length());
            classes.add(Class.forName(newPackageName + fileName.substring(0, fileName.lastIndexOf('.'))));
        }
        return classes;
    }

}
