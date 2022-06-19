package app.screenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class PhotoMaker {

    private final Robot robot;

    public PhotoMaker() throws AWTException {
        robot = new Robot();
    }

    public File takeScreenshot(String format, String name) throws IOException, AWTException {
        String fileName = name + "." + format;
        File file = new File(fileName);
        ImageIO.write(robot.createScreenCapture(
                new Rectangle(
                        Toolkit.getDefaultToolkit().getScreenSize())),
                format, file);
        return file;
    }
}
