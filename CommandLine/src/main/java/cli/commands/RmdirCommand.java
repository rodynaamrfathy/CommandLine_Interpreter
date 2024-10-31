package cli.commands;

import java.io.File;

public class RmdirCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No directory path provided.");
            return false;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);


        if (!directory.exists()) {
            System.out.println("Error: Directory does not exist.");
            return false;
        }

        // Check if directory is empty, ignoring hidden files
        if (directory.isDirectory() && directory.listFiles(File::isHidden).length > 0) {
            System.out.println("Error: Directory is not empty (hidden files detected).");
            return false;
        }

        if (directory.delete()) {
            return true;
        } else {
            System.out.println("Error: Failed to remove directory.");
            return false;
        }
    }
}