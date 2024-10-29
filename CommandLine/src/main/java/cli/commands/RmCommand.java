package cli.commands;

import java.io.File;
import java.nio.file.Path;

public class RmCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: rm [-r] <filename>");
            return false;
        }

        boolean recursive = false;
        String filename;

        // Check for the -r flag
        if ("-r".equals(args[0])) {
            recursive = true;
            if (args.length < 2) {
                System.out.println("Usage: rm [-r] <filename>");
                return false;
            }
            filename = args[1].trim();
        } else {
            filename = args[0].trim();
        }

        // Resolve the path based on the current working directory
        Path filePath = Path.of(System.getProperty("user.dir")).resolve(filename).normalize();
        File file = filePath.toFile();

        if (file.exists()) {
            if (recursive && file.isDirectory()) {
                if (deleteDirectory(file)) {
                    System.out.println("Directory " + filePath + " and all contents deleted");
                    return true;
                } else {
                    System.out.println("Error deleting directory " + filePath);
                    return false;
                }
            } else if (!recursive && file.isDirectory()) {
                System.out.println("Cannot delete directory without -r option: " + filePath);
                return false;
            } else if (file.delete()) {
                System.out.println("File " + filePath + " deleted");
                return true;
            } else {
                System.out.println("Error deleting file " + filePath);
                return false;
            }
        } else {
            System.out.println("File " + filePath + " not found");
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
