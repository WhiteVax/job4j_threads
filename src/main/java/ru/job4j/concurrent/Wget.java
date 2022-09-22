package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        var thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ...");
                        for (var index = 0; index <= 100; index++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + index + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        thread.start();
    }
}
