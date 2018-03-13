package org.examples.pbk.otus.l141homework;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ParallelArraySort {

    private static final int THRESHOLD = 100_000;

    public static void sort(int[] array) {
        int processors = Integer.parseInt(System.getenv("NUMBER_OF_PROCESSORS"));
        sort(array, processors);
    }

    public static void sort(int[] array, int numTasks) {
        final int nElements = array.length;
        if (nElements < THRESHOLD) {
            new SortAction(array, 0, nElements).compute();
        } else {
            SortAction[] tasks = new SortAction[numTasks];
            for (int i = 0; i < tasks.length; i++) {
                int startIndex = getChunkStartInclusive(i, nElements, numTasks);
                int endIndex = getChunkEndExclusive(i, nElements, numTasks);
                tasks[i] = new SortAction(array, startIndex, endIndex);
            }
            for (int i = 1; i < numTasks; i++) {
                tasks[i].fork();
            }
            tasks[0].compute();
            for (int i = 1; i < numTasks; i++) {
                tasks[i].join();
            }
            merge(array, numTasks);
        }
    }

    private static void merge(int[] array, int chunks) {
        for (int mergeCycle = 1, k = 1; mergeCycle < chunks; mergeCycle *= 2, k++) {
            RecursiveAction tasks[] = new RecursiveAction[chunks / (1 << k)];
            for (int i = 1; i < tasks.length; i++) {
                int leftStart = i * array.length / tasks.length;
                int leftSize = array.length / tasks.length / 2;
                int rightStart = leftStart + leftSize;
                int rightSize = leftSize;
                tasks[i] = new MergeAction(array, leftStart, rightStart, leftSize, rightSize);
                tasks[i].fork();
            }
            int leftStart = 0;
            int leftSize = array.length / tasks.length / 2;
            int rightStart = leftStart + leftSize;
            int rightSize = leftSize;
            new MergeAction(array, leftStart, rightStart, leftSize, rightSize).compute();
            for (int i = 1; i < tasks.length; i++) {
                tasks[i].join();
            }
        }
    }

    private static int getChunkSize(int nElements, int nChunks) {
        return (nElements + nChunks - 1) / nChunks;
    }

    private static int getChunkStartInclusive(int chunk, int nElements, int nChunks) {
        int chunkSize = getChunkSize(nElements, nChunks);
        return chunk * chunkSize;
    }

    private static int getChunkEndExclusive(int chunk, int nElements, int nChunks) {
        int chunkSize = getChunkSize(nElements, nChunks);
        int end = (chunk + 1) * chunkSize;
        if (end > nElements)
            return nElements;
        return end;
    }
}

class SortAction extends RecursiveAction {
    private int[] input;
    private int start;
    private int end;

    public SortAction(int[] input, int start, int end) {
        this.input = input;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        Arrays.sort(input, start, end);
    }
}

class MergeAction extends RecursiveAction {
    private int[] source;
    private int[] temp;
    private int leftStart;
    private int rightStart;
    private int leftSize;
    private int rightSize;

    public MergeAction(int[] array, int leftStart, int rightStart, int leftSize, int rightSize) {
        this.source = array;
        this.temp = Arrays.copyOfRange(array, leftStart, rightStart + rightSize);
        this.leftStart = leftStart;
        this.rightStart = rightStart;
        this.leftSize = leftSize;
        this.rightSize = rightSize;
    }

    @Override
    protected void compute() {
        int tempLeftIndex = 0;
        int tempRightIndex = leftSize;
        int mergedElemIndex = leftStart;
        while (tempLeftIndex < leftSize && tempRightIndex < temp.length) {
            if (temp[tempLeftIndex] < temp[tempRightIndex]) {
                source[mergedElemIndex] = temp[tempLeftIndex];
                tempLeftIndex++;
            } else {
                source[mergedElemIndex] = temp[tempRightIndex];
                tempRightIndex++;
            }
            mergedElemIndex++;
        }
        while (tempLeftIndex < leftSize) {
            source[mergedElemIndex] = temp[tempLeftIndex];
            tempLeftIndex++;
            mergedElemIndex++;
        }
        while (tempRightIndex < temp.length) {
            source[mergedElemIndex] = temp[tempRightIndex];
            tempRightIndex++;
            mergedElemIndex++;
        }
    }
}
