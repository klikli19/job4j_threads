package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelSearch(T value, T[] array, int from, int to) {
        this.value = value;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
    if (to - from < 10) {
        for (int i = from; i <= to; i++) {
            if (value.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
    int middle = (from + to) / 2;
    ParallelSearch<T> leftSort = new ParallelSearch<>(value, array, from, middle);
    ParallelSearch<T> rightSort = new ParallelSearch<>(value, array, middle + 1, to);
    leftSort.fork();
    rightSort.fork();
    Integer left = leftSort.join();
    Integer right = rightSort.join();
    return left == -1 ? right : left;
}

public static <T> int sort(T value, T[] array) {
    if (value == null || array == null || array.length == 0 || value.getClass() != array[0].getClass()) {
        throw new IllegalArgumentException();
    }
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    return forkJoinPool.invoke(new ParallelSearch<>(value, array, 0, array.length - 1));
    }
}
