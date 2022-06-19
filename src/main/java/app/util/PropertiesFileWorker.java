package app.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

public class PropertiesFileWorker extends Properties implements Closeable {

    private final String extension = ".properties";
    private final String fileName;
    private FileOutputStream fileOutputStream;
    private boolean needToDelete = false;

    public PropertiesFileWorker() throws IOException {
        this(UUID.randomUUID().toString());
    }

    public PropertiesFileWorker(String filename) throws IOException {
        super();
        fileName = filename;
        File file = new File(fileName + extension);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileInputStream fileInputStream = new FileInputStream(filename + extension);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream)) {
            load(inputStreamReader);
            fileOutputStream = new FileOutputStream(filename + extension);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void push(String comment) throws IOException {
        store(fileOutputStream, comment);
    }

    public void deleteFileWhenIsClosed() {
        needToDelete = true;
    }

    @Override
    public void close() throws IOException {
        push("close file at " + System.currentTimeMillis());
        fileOutputStream.close();
        if (needToDelete) {
            Files.delete(Paths.get(fileName + extension));
        }
    }
}
