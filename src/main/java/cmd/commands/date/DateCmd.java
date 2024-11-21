package cmd.commands.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * "Date" command class.
 * <p/>
 * Show the last modification date of a file.
 */
@CommandLine.Command(
        name = "date",
        description = "Show the last modification date of a file",
        mixinStandardHelpOptions = true)
public class DateCmd implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(DateCmd.class);

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

    @CommandLine.Parameters(index = "0", description = "absolute path of the file")
    private File file;

    public DateCmd() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        // check file path is absolute
        if (!file.isAbsolute()) {
            LOG.error("The path '{}' is not absolute. Please provide an absolute path.\n", file);
            return;
        }

        // check if path exists
        if (!file.exists()) {
            LOG.error("The path '{}' does not exist.\n", file);
            return;
        }

        // check if path is a file
        if (!file.isFile()) {
            LOG.error("The path '{}' is not a file.\n", file);
            return;
        }

        // find last modification timestamp
        try {
            long epochMillis = file.lastModified();
            if (epochMillis == 0L) {
                LOG.error("Failed to read the last modified timestamp of file '{}'.\n", file);
                return;
            }
            Date modificationDate = new Date(epochMillis);
            String formattedDate = dateFormatter.format(modificationDate);
            LOG.info("Last modification timestamp of file '{}' is '{}'\n", file, formattedDate);
        } catch (SecurityException e) {
            LOG.error("No permissions to read the file '{}'.\n", file);
        }
    }
}
