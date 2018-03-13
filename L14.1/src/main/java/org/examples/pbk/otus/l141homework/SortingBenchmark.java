package org.examples.pbk.otus.l141homework;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 10)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SortingBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {

        public static final int ARRAY_LENGTH = 10_000_000;

        int[] array;

        @Setup(Level.Iteration)
        public void initState() {
            this.array = new int[ARRAY_LENGTH];
            Random random = new Random();
            for (int i = 0; i < ARRAY_LENGTH; i++) {
                array[i] = random.nextInt();
            }
        }
    }

    @Benchmark
    public int arraysSequentialSort(BenchmarkState state) {
        Arrays.sort(state.array);
        return state.array[0];
    }

    @Benchmark
    public int arraysParallelSort(BenchmarkState state) {
        Arrays.parallelSort(state.array);
        return state.array[0];
    }

    @Benchmark
    public int myParallelSort2Threads(BenchmarkState state) {
        ParallelArraySort.sort(state.array, 2);
        return state.array[0];
    }

    @Benchmark
    public int myParallelSort4Threads(BenchmarkState state) {
        ParallelArraySort.sort(state.array, 4);
        return state.array[0];
    }

    @Benchmark
    public int myParallelSort8Threads(BenchmarkState state) {
        ParallelArraySort.sort(state.array, 8);
        return state.array[0];
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SortingBenchmark.class.getSimpleName())
                .forks(1)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }

}
