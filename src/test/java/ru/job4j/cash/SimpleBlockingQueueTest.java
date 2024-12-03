package ru.job4j.cash;

import org.junit.jupiter.api.Test;
import ru.job4j.SimpleBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueTest {
    @Test
    void whenProductAndConsumerThreadsTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        int[] result = new int[2];
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                        queue.offer(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        result[0] = queue.poll();
                        result[1] = queue.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(result[0]).isEqualTo(1);
        assertThat(result[1]).isEqualTo(5);
    }
}
