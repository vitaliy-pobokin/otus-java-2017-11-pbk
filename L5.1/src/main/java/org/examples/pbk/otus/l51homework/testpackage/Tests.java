package org.examples.pbk.otus.l51homework.testpackage;

import org.examples.pbk.otus.l51homework.annotations.After;
import org.examples.pbk.otus.l51homework.annotations.Before;
import org.examples.pbk.otus.l51homework.annotations.Test;
import org.examples.pbk.otus.l51homework.framework.Assert;

import java.lang.annotation.AnnotationTypeMismatchException;

public class Tests {
    public Tests() {
        System.out.println("Constructor");
    }

    @Before
    public void before() throws Exception {
        System.out.println("Before");
    }

    @After
    public void after() throws Exception {
        System.out.println("After");
    }

    @Test
    public void test1() {
        System.out.println("Test-1: expected SUCCESS");
    }

    @Test
    public void test2() throws Exception {
        System.out.println("Test-2: expected ERROR");
        throw new Exception("Test-2 exception");
    }

    @Test(expected = Exception.class)
    public void test3() throws Exception {
        System.out.println("Test-3: expected SUCCESS");
        throw new Exception("Test-3 exception");
    }

    @Test(expected = AnnotationTypeMismatchException.class)
    public void test4() throws Exception {
        System.out.println("Test-4: expected ERROR");
        throw new Exception("Test-4 exception");
    }

    @Test
    public void test5() {
        System.out.println("Test-5: expected FAILURE");
        Assert.assertTrue(false);
    }
}
