package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Параллельный поиск индекса в массиве объектов, если размер не больше 10
 * используется обычный линейный поиск
 */
public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public int linearSearch() {
        var rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        var left = new ParallelSearchIndex<>(array, value, from, mid);
        var right = new ParallelSearchIndex<>(array, value, mid + 1, to);
        left.fork();
        right.fork();
        var leftInt = left.join();
        var rightInt = right.join();
        return Math.max(leftInt, rightInt);
    }

    public static <T> int find(T[] array, T value) {
        var forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndex<>(array, value, 0,
                array.length - 1));
    }
}
