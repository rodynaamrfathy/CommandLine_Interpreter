package cli.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CatCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: cat <filename>");
            return false; // Command execution failed due to missing arguments
        }

        String filename = args[0];
        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir, filename);

        // Check if the file exists and is a file
        if (!file.exists() || !file.isFile()) {
            System.out.println("File not found: " + filename);
            return false; // Command execution failed due to file not found
        }

        // Read and print the file content
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return false; // Command execution failed due to an error
        }

        return true; // Command executed successfully
    }
}
