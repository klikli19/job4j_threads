package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
        progress.join();
    }

    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/' };
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[i++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (i == process.length) {
                i = 0;
            }
        }
    }
}
