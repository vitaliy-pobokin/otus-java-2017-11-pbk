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
        new InstrumentationObjectSizeTest("a").test();
        new InstrumentationObjectSizeTest("abcd").test();
        new InstrumentationObjectSizeTest("abcde").test();
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

        UserInner2 user2 = new UserInner2(1, "pbk", "123456");
        UserInner2[] users2 = new UserInner2[100];
        for (int i = 0; i < users2.length; i++) {
            users2[i] = new UserInner2(i, "pbk", "123456");
        }
        UserInner1 user1 = new UserInner1(1, "pbk", "123456", users2);
        User user = new User(1, "pbk", "123456", user1);

        new InstrumentationObjectSizeTest(user2).test();
        new InstrumentationObjectSizeTest(users2).test();
        new InstrumentationObjectSizeTest(user1).test();
        new InstrumentationObjectSizeTest(user).test();
    }
}
