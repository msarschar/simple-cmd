package cmd.commands.cd;

import cmd.SimpleCmd;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Command(
        name = "cd",
        description = "Change directory",
        mixinStandardHelpOptions = true)
public class ChangeCommand implements Runnable {

        @CommandLine.Parameters(index = "0", description = "path to navigate to")
        private String target;

        public ChangeCommand() {
            /* intentionally empty */
        }

        @Override
        public void run() {
            try {
                File targetFile = new File(target);
                System.setProperty("user.dir", target);

                System.out.println("Current directory: " + System.getProperty("user.dir"));
                SimpleCmd.setCurrentLocation(targetFile);
                System.out.println("Directory changed to: " + SimpleCmd.getCurrentLocation());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

}
