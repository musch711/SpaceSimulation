package com.spacesimulation.display;

public class ViewManager {

    private volatile static ViewManager instance;
    private ViewFrame viewFrameDelegate;
    private static int xSize;
    private static int ySize;
    private static int zSize;
    private static boolean setup = false;

    public static ViewManager getInstance() {
        if (!setup) {
            System.err.println("ViewManager 'setup' must be called before invoking the ViewManager");
            return null;
        }
        if (instance == null) {
            synchronized (ViewManager.class) {
                if (instance == null) // Double-Check!
                {
                    instance = new ViewManager();
                }
            }
        }
        return instance;
    }

    public static void setup(int x, int y, int z) {
        xSize = x;
        ySize = y;
        zSize = z;
        setup = true;
    }

    private ViewManager() {

        viewFrameDelegate = ViewFrameFactory.create();
    }

    public void toggleGrid() {
        viewFrameDelegate.toggleGrid();
    }

    public static int getXSize() {
        return xSize;
    }

    public static int getYSize() {
        return ySize;
    }

    public static int getZSize() {
        return zSize;
    }

    public void updateInfo(String s) {
        viewFrameDelegate.updateInfo(s);
    }

    public void pause() {
        viewFrameDelegate.pause();
    }

    public void resume() {
        viewFrameDelegate.resume();
    }

    public boolean isPaused() {
        return viewFrameDelegate.isPaused();
    }

    public boolean isStopped() {
        return viewFrameDelegate.isStopped();
    }

    public void stop() {
        viewFrameDelegate.stop();
    }

    public void updateItem(ConsoleItem ci) {
        viewFrameDelegate.updateItem(ci);
    }

    public void removeItem(String id) {
        viewFrameDelegate.removeItem(id);
    }

    public int numItems() {
        return viewFrameDelegate.numItems();
    }

    public void clearAllItems() {
        viewFrameDelegate.clearAllItems();
    }
}
