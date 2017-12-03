package org.examples.pbk.otus.l31homework;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void addAllTest() {
        List<Integer> list = new MyArrayList<>();
        Integer[] elementsToAdd = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Collections.addAll(list, elementsToAdd);
        for (Integer i : elementsToAdd) {
            assertTrue(list.contains(i));
        }
    }

    @Test
    public void copyTest() {
        List<Integer> src = new MyArrayList<>();
        spawnListByRandomIntegers(src, 100);
        List<Integer> dst = new MyArrayList<>();
        spawnListByRandomIntegers(dst, 100);
        Collections.copy(dst, src);
        for (int i = 0; i < src.size(); i++) {
            assertTrue(src.get(i).equals(dst.get(i)));
        }
    }

    @Test
    public void sortTest() {
        List<Integer> list = new MyArrayList<>();
        spawnListByRandomIntegers(list, 100);
        Collections.sort(list, Comparator.naturalOrder());
        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue(list.get(i) < list.get(i + 1));
        }
    }

    private void spawnListByRandomIntegers(List<? super Integer> list, int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt());
        }
    }
}
