package app.sender.factory;

import app.sender.Sender;
import app.util.ReadWriteProperties;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Optional;

public class SendersFactory {

    private final ReadWriteProperties fileWorker;

    public SendersFactory(ReadWriteProperties fileWorker) {
        this.fileWorker = fileWorker;
    }

    public Optional<Sender> getSender(int program) throws IOException, ParseException {
        Sender sender;
        switch (program) {
            case 1:
                sender = new TelegramSenderFactory().createSender(fileWorker);
                break;
            case 2:
                sender = new MailSenderFactory().createSender(fileWorker);
                break;
            default:
                sender = null;
        }
        return Optional.ofNullable(sender);
    }
}
