package org.examples.pbk.otus.l51homework.testpackage.testinnerpackage;

import org.examples.pbk.otus.l51homework.annotations.After;
import org.examples.pbk.otus.l51homework.annotations.Before;
import org.examples.pbk.otus.l51homework.annotations.Test;

public class TestBadTestMethod {
    public TestBadTestMethod() {
        System.out.println("TestBadTestMethod - Constructor");
    }

    @Before
    public void before() throws Exception {
        System.out.println("Before");
        throw new Exception("Exception in Before");
    }

    @Test
    public String test() {
        System.out.println("Test");
        return "";
    }

    @After
    public void after() {
        System.out.println("After");
    }
}
