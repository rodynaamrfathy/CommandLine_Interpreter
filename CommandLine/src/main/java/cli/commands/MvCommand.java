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
    
            // Check if the destination path exists
            if (Files.exists(destinationPath)) {
                // If destination is a directory, move the file into the directory
                if (Files.isDirectory(destinationPath)) {
                    destinationPath = destinationPath.resolve(sourcePath.getFileName());
                }
            } else {
                // If destination doesn't exist, it's treated as a new name for renaming
                Path parentDir = destinationPath.getParent();
                if (parentDir != null && Files.notExists(parentDir)) {
                    return false; // Invalid destination directory
                }
            }
    
            // Move or rename the file or directory
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return true; // Move or rename was successful
    
        } catch (IOException e) {
            e.printStackTrace();
            return false; // An error occurred during the move or rename
        }
    }
}