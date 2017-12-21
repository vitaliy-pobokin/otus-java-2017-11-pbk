package org.examples.pbk.otus.l51homework.framework;

import java.io.PrintStream;

public class ResultPrinter {

    private enum Color {
        GREEN("\u001B[32m"),
        RED("\u001B[31m"),
        YELLOW("\u001B[33m"),
        RESET_COLOR("\u001B[0m");

        private final String ansiCode;
        Color(String ansiCode) {
            this.ansiCode = ansiCode;
        }

        String getAnsiCode() {
            return this.ansiCode;
        }
    }

    private PrintStream writer;

    public ResultPrinter(PrintStream writer) {
        this.writer = writer;
    }

    public void print(TestResult result) {
        printTestInfo(result);
        printErrors(result);
        printFailures(result);
        resetColor();
        writer.println();
    }


    private void printTestInfo(TestResult result) {
        if (result.isSuccess()) {
            setColor(Color.GREEN);
            writer.print("SUCCESS - ");
        } else if (result.hasErrors()) {
            setColor(Color.RED);
            writer.print("ERROR - ");
        } else {
            setColor(Color.YELLOW);
            writer.print("FAILURE - ");
        }
        writer.println(result.getTest().getName());
        writer.println();
    }

    private void printErrors(TestResult result) {
        for (TestFailure error : result.getErrors()) {
            printError(error);
        }
    }

    private void printError(TestFailure error) {
        if (error.getError().getClass().equals(UnexpectedException.class)) {
            if (error.getFailedTest().expectedExceptions.size() > 0) {
                printUnexpectedExceptions(error);
            }
            writer.println(error.getError().getCause());
        } else {
            writer.println(error.getError());
        }
        printStackTrace(error.getError());
        writer.println();
    }

    private void printFailures(TestResult result) {
        for (TestFailure failure : result.getFailures()) {
            printFailure(failure);
        }
    }

    private void printFailure(TestFailure failure) {
        writer.println(failure.getError());
        printStackTrace(failure.getError());
        writer.println();
    }

    private void printUnexpectedExceptions(TestFailure failure) {
        writer.print("Expected: <");
        for (Class<? extends Throwable> expectedException : failure.getFailedTest().expectedExceptions) {
            writer.print(expectedException.getName());
        }
        writer.print("> but was <");
        writer.print(failure.getError().getCause().getClass());
        writer.println(">");
        writer.print("Caused by: ");
    }

    private void printStackTrace(Throwable t) {
        for (StackTraceElement element : t.getStackTrace()) {
            writer.print("\t at ");
            writer.println(element.toString());
        }
    }

    private void setColor(Color color) {
        writer.print(color.getAnsiCode());
    }

    private void resetColor() {
        setColor(Color.RESET_COLOR);
    }
}
