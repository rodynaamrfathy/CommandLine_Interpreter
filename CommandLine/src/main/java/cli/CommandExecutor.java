package cli;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import cli.commands.CatCommand;
import cli.commands.CdCommand;
import cli.commands.ClearCommand;
import cli.commands.Command;
import cli.commands.HelpCommand;
import cli.commands.LsCommand;
import cli.commands.MkdirCommand;
import cli.commands.MvCommand;
import cli.commands.PwdCommand;
import cli.commands.RmCommand;
import cli.commands.RmdirCommand;
import cli.commands.TouchCommand;
import cli.commands.WcCommand;
/*
CommandExecutor:
- Interprets commands entered by the user.
- Splits the input string to determine the command and its arguments.
- Uses a switch to create an instance of the appropriate command.
- Executes the command and displays messages based on success or failure.
*/

public class CommandExecutor {
    public static void executeCommand(String input) {
        // Split the input into commands by the pipe character '|'
        String[] commands = input.split("\\|");

        // Prepare to store command objects and arguments
        Command[] cmdArray = new Command[commands.length];
        String[][] argsArray = new String[commands.length][];

        for (int i = 0; i < commands.length; i++) {
            String commandLine = commands[i].trim(); // Trim whitespace
            // Split the command line into tokens
            String[] tokens = commandLine.split(" ");
            String commandName = tokens[0];
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

            // Create command instance based on command name
            cmdArray[i] = createCommand(commandName);
            argsArray[i] = args; // Store arguments for each command
        }

        // Execute commands with piping
        executeWithPipes(cmdArray, argsArray);
    }

    private static Command createCommand(String commandName) {
        switch (commandName.toLowerCase()) {
            case "pwd": return new PwdCommand();
            case "ls": return new LsCommand();
            case "cd": return new CdCommand();
            case "cat": return new CatCommand();
            case "mkdir": return new MkdirCommand();
            case "rmdir": return new RmdirCommand();
            case "touch": return new TouchCommand();
            case "mv": return new MvCommand();
            case "rm": return new RmCommand();
            case "clear": return new ClearCommand();
            case "help": return new HelpCommand();
            case "wc" : return new WcCommand();
            default: throw new IllegalArgumentException("Command not recognized: " + commandName);
        }
    }

    private static void executeWithPipes(Command[] commands, String[][] argsArray) {
        PrintStream originalOut = System.out; // Save the original output stream
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        for (int i = 0; i < commands.length; i++) {
            // Check for output redirection symbols '>' and '>>' in the current command args
            String outputFile = null;
            boolean appendMode = false;

            for (int j = 0; j < argsArray[i].length; j++) {
                if (argsArray[i][j].equals(">") && j + 1 < argsArray[i].length) {
                    outputFile = argsArray[i][j + 1]; // Get the file name to redirect output
                    appendMode = false; // Set to overwrite mode
                    argsArray[i] = Arrays.copyOfRange(argsArray[i], 0, j); // Remove redirection part from args
                    break;
                } else if (argsArray[i][j].equals(">>") && j + 1 < argsArray[i].length) {
                    outputFile = argsArray[i][j + 1]; // Get the file name to redirect output
                    appendMode = true; // Set to append mode
                    argsArray[i] = Arrays.copyOfRange(argsArray[i], 0, j); // Remove redirection part from args
                    break;
                }
            }

            // Execute the command
            boolean success = executeWithRedirection(commands[i], argsArray[i], outputFile, appendMode);
            if (!success) {
                System.out.println("Error executing command: " + commands[i].getClass().getSimpleName());
            }
        }

        // Restore the original output stream
        System.setOut(originalOut);
    }

    private static boolean executeWithRedirection(Command cmd, String[] args, String outputFile, boolean appendMode) {
        PrintStream originalOut = System.out; // Save the original output stream
        FileOutputStream fos = null; // Declare FileOutputStream outside the try block
    
        if (outputFile != null) {
            try {
                fos = new FileOutputStream(outputFile, appendMode); // Create FileOutputStream
                System.setOut(new PrintStream(fos)); // Redirect output
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
                return false;
            }
        }
    
        boolean success = cmd.execute(args); // Execute the command
    
        // Restore the original output stream
        if (outputFile != null) {
            System.setOut(originalOut); // Restore original output stream
        }
    
        // Close the FileOutputStream if it was openeda
        if (fos != null) {
            try {
                fos.close(); // Close the output stream
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
    
        return success;
    }
}