package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация не блокирующего счетчика.
 */
@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        int newInt;
        int old;
        do {
            old = count.get();
            newInt = old + 1;
        } while (!count.compareAndSet(old, newInt));
    }

    public int get() {
        return count.get();
    }
}
