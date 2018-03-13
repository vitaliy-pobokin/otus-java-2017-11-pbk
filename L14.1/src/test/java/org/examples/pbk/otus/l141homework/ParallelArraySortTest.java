package org.examples.pbk.otus.l141homework;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class ParallelArraySortTest {

    @Test
    public void testSort() {
        int size = 10_000_000;
        int[] array = getUnsortedArray(size);
        int parallelThreads = 4;
        ParallelArraySort.sort(array, parallelThreads);
        Assert.assertTrue(isSortedArray(array));
    }

    private boolean isSortedArray(int[] array) {
        for(int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) return false;
        }
        return true;
    }

    private int[] getUnsortedArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
