package cli;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI {

    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m"; 

    public static void main(String[] args) {
        // Retrieve the username and hostname
        String username = System.getProperty("user.name");
        String hostname = "localhost";
        
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Unable to retrieve hostname. Using 'localhost' instead.");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Command Line Interpreter (CLI)!");

            while (true) {
                try {
                    // Display the prompt in green
                    System.out.print(GREEN + username + "@" + hostname + " $ " + RESET);
                    String command = scanner.nextLine();

                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting the CLI. Goodbye!");
                        break;
                    } else {
                        executeCommand(command); // Call the command execution method
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("No input available. Exiting.");
                    break;
                }
            }
        }
    }

    public static void executeCommand(String command) {
        Thread commandThread = new Thread(() -> {
            CommandExecutor executor = new CommandExecutor();
            executor.executeCommand(command);
        });

        commandThread.start();
        try {
            commandThread.join();
        } catch (InterruptedException e) {
            System.out.println("Command execution interrupted.");
        }
    }
}
