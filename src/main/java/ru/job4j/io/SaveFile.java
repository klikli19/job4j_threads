package ru.job4j.io;

import java.io.*;

public class SaveFile {
    private final File file;

    public SaveFile(final File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file);
             BufferedOutputStream out = new BufferedOutputStream(o)) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
