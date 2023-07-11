package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Не блокирующий кеш, реализация с помощью CAS
 */
@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Метод для добавления объектов
     * putIfAbsent выполняет методы сравнения и вставки, делает это атомарно
     * @param model объект для добавления
     * @return true/false
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Метод для обновления объектов
     * если версии не равны то выкинет ошибку, если равны увеличит версию на
     * 1 и заменит объект
     * computeIfPresent атомарная операция, по аналогии putIfAbsent
     * @param model объект для обновления
     * @return true/false
     */
    public boolean update(Base model) {
        var base = memory.computeIfPresent(model.getId(),
                (key, value) -> {
            if (value.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            var temp = new Base(model.getId(), model.getVersion() + 1);
            temp.setName(model.getName());
            return temp;
        });
        return base != null;
    }

    /**
     * remove() - атомарный по аналогии putIfAbsent, только для удаления
     * @param model получает объект для удаления
     */
    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
