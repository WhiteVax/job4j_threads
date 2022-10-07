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

    public synchronized void offer(T value) {
        while (queue.size() == limit) {
            try {
                System.out.println("Поток усыплён.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        queue.add(value);
        System.out.println("Элемент добавлен.");

    }

    public synchronized T poll() {
        T rsl = null;
        while (queue.size() == 0) {
            try {
                System.out.println("Поток усыплён.");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notify();
        rsl = queue.poll();
        System.out.println("Элемент удалён.");
        return rsl;
    }
}

