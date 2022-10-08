package ru.job4j.thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            System.out.println("Поток усыплён.");
            wait();
        }
        queue.add(value);
        notify();
        System.out.println("Элемент добавлен.");

    }

    public synchronized T poll() throws InterruptedException {
        T rsl = null;
        while (queue.size() == 0) {
            System.out.println("Поток усыплён.");
            wait();
        }
        rsl = queue.poll();
        notify();
        System.out.println("Элемент удалён.");
        return rsl;
    }
}

