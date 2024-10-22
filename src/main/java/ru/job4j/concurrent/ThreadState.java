package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        thread.start();
        second.start();
        while (thread.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(thread.getState());
            System.out.println(second.getState());
        }

        System.out.println("Работа завершена");
    }
}
