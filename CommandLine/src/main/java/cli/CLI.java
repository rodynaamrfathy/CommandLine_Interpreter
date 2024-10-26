package main.java.cli;

import java.util.Scanner;

public class CLI {
<<<<<<< HEAD
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to the Command Line Interpreter (CLI)!");

        // Infinite loop to continuously accept commands
        while (true) {
            // Prompt for user input
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();

            // Process the command
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the CLI. Goodbye!");
                break; // Exit the loop and terminate the program
            } else {
                // Here you would typically use the CommandExecuter to handle the command
                // For now, just echo the command back to the user
                System.out.println("You entered: " + command);
            }
        }

        // Close the scanner
=======
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Disconnecting");
                break;
            }
            CommandExecutor.executeCommand(input);
        }
>>>>>>> 077f207e549fba00a6953509eb53483ab1a2d8c8
        scanner.close();
    }
}
