package ru.job4j.thread;

/**
 * Исправление ошибки атомарности с использованием synchronized
 */

public class Cache {
    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
