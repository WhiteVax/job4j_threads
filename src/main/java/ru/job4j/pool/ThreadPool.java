package ru.job4j.pool;

import ru.job4j.thread.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks =
            new SimpleBlockingQueue<>(size);

    public void threadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ));
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }
}