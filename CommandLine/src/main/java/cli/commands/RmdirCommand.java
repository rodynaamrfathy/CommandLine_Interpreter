package cli.commands;

import java.io.File;

public class RmdirCommand implements Command {

    public RmdirCommand() {}

    @Override
    public boolean execute(String[] args) {
        // Ensure directory name is provided
        if (args.length == 0) {
            System.out.println("Error: No directory path provided.");
            return false;
        }

        String directoryName = args[0];
        String directoryPath = System.getProperty("user.dir") + File.separator + directoryName;
        File directory = new File(directoryPath);

        // Check if the directory exists
        if (!directory.exists()) {
            System.out.println("Error: Directory does not exist.");
            return false;
        }

        // Check if the directory is empty
        if (directory.list() != null && directory.list().length > 0) {
            System.out.println("Error: Directory is not empty.");
            return false;
        }

        // Attempt to delete the directory
        if (directory.delete()) {
            System.out.println("Directory removed successfully.");
            return true;
        } else {
            System.out.println("Error: Failed to remove directory.");
            return false;
        }
    }
}
