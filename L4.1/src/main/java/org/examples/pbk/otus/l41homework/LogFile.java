package org.examples.pbk.otus.l41homework;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.WRITE;

public class LogFile {
    private Path file;
    private final Charset charset = Charset.forName("US-ASCII");

    public LogFile(String directory, String fileName) {
        Path path = Paths.get(directory, fileName);
        try {
            if (Files.notExists(path)) Files.createFile(path);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        this.file = path;
    }

    public void writeLogEntry(String logEntry) {
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, WRITE, APPEND)) {
            writer.write(logEntry, 0, logEntry.length());
            writer.newLine();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
