package app;

import app.util.DaemonWorker;
import app.util.ProjectConfigurer;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("d")) {
            new DaemonWorker().init(Integer.parseInt(args[1]));
        }
        else {
            new ProjectConfigurer().init();
        }
    }
}
