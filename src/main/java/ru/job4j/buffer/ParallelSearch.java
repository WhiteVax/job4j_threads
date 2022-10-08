package ru.job4j.buffer;

import ru.job4j.thread.SimpleBlockingQueue;

/**
 * Предоставлена утилита по поиску файлов.
 * Когда одна нить ищет файлы, вторая нить берёт эти файлы и читает.
 * Эта схема хорошо описывается шаблоном Producer-Consumer. Однако есть один момент.
 * Когда первая нить заканчивает свою работу, потребители переходят в режим wait.
 */

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue =
                new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        var producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        producer.join();
        consumer.interrupt();
    }
}
