package ru.job4j.pool;

import ru.job4j.thread.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализация пула
 * клиент берёт ресурс из пула, выполняет свою работу и возвращает обратно в пул
 * инициализация пула по кол-ву ядер
 */

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks =
            new SimpleBlockingQueue<>(size);

    /**
     * метод для каждого потока процессора добавляет нить, задачу берёт с
     * очереди
     */
    public void threadPool() {
        for (int i = 0; i < size; i++) {
            var thread = (new Thread(
                    () -> {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            ));
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * метод добавляет задачу
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * метод завершает все нити
     */
    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var threadPools = new ThreadPool();
        var size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threadPools.work(() -> System.out.println("Execute: "
                    + Thread.currentThread().getName()));
        }
        threadPools.threadPool();
        threadPools.shutdown();
    }
}