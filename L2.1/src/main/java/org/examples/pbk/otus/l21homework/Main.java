package org.examples.pbk.otus.l21homework;

/**
 * To run InstrumentationObjectSizeTest you should add
 * InstrumentationAgent at JVM startup through -javaagent parameter
 * e.g. run
 * java -javaagent:target\L2.1-homework.jar -jar target\L2.1-homework.jar
 */
public class Main {
    public static void main(String[] args) {
        new InstrumentationObjectSizeTest(new Object()).test();
        new InstrumentationObjectSizeTest("").test();
        new InstrumentationObjectSizeTest(new User(1, "pbk", "123456")).test();
        new InstrumentationObjectSizeTest(new int[0]).test();
        new InstrumentationObjectSizeTest(new int[1]).test();
        new InstrumentationObjectSizeTest(new int[2]).test();
        new InstrumentationObjectSizeTest(new int[3]).test();
        new InstrumentationObjectSizeTest(new int[10_000_000]).test();
        new InstrumentationObjectSizeTest(new long[0]).test();
        new InstrumentationObjectSizeTest(new long[1]).test();
        new InstrumentationObjectSizeTest(new long[2]).test();
        new InstrumentationObjectSizeTest(new long[3]).test();
        new InstrumentationObjectSizeTest(new long[10_000_000]).test();
    }
}
