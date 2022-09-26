package ru.job4j.concurrent;

/**
 * Симуляция блокированной нити
 * с прерыванием нити
 */

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: ... " + process[count]);
            count = count < process.length - 1 ? count + 1 : 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}

