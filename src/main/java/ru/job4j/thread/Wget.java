package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Класс выполняет скачивание файла с ограничением
 */

public class Wget implements Runnable {
    private static final String TEMPLATE_URL =
            "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    private final String url;
    private final int speed;
    private final String outFile;

    public Wget(String url, int speed, String outFile) {
        this.url = url;
        this.speed = speed;
        this.outFile = outFile;
    }

    private static void validate(String[] array) {
        if (array.length != 3) {
            throw new IllegalArgumentException("Wrong args.");
        } else if (!array[0].matches(TEMPLATE_URL)) {
            throw new IllegalArgumentException(String.format("Wrong url : %s",
                    array[0]));
        } else if (Integer.parseInt(array[1]) < 0) {
            throw new IllegalArgumentException(String.format("Wrong speed : %s",
                    array[1]));
        } else if (!Files.exists(Path.of(array[2]))) {
            throw new IllegalArgumentException(String.format("Wrong out file %s.",
                    array[2]));
        }
    }

    @Override
    public void run() {
        try (var inStream = new BufferedInputStream(new URL(url).openStream());
             var outStream = new FileOutputStream(outFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = inStream.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long interval = System.currentTimeMillis() - start;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
                outStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        var url = args[0];
        var speed = Integer.parseInt(args[1]);
        var out = args[2];
        var wget = new Thread(new Wget(url, speed, out));
        wget.start();
        wget.join();
    }
}
