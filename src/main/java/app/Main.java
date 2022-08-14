package app;

import app.util.SpyWorker;
import app.util.ProjectConfigurer;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("d")) {
            new SpyWorker().init(Integer.parseInt(args[1]));
        }
        else {
            new ProjectConfigurer().init();
        }
    }
}
