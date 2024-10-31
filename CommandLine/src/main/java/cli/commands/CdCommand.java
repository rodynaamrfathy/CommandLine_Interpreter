package cli.commands;

import java.io.File;
import java.io.IOException;

public class CdCommand implements Command {
    private static String previousDir = System.getProperty("user.dir");

    @Override
    public boolean execute(String[] args) {
        // Check if any arguments were provided
        String path = (args != null && args.length > 0) ? args[0] : "";

        if (path.isEmpty()) {
            previousDir = System.getProperty("user.dir"); 
            System.setProperty("user.dir", System.getProperty("user.home"));
            return true;
        } else if (path.equals(".")) {
            return true;
        } else if (path.equals("..")) {
            File currentDir = new File(System.getProperty("user.dir"));
            File parentDir = currentDir.getParentFile();

            if (parentDir != null) {
                previousDir = System.getProperty("user.dir"); 
                try {
                    System.setProperty("user.dir", parentDir.getCanonicalPath());
                } catch (IOException e) {
                    return false;
                }
                return true;
            }
            return false;
        } else if (path.equals("-")) {
            String tempDir = previousDir;
            previousDir = System.getProperty("user.dir");
            System.setProperty("user.dir", tempDir);
            return true;
        } else if (path.equals("~")) {
            previousDir = System.getProperty("user.dir"); 
            System.setProperty("user.dir", System.getProperty("user.home"));
            return true;
        }

        File dir = new File(System.getProperty("user.dir"), path);
        if (dir.exists() && dir.isDirectory()) {
            previousDir = System.getProperty("user.dir"); 
            System.setProperty("user.dir", dir.getAbsolutePath());
            return true;
        }
        return false;
    }
}