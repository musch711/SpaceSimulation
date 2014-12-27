package com.spacesimulation;


import com.spacesimulation.display.ViewManager;
import com.spacesimulation.domain.EntityManager;

public class Driver 
{

    public static void main(String[] args) {

        // Set the View Frame size (the size of "space")
       ViewManager.setup(600, 600, 600);

       // Start the View Manager
       ViewManager.getInstance();

       // Start the Entity Manager
       EntityManager.getInstance().start();

       EntityManager.getInstance().initialize("CYAN", 3, 100, 5.0, 3, 100, 0.025, 3, 100, 5.0);
       EntityManager.getInstance().initialize("MAGENTA", 3, 100, 5.0, 3, 100, 0.025, 3, 100, 5.0);
       //EntityManager.getInstance().initialize("GREEN", 3, 100, 5.0, 3, 100, 0.025, 3, 100, 5.0);
    }
}
