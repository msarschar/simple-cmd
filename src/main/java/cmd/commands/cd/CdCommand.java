package cmd.commands.cd;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;

@Command(
        name = "cd",
        description = "Change directory",
        mixinStandardHelpOptions = true)
public class CdCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(CdCommand.class);

    @CommandLine.Parameters(index = "0", description = "path to navigate to")
    private File target;

    public CdCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {

        // check if the path exists
        if(!target.exists()){
            LOG.error("Path '{}' does not exist. Please provide an existing path!", target);
            return;
        }

        // check if the path is a directory
        if(!target.isDirectory()){
            LOG.error("Path '{}' is not a directory. Please provide a directory path!", target);
            return;
        }

        // check if the path is absolute
        if(!target.isAbsolute()){
            LOG.error("Path '{}' is not an absolute path. Please provide an absolute path!", target);
            return;
        }

        try {

            LOG.info("Current directory: {}", System.getProperty("user.dir"));
            // change the current directory
            System.setProperty("user.dir", target.getAbsolutePath());

            // update the current location
            SimpleCmd.setCurrentLocation(target);
            LOG.info("Directory changed to: " + SimpleCmd.getCurrentLocation());
        } catch (Exception e) {
            LOG.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
