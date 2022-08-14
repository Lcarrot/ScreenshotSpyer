package app.configurer;

import java.util.Properties;
import java.util.Scanner;

import static app.constant.ProjectConstants.APP;
import static app.constant.ProjectConstants.TELEGRAM_USER;

public class TelegramConfigurer implements Configurer {

    @Override
    public void configure(Properties properties) {
        try (Scanner scanner = new Scanner(System.in)) {
            properties.setProperty(APP, "telegram");
            System.out.println("Send some message to @ProdigyScreenMakerBot and then enter your login");
            String tg_user = scanner.nextLine();
            properties.setProperty(TELEGRAM_USER, tg_user);
        }
    }
}
