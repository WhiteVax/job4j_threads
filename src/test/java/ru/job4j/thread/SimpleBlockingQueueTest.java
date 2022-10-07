package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenPutAndPoll() throws InterruptedException {
        List<Integer> rsl = Collections.synchronizedList(new ArrayList<>());
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        var producer = new Thread(
                () -> IntStream.range(0, 3).forEach(queue::offer)
        );
        var consumer = new Thread(() -> rsl.add(queue.poll()));
        producer.start();
        consumer.start();
        consumer.join();
        assertThat(rsl).contains(0);
    }
}