package app.configurer;

import java.util.Properties;
import java.util.Scanner;

public class EmailConfigurer implements Configurer {

    private final String mail_to_key = "email-to";
    private final String mail_from_key = "email-from";
    private final String mail_password_key = "email-from-password";
    private final String app = "app";


    @Override
    public void configure(Properties properties) {
        try (Scanner scanner = new Scanner(System.in)) {
            properties.setProperty(app, "mail");
            System.out.println("Enter a email that could send");
            String email_from = scanner.nextLine().trim();
            properties.setProperty(mail_from_key, email_from);
            System.out.println("password from this email");
            String password = scanner.nextLine().trim();
            properties.setProperty(mail_password_key, password);
            System.out.println("recipient email");
            String email_to = scanner.nextLine().trim();
            properties.setProperty(mail_to_key, email_to);
        }
    }
}
