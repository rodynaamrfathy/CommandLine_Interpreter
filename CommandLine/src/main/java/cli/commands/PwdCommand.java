package cli.commands;

import java.io.File;
import java.io.IOException;

public class PwdCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        try {
            String userDir = System.getProperty("user.dir");
            if (userDir == null) {
                System.out.println("User directory is null");
                return false;
            }
            File currentDir = new File(userDir);
            String canonicalPath = currentDir.getCanonicalPath();
            System.out.println(canonicalPath);
            return true;
        } catch (IOException e) {
            System.out.println("Error retrieving current directory: " + e.getMessage());
            return false;
        }
    }

}
