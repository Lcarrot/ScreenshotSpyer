package app.sender.factory;

import app.sender.MailSender;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.constant.ProjectConstants.MAIL_FROM_KEY;
import static app.constant.ProjectConstants.MAIL_PASSWORD_KEY;
import static app.constant.ProjectConstants.MAIL_TO_KEY;

public class MailSenderFactory implements SenderFactory<MailSender> {

  private final Pattern pattern = Pattern.compile("^.+@([a-z]+)\\..+$");

  @Override
  public MailSender createSender(Properties client_properties) throws IOException {
    String domain;
    Matcher matcher = pattern.matcher(client_properties.getProperty(MAIL_FROM_KEY));
    if (matcher.find()) {
      domain = matcher.group(1);
      System.out.println(domain);
    }
    else {
      throw new IllegalArgumentException("wrong email");
    }
    Properties settings_properties = new Properties();
    settings_properties.load(getClass().getResourceAsStream("/" + domain + "_settings.properties"));
    return MailSender.builder()
        .email_from(client_properties.getProperty(MAIL_FROM_KEY))
        .email_password(client_properties.getProperty(MAIL_PASSWORD_KEY))
        .email_to(client_properties.getProperty(MAIL_TO_KEY))
        .smtp(settings_properties.getProperty("mail.host"))
        .smtpPort(Integer.parseInt(settings_properties.getProperty("mail.port")))
        .ssl(Boolean.parseBoolean(settings_properties.getProperty("mail.ssl")))
        .tls(Boolean.parseBoolean(settings_properties.getProperty("mail.tls")))
        .debug(true)
        .build();
  }
}
