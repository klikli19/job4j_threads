package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content(character -> true);
    }

    public String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }

    public String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file);
             BufferedInputStream in = new BufferedInputStream(i)) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
}
