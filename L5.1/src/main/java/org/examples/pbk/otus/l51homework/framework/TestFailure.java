package org.examples.pbk.otus.l51homework.framework;

public class TestFailure {
    private TestCase failedTest;
    private Throwable error;

    public TestFailure(TestCase failedTest, Throwable error) {
        this.failedTest = failedTest;
        this.error = error;
    }

    public TestCase getFailedTest() {
        return failedTest;
    }

    public Throwable getError() {
        return error;
    }
}
