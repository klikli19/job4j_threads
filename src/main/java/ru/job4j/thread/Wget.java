package ru.job4j.thread;
import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long downloadedBytes = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                downloadedBytes += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (downloadedBytes >= speed) {
                    long downloadAt = System.currentTimeMillis();
                    long downloadTime = start - downloadAt;
                    if (downloadTime < 1000) {
                        Thread.sleep(downloadedBytes/speed * 1000 - downloadTime);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }

    private static void validateArgs(int args) {
        if (args != 3) {
            throw new IllegalArgumentException("Недостаточно параметров");
        }
    }
}
