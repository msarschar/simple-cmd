package cmd.commands.mkd;

import cmd.commands.del.DelCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;

/**
 * "Make Directory" command class
 * <p/>
 * Executing the command creates a new directory at the given absolute path.
 * If the directory already exists, warn & ask for a new name
 */
@Command(
        name = "mkd",
        description = "Make Directory",
        mixinStandardHelpOptions = true)
public class MkdCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(MkdCommand.class);

    @Parameters(index = "0", description = "absolute path of the directory")
    private File target;

    public MkdCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        // Check if directory exists
        if (target.exists()) {
            LOG.error("The directory '{}' already exists. Please provide a different path.\n", target);
            return;
        }

        // Check if path is absolute
        if (!target.isAbsolute()) {
            LOG.error("The path must be absolute. A relative path '{}' was specified.\n", target);
            return;
        }

        // Check if parent exists
        File parent = target.getParentFile();
        if (parent == null) {
            LOG.error("Unable to create a new file system at location '{}'.\n", target);
            return;
        }
        if (!parent.exists()) {
            LOG.error("No such parent directory: '{}'. Please create parent directory first.\n", parent);
            return;
        }

        // Create new directory
        try {
            if (target.mkdir()) {
                LOG.info("Directory '{}' successfully created.\n", target);
            }
            else {
                LOG.error("Failed to create directory '{}'. Directory name contains forbidden characters.\n", target);
            }
        } catch (SecurityException e) {
            LOG.error("No permissions to create directory '{}'.\n", target);
            e.printStackTrace();
        }
    }
}
