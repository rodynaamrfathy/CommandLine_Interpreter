package cli.commands;

import java.io.File;

public class MkdirCommand implements Command {

    public MkdirCommand() {}

    @Override
    public boolean execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No directory path provided.");
            return false;
        }

        boolean createParents = false;
        String directoryPath;

        // Check if the first argument is "-p" for creating parent directories
        if (args[0].equals("-p")) {
            createParents = true;
            if (args.length > 1) {
                directoryPath = args[1];
            } else {
                System.out.println("Error: No directory path provided after -p.");
                return false;
            }
        } else {
            directoryPath = args[0];
        }

        // Use a platform-independent base path (current working directory)
        File directory = new File(System.getProperty("user.dir"), directoryPath);

        // Attempt to create the directory or directories based on the flag
        if (createParents) {
            // Create parent directories if the -p flag is set
            if (directory.mkdirs()) {
                System.out.println("Directories created successfully");
                return true;
            } else {
                System.out.println("Failed to create directories (may already exist or invalid path)");
                return false;
            }
        } else {
            // Check if the directory already exists
            if (directory.exists()) {
                System.out.println("Directory already exists");
                return true;
            }
            // Attempt to create only the last directory in the path
            if (directory.mkdir()) {
                System.out.println("Directory created successfully");
                return true;
            } else {
                System.out.println("Failed to create directory (parent directory might not exist)");
                return false;
            }
        }
    }
}
