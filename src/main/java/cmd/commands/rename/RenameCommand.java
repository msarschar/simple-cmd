package cmd.commands.mkf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

/**
 * "Rename" command class
 * <p/>
 * Executing the command renames a file
 */
@CommandLine.Command(
        name = "rename",
        description = "Rename a file",
        mixinStandardHelpOptions = true)
public class RenameCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(RenameCommand.class);

    @CommandLine.Parameters(index = "0", description = "Absolute path of the file to be renamed")
    private File source;

    @CommandLine.Parameters(index = "1", description = "Absolute path of the renamed file")
    private File target;

    public RenameCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        // Check if source and target paths are absolute
        if (!source.isAbsolute()) {
            LOG.error("Source file '{}' is not absolute. Please provide an absolute path.\n", source);
            return;
        }
        if (!target.isAbsolute()) {
            LOG.error("Target file '{}' is not absolute. Please provide an absolute path.\n", source);
            return;
        }

        // Check if source exists
        if (!source.exists()) {
            LOG.error("Source file '{}' does not exist.\n", source);
            return;
        }

        // Check if source is a file
        if (!source.isFile()) {
            LOG.error("Source file '{}' is not a file.\n", source);
            return;
        }

        // Check if source file is in the same directory as target file
        if (!source.getParentFile().equals(target.getParentFile())) {
            LOG.error("Source and target directories are not equal. Please use the move command to move files to other directories.\n");
            return;
        }

        // Check if target file already exists
        if (target.exists()) {
            LOG.error("Target file '{}' already exists. Please choose a different name.\n", target);
            return;
        }

        try {
            Files.move(source.toPath(), target.toPath());
            LOG.info("File renamed from {} to {}\n", source, target);
        }
        catch (IOException | InvalidPathException e) {
            LOG.error("Cannot rename file, target path contains not allowed characters.\n");
        }
        catch (SecurityException e) {
            LOG.error("Cannot write new file, no permissions.\n");
        }
    }
}