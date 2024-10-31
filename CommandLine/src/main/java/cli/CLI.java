package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI {

    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
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
                    System.out.print(GREEN + username + "@" + hostname + " $ " + RESET);
                    String command = scanner.nextLine();

                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting the CLI. Goodbye!");
                        break;
                    } else {
                        executeCommand(command);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("No input available. Exiting.");
                    break;
                }
            }
        }
    }

    public static void executeCommand(String command) {
        if (command.contains("|")) {
            String[] commands = command.split("\\|");
            String firstCommand = commands[0].trim();
            String secondCommand = commands[1].trim();

            try (PipedOutputStream pos = new PipedOutputStream();
                 PipedInputStream pis = new PipedInputStream(pos)) {

                // Capture original System.out and System.in
                PrintStream originalOut = System.out;
                InputStream originalIn = System.in;

                // Thread for the first command
                Thread firstThread = new Thread(() -> {
                    CommandExecutor executor = new CommandExecutor();
                    System.setOut(new PrintStream(pos)); // Redirect System.out to pos
                    executor.executeCommand(firstCommand);
                    System.out.flush();
                    System.setOut(originalOut); // Restore original System.out
                });

                // Thread for the second command
                Thread secondThread = new Thread(() -> {
                    CommandExecutor executor = new CommandExecutor();
                    System.setIn(pis); // Redirect System.in to pis
                    executor.executeCommand(secondCommand);
                    System.setIn(originalIn); // Restore original System.in
                });

                firstThread.start();
                // Wait for the first command to finish or timeout after 2 seconds
                firstThread.join(2000);
                if (firstThread.isAlive()) {
                    System.out.println("First command exceeded time limit and was interrupted.");
                    firstThread.interrupt(); // Interrupt if still running
                }

                pos.close(); // Close output to signal end of data for the second command

                secondThread.start();
                // Wait for the second command to finish or timeout after 2 seconds
                secondThread.join(2000);
                if (secondThread.isAlive()) {
                    System.out.println("Second command exceeded time limit and was interrupted.");
                    secondThread.interrupt(); // Interrupt if still running
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Error executing piped commands: " + e.getMessage());
            }
        } else {
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
}