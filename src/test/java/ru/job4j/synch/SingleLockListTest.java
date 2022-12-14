package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> start = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(start);
        var first = new Thread(() -> list.add(1));
        var second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).contains(1, 2);
    }
}