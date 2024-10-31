package cli.commands;

import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command {

    public TouchCommand() {}

    @Override
    public boolean execute(String[] args) {
        // Check if args is null or empty
        if (args == null || args.length == 0) {
            return false;
        }

        String fileName;
        File directory;

        // If one argument is provided, use the current directory
        if (args.length == 1) {
            fileName = args[0];
            directory = new File(System.getProperty("user.dir")); // Use current directory
        } 
        // If two arguments are provided, use the provided directory path
        else {
            fileName = args[0]; // First argument is the filename
            String dirPath = args[1]; // Second argument is the directory path
            directory = new File(dirPath);
            // Debug: Check if directory exists
        }

        // Check if the directory exists if two arguments were provided
        if (args.length > 1 && !directory.exists()) {
            return false; // Prevent file creation if directory doesn't exist
        }

        File file = new File(directory, fileName);

        try {
            if (file.exists()) {
                // Update the file's last modified timestamp
                boolean updated = file.setLastModified(System.currentTimeMillis());
                
                return updated;
            } else {
                // Create the file only if the directory exists
                if (args.length > 1 || directory.exists()) {
                    if (file.createNewFile()) {
                        return true;
                    } else {
                        System.out.println("Error: Failed to create file.");
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("touch: cannot touch " + file.getAbsolutePath() + ": " + e.getMessage());
            return false;
        }
    }
}
