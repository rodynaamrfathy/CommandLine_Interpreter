package cli.commands;

import java.io.File;
import java.text.SimpleDateFormat;

public class LsCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir);
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in the current directory.");
            return true;  // Command executed successfully, even if no files are present
        }

        // Default to showing non-hidden files
        boolean showHidden = false;
        boolean showLongFormat = false;
        boolean showRecursive = false;

        // Check for options
        for (String arg : args) {
            switch (arg) {
                case "-r":
                    showRecursive = true;
                    break;
                case "-a":
                    showHidden = true;  // Show hidden files
                    break;
                case "-l":
                    showLongFormat = true;  // Show long format
                    break;
                default:
                    System.out.println("Option not available for ls: " + arg);
                    return false;  // Command failed due to invalid option
            }
        }

        // Display files
        System.out.println("Contents of directory: " + currentDir);
        listDirectoryContents(directory, showHidden, showLongFormat);

        // If recursive option is set, display contents of subdirectories
        if (showRecursive) {
            System.out.println("\nContents of subdirectories:");
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory " + file.getName() + ": ");
                    listDirectoryContents(file, showHidden, showLongFormat); // Call to display contents of subdirectory
                }
            }
        }

        return true;  // Command executed successfully
    }

    private void listDirectoryContents(File dir, boolean showHidden, boolean showLongFormat) {
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in directory: " + dir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            // Check if the file should be shown based on the showHidden flag
            if (!showHidden && file.isHidden()) {
                continue; // Skip hidden files
            }
            // Skip files that start with a dot (.) for normal ls command
            if (!showHidden && file.getName().startsWith(".")) {
                continue; // Skip hidden files (those starting with a dot)
            }

            // Print file information
            if (showLongFormat) {
                printFileDetails(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }

    private void printFileDetails(File file) {
        String fileType = file.isDirectory() ? "d" : "-";
        String permissions = (file.canRead() ? "r" : "-") +
                             (file.canWrite() ? "w" : "-") +
                             (file.canExecute() ? "x" : "-");

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        String lastModified = sdf.format(file.lastModified());
        long size = file.length();

        System.out.printf("%s%s %10d %s %s%n", fileType, permissions, size, lastModified, file.getName());
    }
}
