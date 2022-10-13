package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchIndexTest {
    @Test
    public void whenSearchLinear() {
        var rsl = ParallelSearchIndex.find(new String[]{"first", "second",
                "box"}, "box");
        assertThat(rsl).isEqualTo(2);
    }

    @Test
    public void whenSearchRecursive() {
        var rsl = ParallelSearchIndex.find(new String[]{"first", "second",
                "box", "mouse", "laptop", "desktop", "table", "window",
                "cat", "animal", "mouse", "array", "list", "string"},
                "list");
        assertThat(rsl).isEqualTo(12);
    }

    @Test
    public void whenSearchNotFound() {
        var rsl = ParallelSearchIndex.find(new Integer[]{0, 1, 2, 3, 4, 5, 6},
                12);
        assertThat(rsl).isEqualTo(-1);
    }
}