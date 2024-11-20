package cmd.commands.mkf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * "Make File" command class
 * <p/>
 * Executing the command creates a new File asn
 * If the directory already exists, warn & ask for a new name
 */
@CommandLine.Command(
        name = "mkf",
        description = "Create File",
        mixinStandardHelpOptions = true)
public class MkfCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(MkfCommand.class);

    @CommandLine.Parameters(index = "0", description = "absolute path of the file that should be created")
    private File file;

    public MkfCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {

        //If directory is not absolute
        if (!file.isAbsolute()){
            LOG.error("The directory '{}' is not absolute. Please provide a different path.\n",file);
            return;
        }
        //If file does not have an extension
        if (!file.getName().contains(".")) {
            file = new File(file.getParent(),file.getName()+ ".txt");
        }
        //If directory does not exist
        File parent = file.getParentFile();
        if (parent == null) {
            LOG.error("Unable to create a new file system at location '{}'.\n", file);
            return;
        }
        if (!parent.exists()) {
            LOG.error("No such parent directory: '{}'. Please create parent directory first.\n", parent);
            return;
        }

        if (file.exists()){
            LOG.error("File already exists.\n",file);
            return;
        }
        else {
            try {
                file.createNewFile();
                LOG.info("File was created successfully at: '{}'\n", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}