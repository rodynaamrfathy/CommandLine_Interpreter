package cli.commands;

import java.io.File;
import java.nio.file.Path;

public class RmCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 0) {
            return false;
        }

        boolean recursive = false;
        String filename;

        // Check for the -r flag
        if ("-r".equals(args[0])) {
            recursive = true;
            if (args.length < 2) {
                return false;
            }
            filename = args[1].trim();
        } else {
            filename = args[0].trim();
        }

        // Resolve the path based on the argument directly
        Path filePath = Path.of(filename).normalize(); // Updated to accept absolute or relative paths
        File file = filePath.toFile();

        // Check if file or directory exists before proceeding
        if (!file.exists()) {
            return false;
        }

        if (recursive && file.isDirectory()) {
            if (deleteDirectory(file)) {
                return true;
            } else {
                return false;
            }
        } else if (!recursive && file.isDirectory()) {
            return false;
        } else if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    // Helper method to delete a directory recursively
    private boolean deleteDirectory(File dir) {
        File[] contents = dir.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory()) {
                    if (!deleteDirectory(file)) {
                        return false;
                    }
                } else if (!file.delete()) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
