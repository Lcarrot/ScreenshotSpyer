package app.sender.factory;

import app.sender.TelegramSender;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Properties;

import static app.constant.ProjectConstants.TELEGRAM_BOT_TOKEN;
import static app.constant.ProjectConstants.TELEGRAM_KEY;
import static app.constant.ProjectConstants.TELEGRAM_USER;

public class TelegramSenderFactory implements SenderFactory<TelegramSender> {

    @Override
    public TelegramSender createSender(Properties properties) throws IOException, ParseException {
        if (properties.getProperty(TELEGRAM_KEY) != null) {
            return new TelegramSender(TELEGRAM_BOT_TOKEN, Integer.parseInt(properties.getProperty(TELEGRAM_KEY)));
        } else {
            TelegramSender sender = new TelegramSender(TELEGRAM_BOT_TOKEN, properties.getProperty(TELEGRAM_USER));
            properties.setProperty(TELEGRAM_KEY, String.valueOf(sender.getClientId()));
            return sender;
        }
    }
}
