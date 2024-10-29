package cli;

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
        
        // Determine the command to execute based on commandName
        switch (commandName.toLowerCase()) {
            case "pwd":
                cmd = new PwdCommand();
                break;
            case "ls":
                cmd = new LsCommand();
                break;
            case  "cd" :
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
            default:
                System.out.println("Command not recognized. Please try again.");
                return;
        }
        
        // Execute the command and check the return value for success/failure
        boolean success = cmd.execute(args);
        if (!success) {
            System.out.println("Error executing command: " + commandName);
        }
    }
}
