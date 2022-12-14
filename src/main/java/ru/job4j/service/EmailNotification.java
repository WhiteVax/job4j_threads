package ru.job4j.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        var subject = String.format("Notification {%s} to email {%s}.",
                user.getUsername(), user.getEmail());
        var body = String.format("Add a new event to {%s}",
                user.getUsername());
        pool.submit(new Thread(
                () -> send(subject, body, user.getEmail())
        ));
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
