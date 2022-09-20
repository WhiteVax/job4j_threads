package ru.job4j.concurrent;

/**
 * Класс показывает состояния нитей
 * @author Vladyslav Bedenko
 * @version 1.0
 */

public class ThreadState {
    public static void main(String[] args) {
        var first = new Thread(() -> {
        });
        var second = new Thread(() -> {
        });
        System.out.println(first.getName());
        System.out.println(second.getName());
        first.start();
        second.start();
        while ((first.getState() != Thread.State.TERMINATED)
                && (second.getState() != Thread.State.TERMINATED)) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        if ((first.getState() == Thread.State.TERMINATED)
                && (second.getState() == Thread.State.TERMINATED)) {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Работа завершена.");
        }
    }
}
