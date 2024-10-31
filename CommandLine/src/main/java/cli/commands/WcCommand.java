package cli.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WcCommand implements Command {
    @Override
    public boolean execute(String[] args) {
        // If no arguments, read from standard input
        if (args.length == 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                int lineCount = 0;
                int wordCount = 0;
                int charCount = 0;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    wordCount += line.split("\\s+").length;
                    charCount += line.length();
                }

                System.out.println(lineCount + " " + wordCount + " " + charCount);
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Usage: wc <file>");
            return false;
        }
        return true;
    }
}