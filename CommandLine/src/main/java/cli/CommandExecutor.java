package main.java.cli;

import main.java.cli.commands.Command;
import main.java.cli.commands.PwdCommand;


/*

2. CommandExecuter File
Functionality:

-The CommandExecuter is responsible for interpreting the commands entered by the user.
-It takes the input string from the CLI, splits it into the command name and arguments, and determines which command to execute.
-Using a command factory or a similar mechanism, it retrieves the appropriate command instance based on the command name.
-The file ensures that commands are executed correctly, catching any exceptions that may occur during execution and displaying error messages as needed.
-This file is central to the command processing logic, connecting user input with the corresponding functionality defined in individual command classes.

*/

public class CommandExecutor {
    static void executeCommand(String command) {
        Command cmd;
        switch (command.toLowerCase()) {
            case "pwd" :
                cmd = new PwdCommand();
                break;
            default:
                System.out.println("Command not recognized. Please try again.");

                return;  
        }
        cmd.execute();
    }
}
