package app.listener;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import java.util.concurrent.ExecutorService;

public class RunnableWorkerMouseListener implements NativeMouseMotionListener {

    private final Runnable runnable;
    private final ExecutorService service;
    private final int delay;
    private final int xBorder;
    private final int yBorder;

    private long lastTime = System.currentTimeMillis();

    public RunnableWorkerMouseListener(Runnable runnable, int delay, int xBorder, int yBorder, ExecutorService service) {
        this.runnable = runnable;
        this.delay = delay;
        this.service = service;
        this.xBorder = xBorder;
        this.yBorder = yBorder;
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        long time = System.currentTimeMillis();
        if (nativeMouseEvent.getX() <= xBorder && nativeMouseEvent.getY() <= yBorder && (time - lastTime > delay)) {
            service.submit(runnable);
            System.out.println("Screen was sent");
            lastTime = time;
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
    }
}
