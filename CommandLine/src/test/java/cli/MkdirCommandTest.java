package cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cli.commands.MkdirCommand;

public class MkdirCommandTest {
    private String initialDir;
    private MkdirCommand mkdirCommand;
    private File testDir;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        initialDir = System.getProperty("user.dir");
        mkdirCommand = new MkdirCommand();
        testDir = new File(initialDir, "testDir");

        // Redirect System.out to suppress output
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        // Clean up: delete the test directory if it exists
        if (testDir.exists()) {
            testDir.delete();
        }
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testMkdirCommandWithNewDirectory() {
        // Execute the command to create a new directory
        boolean result = mkdirCommand.execute(new String[]{"testDir"});

        // Check if the command executed successfully and the directory was created
        assertTrue(result, "Command should execute successfully.");
        assertTrue(testDir.exists(), "The test directory should have been created.");
    }

    @Test
    public void testMkdirCommandWithExistingDirectory() {
        // First create the directory
        mkdirCommand.execute(new String[]{"testDir"});

        // Execute the command again to create the same directory
        boolean result = mkdirCommand.execute(new String[]{"testDir"});

        // Check if the command executed successfully and the directory already exists
        assertTrue(result, "Command should execute successfully even if the directory exists.");
        assertTrue(testDir.exists(), "The test directory should still exist.");
    }

    @Test
    public void testMkdirCommandWithoutArguments() {
        // Execute the command without any arguments
        boolean result = mkdirCommand.execute(new String[]{});

        // Check if the command failed and shows the appropriate error message
        assertFalse(result, "Command should fail without arguments.");
        // Note: You might want to capture the console output to verify the error message.
    }
}
