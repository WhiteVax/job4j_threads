package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        var storage = new Cache();
        var first = new Base(1, 1);
        first.setName("Igor");
        var second = new Base(2, 1);
        first.setName("Petr");
        assertThat(storage.add(first)).isTrue();
        assertThat(storage.add(second)).isTrue();
        assertThat(storage.get(first.getId())).isEqualTo(first);
    }

    @Test
    void whenUpdate() {
        var storage = new Cache();
        var first = new Base(1, 1);
        first.setName("Igor");
        var second = new Base(1, 1);
        second.setName("Petr");
        storage.add(first);
        var expected = new Base(1, 2);
        expected.setName("Petr");
        assertThat(storage.update(second)).isTrue();
        assertThat(storage.get(second.getId())).isEqualTo(expected);
    }

    @Test
    void whenUpdateWithException() {
        var storage = new Cache();
        var first = new Base(1, 1);
        var second = new Base(1, 2);
        storage.add(first);
        assertThatThrownBy(() -> storage.update(second))
                .isInstanceOf(OptimisticException.class)
                .hasMessage("Versions are not equal");
    }

    @Test
    void whenDelete() {
        var storage = new Cache();
        var base = new Base(1, 1);
        storage.add(base);
        storage.delete(base);
        assertThat(storage.get(1)).isNull();
    }
}