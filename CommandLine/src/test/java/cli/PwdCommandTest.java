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
            // Execute the PwdCommand
            Command pwdCommand = new PwdCommand();
            pwdCommand.execute();

            // Get the output and verify it contains the expected result
            String output = outputStream.toString().trim();
            String expectedDir = System.getProperty("user.dir");

            assertTrue(output.contains(expectedDir), "Output should contain the current directory path");

        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }
}
