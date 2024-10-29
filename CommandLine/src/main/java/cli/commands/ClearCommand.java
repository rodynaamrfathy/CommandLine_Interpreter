package cli.commands;

public class ClearCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        // Determine the operating system
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // Windows clear command
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // UNIX-based clear command (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing the console: " + e.getMessage());
            return false;
        }
        return true;
    }
}
