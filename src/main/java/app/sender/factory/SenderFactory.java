package app.sender.factory;

import app.sender.Sender;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Properties;

public interface SenderFactory<T extends Sender> {

    T createSender(Properties properties) throws IOException, ParseException;
}
