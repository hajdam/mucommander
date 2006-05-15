
package com.mucommander;

import com.mucommander.conf.ConfigurationManager;

/**
 * The run method of this thread is called when the program shuts down, either because
 * the user chose to quit the program or because the program was interrupted by a logoff.
 *
 * @author Maxence Bernard
 */
public class ShutdownHook extends Thread {

    private static boolean shutdownTasksPerformed;

    public ShutdownHook() {
        super("com.mucommander.ShutDownHook's thread");
    }


    public static void initiateShutdown() {
        if(Debug.ON) Debug.trace("shutting down");

        if(PlatformManager.getJavaVersion()>= PlatformManager.JAVA_1_4) {
            // No need to call System.exit() under Java 1.4, application will naturally exit
            // when no there is no more window showing and no non-daemon thread still running.
            // However, natural application death will not trigger ShutdownHook so we need to explicitly
            // perform shutdown tasks.
            performShutdownTasks();
        }
        else {
            // System.exit() will trigger ShutdownHook and perform shutdown tasks
            System.exit(0);     
        }
    }
    

    /**
     * Called by the VM when the program shuts down, this method writes the configuration.
     */
    public void run() {
        if(Debug.ON) Debug.trace("called");

        performShutdownTasks();
    }


    /**
     * Performs tasks before shut down, such as writing the configuration file.
     */
    private synchronized static void performShutdownTasks() {
        // Return if shutdown tasks have already been performed
        if(shutdownTasksPerformed)
            return;
        
        // Saves preferences
        ConfigurationManager.writeConfiguration();
    
        // Shutdown tasks should only be performed once
        shutdownTasksPerformed = true;
    }
}
