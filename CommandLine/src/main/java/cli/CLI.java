package main.java.cli;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        try (Scanner scanner = new Scanner(System.in)) {
            // Welcome message
            System.out.println("Welcome to the Command Line Interpreter (CLI)!");

            // Infinite loop to continuously accept commands
            while (true) {
                try {
                    // Prompt for user input
                    System.out.print("Enter a command: ");
                    String command = scanner.nextLine();

                    // Process the command
                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting the CLI. Goodbye!");
                        break; // Exit the loop and terminate the program
                    } else {
                        System.out.println("You entered: " + command);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("No input available. Exiting.");
                    break;
                }
            }
        }
    }
}
