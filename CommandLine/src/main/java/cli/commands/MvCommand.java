package cli.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MvCommand implements Command {

    public MvCommand() {}

    @Override
    public boolean execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Missing source or destination path.");
            return false;
        }

        // Ensure the command line inputs are trimmed to avoid leading/trailing spaces
        String sourceArg = args[0].trim();
        String destinationArg = args[1].trim();

        // Resolve paths based on the current working directory
        Path sourcePath = Path.of(System.getProperty("user.dir")).resolve(sourceArg).normalize();
        Path destinationPath = Path.of(System.getProperty("user.dir")).resolve(destinationArg).normalize();

        // Debugging output for paths
        System.out.println("Source Path: " + sourcePath);
        System.out.println("Destination Path: " + destinationPath);

        // Check if the source exists
        if (!Files.exists(sourcePath)) {
            System.out.println("Error: Source file or directory does not exist.");
            return false;
        }

        // If the destination is a directory, move the source into it
        if (Files.isDirectory(destinationPath)) {
            destinationPath = destinationPath.resolve(sourcePath.getFileName());
        } else {
            // Ensure the parent directory of the destination exists
            if (!Files.exists(destinationPath.getParent())) {
                System.out.println("Error: Destination directory does not exist.");
                return false;
            }
        }

        try {
            // If the destination already exists, prompt the user
            if (Files.exists(destinationPath)) {
                System.out.println("Warning: Destination already exists. Overwriting: " + destinationPath);
                Files.delete(destinationPath); // Attempt to delete the existing file
            }

            // Move the file or directory, allowing overwriting if it exists
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved " + sourcePath + " to " + destinationPath);
            return true;
        } catch (IOException e) {
            System.out.println("Error moving file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for more detailed error information
            return false;
        }
    }
}
