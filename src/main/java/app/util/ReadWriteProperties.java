package app.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

public class ReadWriteProperties extends Properties {

  private final Path path;

  public ReadWriteProperties(Path path) throws IOException {
    this.path = path;
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    try (InputStreamReader inputStreamReader = new InputStreamReader(
        new FileInputStream(path.toFile()))) {
      load(inputStreamReader);
    }
    catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public void push(String comment) throws IOException {
    try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
      store(outputStream, comment);
    }
  }

  public Path getFilePath() {
    return path;
  }
}
