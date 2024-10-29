package cli.commands;

import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command {

    public TouchCommand() {}

    @Override
    public boolean execute(String[] args) {
        // Ensure file path is provided
        if (args.length == 0) {
            System.out.println("Error: No file path provided.");
            return false;
        }

        String fullPath = args[0];

        // Split the path into directory and file name
        String[] pathParts = fullPath.split("/");
        String fileName = pathParts[pathParts.length - 1]; // Last part is the file name
        String directoryPath = String.join("/", java.util.Arrays.copyOf(pathParts, pathParts.length - 1)); // All parts except last are the directory path

        // Create the full file path
        File directory = new File(System.getProperty("user.dir") + File.separator + directoryPath);
        File file = new File(directory, fileName);

        try {
            // Check if the file exists
            if (file.exists()) {
                // If it exists, update the last modified time
                boolean updated = file.setLastModified(System.currentTimeMillis());
                if (updated) {
                    System.out.println("File updated successfully.");
                } else {
                    System.out.println("Failed to update file.");
                }
                return updated;
            } else {
                // If it does not exist, create the directory (if needed) and then the file
                if (!directory.exists() && !directory.mkdirs()) {
                    System.out.println("Error: Failed to create directory path.");
                    return false;
                }
                
                if (file.createNewFile()) {
                    System.out.println("File created successfully.");
                    return true;
                } else {
                    System.out.println("Error: Failed to create file.");
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("touch: cannot touch " + fullPath + ": No such file or directory.");
            return false;
        }
    }
}
