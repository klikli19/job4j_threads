package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(size);
        Runnable target = () -> {
            try {
                Runnable task = tasks.poll();
                task.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(target, "Thread_" + i);
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
    public void shutdown() {
        threads.stream().iterator().forEachRemaining(Thread::interrupt);
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        IntStream.range(0, 10).forEach(i -> {
            try {
                pool.work(() ->
                        System.out.println("Задачу №" + i + " выполнил поток " + Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        pool.shutdown();
    }
}
