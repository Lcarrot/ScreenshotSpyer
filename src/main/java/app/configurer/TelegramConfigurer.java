package app.configurer;

import java.util.Properties;
import java.util.Scanner;

public class TelegramConfigurer implements Configurer {

    private final String TELEGRAM_USER = "tg_username";
    private final String app = "app";

    @Override
    public void configure(Properties properties) {
        try (Scanner scanner = new Scanner(System.in)) {
            properties.setProperty(app, "telegram");
            System.out.println("Send some message to @ProdigyScreenMakerBot and enter your login");
            String tg_user = scanner.nextLine();
            properties.setProperty(TELEGRAM_USER, tg_user);
        }
    }
}
