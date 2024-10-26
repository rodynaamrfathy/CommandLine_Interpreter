package main.java.cli;

import java.util.Scanner;

public class CLI {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            CommandExecutor.executeCommand(input);
        }
        scanner.close();
    }
}
