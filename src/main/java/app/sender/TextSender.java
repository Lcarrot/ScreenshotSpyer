package app.sender;

import java.io.IOException;

public interface TextSender {
    boolean sendText(String text) throws IOException;
}
