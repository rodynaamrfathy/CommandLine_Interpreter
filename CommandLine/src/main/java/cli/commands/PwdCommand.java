package cli.commands;

import java.io.File;
import java.io.IOException;

public class PwdCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            String canonicalPath = currentDir.getCanonicalPath();
            System.out.println(canonicalPath);
            return true;
        } catch (IOException e) {
            System.out.println("Error retrieving current directory: " + e.getMessage());
            return false;
        }
    }
}
