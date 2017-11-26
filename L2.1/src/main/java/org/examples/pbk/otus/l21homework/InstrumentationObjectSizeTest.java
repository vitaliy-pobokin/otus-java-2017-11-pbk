package org.examples.pbk.otus.l21homework;

public class InstrumentationObjectSizeTest {
    private Object object;

    public InstrumentationObjectSizeTest(Object object) {
        this.object = object;
    }

    public void test() {
        printResult(InstrumentationAgent.getObjectSize(object));
    }

    private void printResult(long memoryResult) {
        System.out.printf("Size of %s in bytes = %d%n", object.getClass().getName(), memoryResult);
    }
}
