package org.examples.pbk.otus.l51homework.framework;

public class Assert {
    private Assert() {}

    public static void assertTrue(boolean condition) {
        assertTrue(condition, "Condition supposed to be true");
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition) {
        assertFalse(condition, "Condition supposed to be false");
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertNull(Object object) {
        assertNull(object, "Object supposed to be null");
    }

    public static void assertNull(Object object, String message) {
        if (object != null) {
            throw new AssertionError(message);
        }
    }

    public static void assertNotNull(Object object) {
        assertNotNull(object, "Object supposed to be not null");
    }

    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(Object obj1, Object obj2) {
        assertEquals(obj1, obj2, "Objects supposed to be equal");
    }

    public static void assertEquals(Object obj1, Object obj2, String message) {
        if (!obj1.equals(obj2)) {
            throw new AssertionError(message);
        }
    }

    public static void assertNotEquals(Object obj1, Object obj2) {
        assertNotEquals(obj1, obj2, "Objects supposed to be not equal");
    }

    public static void assertNotEquals(Object obj1, Object obj2, String message) {
        if (obj1.equals(obj2)) {
            throw new AssertionError(message);
        }
    }

    public static void assertSame(Object obj1, Object obj2) {
        assertSame(obj1, obj2, "Objects references supposed to point on same Object");
    }

    public static void assertSame(Object obj1, Object obj2, String message) {
        if (obj1 != obj2) {
            throw new AssertionError(message);
        }
    }

    public static void assertNotSame(Object obj1, Object obj2) {
        assertNotSame(obj1, obj2, "Objects references supposed to point on different Objects");
    }

    public static void assertNotSame(Object obj1, Object obj2, String message) {
        if (obj1 == obj2) {
            throw new AssertionError(message);
        }
    }
}
