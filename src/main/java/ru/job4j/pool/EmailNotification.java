package ru.job4j.pool;

import ru.job4j.ref.User;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = String.format("Уведомление о письме", user.getName(), user.getEmail());
        String body = String.format("Добавлено новое событие ", user.getName());
        executor.submit(() -> send(subject, body, user.getEmail()));
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        User user = new User("Anna", "klikli@mail.com");
        EmailNotification notification = new EmailNotification();
        notification.emailTo(user);
        notification.close();
    }
}
