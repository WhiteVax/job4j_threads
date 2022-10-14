package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Реализация последовательной работы и асинхронной
 */
public class RolColSum {

    public static Sums sumArray(int[][] matrix, int j) {
        var row = 0;
        var col = 0;
        for (int i = 0; i < matrix[j].length; i++) {
            row += matrix[i][j];
            col += matrix[j][i];
        }
        return new Sums(row, col);
    }

    /* Последовательная версия*/
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumArray(matrix, i);
        }
        return sums;
    }

    /* Асинхронная версия*/
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        return getTask(matrix, sums).get();
    }

    public static CompletableFuture<Sums[]> getTask(int[][] matrix, Sums[] sum) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < matrix.length; i++) {
              sum[i] = sumArray(matrix, i);
            }
            return sum;
        });
    }
}
