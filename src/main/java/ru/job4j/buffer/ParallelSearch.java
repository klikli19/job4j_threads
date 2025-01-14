package ru.job4j.buffer;

import java.util.concurrent.atomic.AtomicBoolean;
import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        AtomicBoolean flag = new AtomicBoolean(Boolean.TRUE);
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(1);
        final Thread consumer = new Thread(
        () -> {
            while (flag.get()) {
            try {
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("consumer end");
        Thread.currentThread().interrupt();}
        );
    final Thread producer = new Thread(
            () -> {
                for (int index = 0; index != 5; index++) {
                    try {
                        queue.offer(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                flag.set(Boolean.FALSE);
                System.out.println("End");
                Thread.currentThread().interrupt();
            }
    );
        consumer.start();
        producer.start();
        consumer.join();
        System.out.println("Stop");
    }
}
