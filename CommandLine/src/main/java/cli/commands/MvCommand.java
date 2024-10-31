// cli/commands/MvCommand.java
package cli.commands;

import java.io.IOException;
import java.nio.file.*;

public class MvCommand implements Command {
    public boolean execute(String[] args) {
        if (args.length != 2) {
            return false; // Expecting exactly two arguments: source and destination
        }
    
        String source = args[0];
        String destination = args[1];
    
        try {
            Path sourcePath = Paths.get(source);
            Path destinationPath = Paths.get(destination);
            
            // Check if source exists
            if (!Files.exists(sourcePath)) {
                return false; // Source does not exist
            }
    
            // Determine if destination is a directory or file
            if (Files.isDirectory(destinationPath)) {
                // If destination is a directory, create a new path for the moved file
                destinationPath = destinationPath.resolve(sourcePath.getFileName());
            } else {
                // Ensure destination's parent directory exists
                if (Files.notExists(destinationPath.getParent())) {
                    return false; // Invalid destination directory
                }
            }
    
            // Move the file
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return true; // Move was successful
    
        } catch (IOException e) {
            e.printStackTrace();
            return false; // An error occurred during the move
        }
    }
    
}