package cmd.commands.find;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * "Find File" command class
 * <p/>
 * Executing the command finds all files of a given file extension in the current directory.
 */
@Command(
        name = "find",
        description = "Find a files by extension in the current directory",
        mixinStandardHelpOptions = true)
public class FindCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(FindCommand.class);

    @Parameters(index = "0", description = "The file extension to search for (e.g., .txt).", defaultValue = ".txt")
    private String extension;

    public FindCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        try {
            // Check if parameter is a valid extension
            if (!extension.startsWith(".")) {
                LOG.error("Invalid file extension!\n");
            }

            File currentLocation = SimpleCmd.getCurrentLocation();
            // Find all files with given file extension in the current directory
            List<Path> files = Files.find(currentLocation.toPath(), 1, ((path, attributes) ->
                            path.getFileName().endsWith(extension))).collect(Collectors.toList());

            if (files.isEmpty()) {
                // Log and print if no files are found
                LOG.info("No files with the extension {} found.\n", extension);
                LOG.info("Create a new file with the command 'mkd'!\n");
            } else {
                // Log and print the found files
                LOG.info("Found {} file(s) with the extension {}:\n", files.size(), extension);
                files.forEach(file -> {
                    System.out.println(file.toString());
                    LOG.debug("Found file: {}\n", file.toString());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
