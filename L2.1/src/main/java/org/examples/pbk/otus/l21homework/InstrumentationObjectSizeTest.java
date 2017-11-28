package org.examples.pbk.otus.l21homework;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;

public class InstrumentationObjectSizeTest {
    private Object object;

    public InstrumentationObjectSizeTest(Object object) {
        this.object = object;
    }

    public void test() {
        printResult(getObjectGraphSize(object, new IdentityHashMap<>()));
    }

    private long getObjectGraphSize(Object object, IdentityHashMap<Object, Object> visitedNodes) {
        if (object == null) return 0;
        if (visitedNodes.containsKey(object)) return 0;
        visitedNodes.put(object, null);
        long size = InstrumentationAgent.getObjectSize(object);

        if (object.getClass().isArray()) {
            if (!object.getClass().getComponentType().isPrimitive()) {
                for (int i = 0; i < Array.getLength(object); i++) {
                    size += getObjectGraphSize(Array.get(object, i), visitedNodes);
                }
            }
        } else {
            for (Field field : object.getClass().getDeclaredFields()) {
                boolean isAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    if (!field.getType().isPrimitive() || field.getModifiers() == Modifier.STATIC) size += getObjectGraphSize(field.get(object), visitedNodes);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(isAccessible);
                }
            }
        }

        return size;
    }

    private void printResult(long memoryResult) {
        System.out.printf("Size of %s in bytes = %d%n", object.getClass().getName(), memoryResult);
    }
}
