package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    void whenIncrementAfterGetAndParallelThreadsNotLocked() {
        var cas = new CASCount();
        var first = new Thread(
                () -> IntStream.range(0, 5).forEach(e -> cas.increment())
        );

        var second = new Thread(
                () -> IntStream.range(0, 1).forEach(e -> cas.get()));
        first.start();
        second.start();
        assertThat(cas.get()).isEqualTo(0);
        assertThat(cas.get()).isEqualTo(5);
    }

    @Test
    void whenIncrement() {
        var cas = new CASCount();
        cas.increment();
        cas.increment();
        assertThat(cas.get()).isEqualTo(2);
    }
}