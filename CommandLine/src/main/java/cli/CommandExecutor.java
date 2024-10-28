package cli;

import cli.commands.CdCommand;
import cli.commands.Command;
import cli.commands.PwdCommand;

public class CommandExecutor {
    static void executeCommand(String command) {
        Command cmd;

        // Split the command into parts (command name and arguments)
        String[] parts = command.trim().split(" ", 2);
        String commandName = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1] : "";

        switch (commandName) {
            case "pwd":
                cmd = new PwdCommand();
                break;
            case "cd":
                cmd = new CdCommand(argument); 
                break;
            default:
                System.out.println("Command not recognized. Please try again.");
                return;  
        }
        cmd.execute();
    }
}
