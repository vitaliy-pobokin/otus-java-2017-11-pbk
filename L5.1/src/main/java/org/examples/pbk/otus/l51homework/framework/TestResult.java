package org.examples.pbk.otus.l51homework.framework;

import java.util.ArrayList;
import java.util.List;

class TestResult {
    private TestCase test;
    private List<TestFailure> failures;
    private List<TestFailure> errors;

    TestResult(TestCase test) {
        this.test = test;
        this.failures = new ArrayList<>();
        this.errors = new ArrayList<>();
    }

    public void addFailure(TestFailure failure) {
        failures.add(failure);
    }

    public void addError(TestFailure error) {
        errors.add(error);
    }

    public boolean isSuccess() {
        return !hasErrors() && !hasFailures();
    }

    public boolean hasErrors() {
        return errors.size() != 0;
    }

    public boolean hasFailures() {
        return failures.size() != 0;
    }

    public TestCase getTest() {
        return test;
    }

    public List<TestFailure> getFailures() {
        return failures;
    }

    public List<TestFailure> getErrors() {
        return errors;
    }
}
