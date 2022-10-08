package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenPutAndPoll() throws InterruptedException {
        List<Integer> rsl = Collections.synchronizedList(new ArrayList<>());
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        var producer = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        );
        var consumer = new Thread(() -> {
            try {
                rsl.add(queue.poll());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        consumer.start();
        consumer.join();
        assertThat(rsl).contains(0);
    }
}