package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class Content {

    private final File file;

    public Content(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content(e -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(e -> e < 0x80);
    }

    public synchronized String content(Predicate<Character> filter) {
        var output = new StringBuilder();
        try (var input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return output.toString();
    }
}
