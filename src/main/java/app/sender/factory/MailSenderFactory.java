package app.sender.factory;

import app.sender.MailSender;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailSenderFactory implements SenderFactory<MailSender> {

    private final String email_to = "email-to";
    private final String email_from = "email-from";
    private final String email_password = "email-from-password";

    private final Pattern pattern = Pattern.compile("^.+@([a-z]+)\\..+$");

    @Override
    public MailSender createSender(Properties client_properties) throws IOException {
        String domain;
        Matcher matcher = pattern.matcher(client_properties.getProperty(email_from));
        if (matcher.find()) {
            domain = matcher.group(1);
            System.out.println(domain);
        } else {
            throw new IOException("wrong email");
        }
        Properties settings_properties = new Properties();
        settings_properties.load(getClass().getResourceAsStream("/" + domain + "_settings.properties"));
        return MailSender.builder()
                .smtp(settings_properties.getProperty("mail.host"))
                .email_from(client_properties.getProperty(email_from))
                .email_password(client_properties.getProperty(email_password))
                .email_to(client_properties.getProperty(email_to))
                .smtpPort(Integer.parseInt(settings_properties.getProperty("mail.port")))
                .ssl(Boolean.parseBoolean(settings_properties.getProperty("mail.ssl")))
                .tls(Boolean.parseBoolean(settings_properties.getProperty("mail.tls")))
                .debug(true)
                .build();
    }
}
