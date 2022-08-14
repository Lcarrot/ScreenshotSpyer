package app.configurer;

import java.util.Properties;
import java.util.Scanner;

import static app.constant.ProjectConstants.APP;
import static app.constant.ProjectConstants.MAIL_FROM_KEY;
import static app.constant.ProjectConstants.MAIL_PASSWORD_KEY;
import static app.constant.ProjectConstants.MAIL_TO_KEY;

public class EmailConfigurer implements Configurer {

    @Override
    public void configure(Properties properties) {
        try (Scanner scanner = new Scanner(System.in)) {
            properties.setProperty(APP, "mail");
            System.out.println("Enter a email that could send");
            String email_from = scanner.nextLine().trim();
            properties.setProperty(MAIL_FROM_KEY, email_from);
            System.out.println("password from this email");
            String password = scanner.nextLine().trim();
            properties.setProperty(MAIL_PASSWORD_KEY, password);
            System.out.println("recipient email");
            String email_to = scanner.nextLine().trim();
            properties.setProperty(MAIL_TO_KEY, email_to);
        }
    }
}
