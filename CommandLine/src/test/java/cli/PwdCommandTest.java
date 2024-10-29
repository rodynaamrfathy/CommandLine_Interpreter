package cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import cli.commands.Command;
import cli.commands.PwdCommand;

public class PwdCommandTest {

    @Test
public void testExecute() {
    // Set up a stream to capture System.out
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
        // Execute the PwdCommand with no arguments and capture the boolean result
        Command pwdCommand = new PwdCommand(); // Ensure the command is initialized
        boolean result = pwdCommand.execute(new String[]{}); // Pass an empty array

        // Get the output and verify it contains the expected result
        String output = outputStream.toString().trim();
        String expectedDir = System.getProperty("user.dir");

        // Debug output to see what's happening
        System.out.println("Captured Output: " + output);
        System.out.println("Expected Directory: " + expectedDir);
        
        // Check that the execute() method returned true and that output matches the expected directory
        assertTrue(result, "The execute method should return true on success");
        assertTrue(output.equals(expectedDir), "Output should match the current directory path");

    } catch (Exception e) {
        // Print the stack trace for any unexpected exceptions
        e.printStackTrace();
    } finally {
        // Restore the original System.out
        System.setOut(originalOut);
    }
}

}
