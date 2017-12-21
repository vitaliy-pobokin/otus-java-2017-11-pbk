package org.examples.pbk.otus.l51homework.testpackage.testinnerpackage;

import org.examples.pbk.otus.l51homework.annotations.After;
import org.examples.pbk.otus.l51homework.annotations.Before;
import org.examples.pbk.otus.l51homework.annotations.Test;

public class TestExceptionInBefore {
    public TestExceptionInBefore() {
        System.out.println("TestExceptionInBefore - Constructor");
    }
    @Before
    public void before() throws Exception {
        System.out.println("Before");
        throw new Exception("Exception in Before");
    }

    @Test
    public void test() {
        System.out.println("Test");
    }

    @After
    public void after() {
        System.out.println("After");
    }
}
