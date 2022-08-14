package app.util;

import app.configurer.ConfigurerFactory;
import app.sender.Sender;
import app.sender.factory.SendersFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import static app.constant.ProjectConstants.DELETE_COMMAND;
import static app.constant.ProjectConstants.USER_CONFIG_FILE_NAME;

public class ProjectConfigurer {

  public void init() {
    try (Scanner scanner = new Scanner(System.in)) {
      ReadWriteProperties fileWorker = new ReadWriteProperties(Paths.get(USER_CONFIG_FILE_NAME));
      System.out.println("Enter a number, how send a screenshots \n Telegram '1', email '2'" +
                         " or clear configuration '-1'");
      int command = scanner.nextInt();
      Sender sender;
      if (command != DELETE_COMMAND) {
        new ConfigurerFactory()
            .createConfigurer(command)
            .ifPresent(configurer -> configurer.configure(fileWorker));
        sender = new SendersFactory(fileWorker)
            .getSender(command)
            .orElseThrow(IllegalStateException::new);
        sender.sendText("hello-world");
        fileWorker.push("Edited in " + LocalDateTime.now());
      }
      else {
        Files.delete(fileWorker.getFilePath());
      }
    }
    catch (IOException | ParseException e) {
      throw new IllegalStateException(e);
    }
  }
}
