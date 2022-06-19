package app.util;

import app.configurer.ConfigurerFactory;
import app.sender.Sender;
import app.sender.factory.SendersFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class ProjectConfigurer {

    private final int DELETE_COMMAND = -1;

    public void init() {
        try (Scanner scanner = new Scanner(System.in);
             PropertiesFileWorker fileWorker = new PropertiesFileWorker("user_config") ) {
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
            }
            else {
                fileWorker.deleteFileWhenIsClosed();
            }
        }
        catch (IOException | ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}
