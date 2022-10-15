package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenSum() {
        int[][] matrix = new int[][]{{0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3}};
        Sums[] sums;
        sums = RolColSum.sum(matrix);
        assertThat(sums).isEqualTo(new Sums[]{new Sums(0, 6),
                new Sums(4, 6),
                new Sums(8, 6),
                new Sums(12, 6),
        });
    }

    @Test
    void whenAsyncSum() {
        int[][] matrix = new int[][]{{0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3}};
        Sums[] sums;
        try {
            sums = RolColSum.asyncSum(matrix);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertThat(sums).isEqualTo(new Sums[]{new Sums(0, 6),
                new Sums(4, 6),
                new Sums(8, 6),
                new Sums(12, 6),
        });
    }
}