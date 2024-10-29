package cli;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import cli.commands.CatCommand;
import cli.commands.CdCommand;
import cli.commands.Command;
import cli.commands.LsCommand;
import cli.commands.MkdirCommand;
import cli.commands.MvCommand;
import cli.commands.PwdCommand;
import cli.commands.RmCommand;
import cli.commands.RmdirCommand;
import cli.commands.TouchCommand;
import cli.commands.ClearCommand;
import cli.commands.HelpCommand;

/*
CommandExecutor:
- Interprets commands entered by the user.
- Splits the input string to determine the command and its arguments.
- Uses a switch to create an instance of the appropriate command.
- Executes the command and displays messages based on success or failure.
*/

public class CommandExecutor {
    public static void executeCommand(String input) {
        // Split the input into tokens
        String[] tokens = input.split(" ");
        String commandName = tokens[0];
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        Command cmd;

        // Variables to store file name and append mode
        String outputFile = null;
        boolean appendMode = false;

        // Check for output redirection symbols '>' and '>>'
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(">") && i + 1 < args.length) {
                outputFile = args[i + 1]; // Get the file name to redirect output
                appendMode = false; // Set to overwrite mode
                args = Arrays.copyOfRange(args, 0, i); // Remove redirection part from args
                break;
            } else if (args[i].equals(">>") && i + 1 < args.length) {
                outputFile = args[i + 1]; // Get the file name to redirect output
                appendMode = true; // Set to append mode
                args = Arrays.copyOfRange(args, 0, i); // Remove redirection part from args
                break;
            }
        }

        // Determine the command to execute based on commandName
        switch (commandName.toLowerCase()) {
            case "pwd":
                cmd = new PwdCommand();
                break;
            case "ls":
                cmd = new LsCommand();
                break;
            case "cd":
                cmd = new CdCommand();
                break;
            case "cat":
                cmd = new CatCommand();
                break;
            case "mkdir":
                cmd = new MkdirCommand();
                break;
            case "rmdir":
                cmd = new RmdirCommand();
                break;
            case "touch":
                cmd = new TouchCommand();
                break;
            case "mv":
                cmd = new MvCommand();
                break;
            case "rm":
                cmd = new RmCommand();
                break;
            case "clear":
                cmd = new ClearCommand();
                break;
            case "help":
                cmd = new HelpCommand();
                break;
            default:
                System.out.println("Command not recognized. Please try again.");
                return;
        }

        // Execute the command and check the return value for success/failure
        boolean success = executeWithRedirection(cmd, args, outputFile, appendMode);
        if (!success) {
            System.out.println("Error executing command: " + commandName);
        }
    }

    private static boolean executeWithRedirection(Command cmd, String[] args, String outputFile, boolean appendMode) {
        PrintStream originalOut = System.out; // Save original output stream

        if (outputFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(outputFile, appendMode); // true for append, false for overwrite
                System.setOut(new PrintStream(fos));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
                return false;
            }
        }

        boolean success = cmd.execute(args); // Execute the command

        if (outputFile != null) {
            System.setOut(originalOut); // Restore original output stream
        }

        return success;
    }
}