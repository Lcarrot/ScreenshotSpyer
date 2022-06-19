package app.sender;

import java.io.File;
import java.io.IOException;

public interface FileSender {

    boolean sendFile(File file) throws IOException;
}
