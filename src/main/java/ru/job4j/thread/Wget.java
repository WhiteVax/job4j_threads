package ru.job4j.thread;

import java.io.*;
import java.net.URL;

/**
 * Класс выполняет скачивание файла с ограничением
 */

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    private static void validate(String[] array, String url, int speed) {
        if (array.length != 2) {
            throw new IllegalArgumentException("Wrong args.");
        } else if (!url.matches(
                "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$")) {
            throw new IllegalArgumentException(String.format("Wrong url : %s", url));
        } else if (!(speed > 0 && speed < 100000)) {
            throw new IllegalArgumentException(String.format("Wrong speed : %d", speed));
        }
    }

    @Override
    public void run() {
        try (var inStream = new BufferedInputStream(new URL(url).openStream());
             var outStream = new FileOutputStream("rsl.xml")) {
            byte[] dataBuffer = new byte[1024];
            int byteRead;
            long start = System.currentTimeMillis();
            while ((byteRead = inStream.read(dataBuffer, 0, 1024)) != -1) {
                long interval = System.currentTimeMillis() - start;
                if (interval < speed) {
                    Thread.sleep(1000);
                } else {
                    Thread.sleep(speed);
                }
                outStream.write(dataBuffer, 0, byteRead);
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args, args[0], Integer.parseInt(args[1]));
        var url = args[0];
        var speed = Integer.parseInt(args[1]);
        var wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
