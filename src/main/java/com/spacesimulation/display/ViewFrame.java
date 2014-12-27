package com.spacesimulation.display;

public interface ViewFrame {

    int getXSize();

    int getYSize();

    int getZSize();

    void stop();

    void pause();

    void resume();

    boolean isPaused();

    boolean isStopped();

    void updateItem(ConsoleItem ci);

    void removeItem(String id);

    int numItems();

    void clearAllItems();

    void updateInfo(String s);

    void toggleGrid();
}
