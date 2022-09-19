package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        var another = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        another.start();
        System.out.println(Thread.currentThread().getName());

        var second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        second.start();
    }
}
