package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread abc = new Thread(
                () -> {
                    try {
                        int i;
                        for (i = 0; i <= 100; i++) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            );
        abc.start();
    }
}
