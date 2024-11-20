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

    @CommandLine.Parameters(index = "0", description = "absolute path of the directory where the new file should be created")
    private String filePath;

    public MkfCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        Path path = Path.of(filePath);
        //If directory is not absolute
        if (!path.isAbsolute()){
            LOG.error("The directory '{}' is not absolute. Please provide a different path.\n",filePath);
            return;
        }
        //If file does not have an extension
        //Takes file that was last written in path
        Path fileName = path.getFileName();
        if (!fileName.toString().contains(".")) {
            path = path.resolveSibling(fileName.toString() + ".txt");
        }

        if (Files.exists(path)){
            Scanner scanner = new Scanner(System.in);
            System.out.println("The file already exists at: " + path.toAbsolutePath());
            System.out.print("Please use another name.\n");
            return;
        }
        else {
            try {
                Files.createFile(path);
                System.out.println("File was created successfully at: "+ path.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }









    }
}