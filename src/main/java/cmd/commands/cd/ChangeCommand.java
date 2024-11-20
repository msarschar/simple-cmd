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
public class ChangeCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(ChangeCommand.class);

    @CommandLine.Parameters(index = "0", description = "path to navigate to")
    private File target;

    public ChangeCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {

        if(!target.exists()){
            LOG.error("Path '{}' does not exist. Please provide an existing path!", target);
            return;
        }

        if(!target.isDirectory()){
            LOG.error("Path '{}' is not a directory. Please provide a directory path!", target);
            return;
        }

        if(!target.isAbsolute()){
            LOG.error("Path '{}' is not an absolute path. Please provide an absolute path!", target);
            return;
        }

        try {
            System.setProperty("user.dir", target.getAbsolutePath());

            LOG.info("Current directory: {}", System.getProperty("user.dir"));
            SimpleCmd.setCurrentLocation(target);
            LOG.info("Directory changed to: " + SimpleCmd.getCurrentLocation());
        } catch (Exception e) {
            LOG.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
