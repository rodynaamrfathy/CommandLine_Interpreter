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
            Command pwdCommand = new PwdCommand();
            boolean result = pwdCommand.execute(new String[]{}); // Pass an empty array

            // Get the output and verify it contains the expected result
            String output = outputStream.toString().trim();
            String expectedDir = System.getProperty("user.dir");

            // Check that the execute() method returned true and that output contains the expected directory
            assertTrue(result, "The execute method should return true on success");
            assertTrue(output.contains(expectedDir), "Output should contain the current directory path");

        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }
}
