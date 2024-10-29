package cli;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cli.commands.CdCommand;

public class CdCommandTest {
    private String initialDir;
    private CdCommand cdCommand;

    @BeforeEach
    public void setUp() {
        initialDir = System.getProperty("user.dir");
        cdCommand = new CdCommand(); // Initialize the CdCommand instance
    }

    @AfterEach
    public void tearDown() {
        // Restore the original working directory after each test
        System.setProperty("user.dir", initialDir);
    }

    @Test
    public void testCdToValidDirectory() {
        // Create a temporary directory for testing
        File tempDir = new File(initialDir, "testDir");
        tempDir.mkdir();

        // Execute cd to the new directory
        boolean result = cdCommand.execute(new String[]{"testDir"});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(tempDir.getAbsolutePath(), System.getProperty("user.dir"), "Current directory should be updated to the test directory.");

        // Clean up
        tempDir.delete();
    }

    @Test
    public void testCdToCurrentDirectory() {
        // Execute command with "." (should remain in the current directory)
        boolean result = cdCommand.execute(new String[]{"."});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(initialDir, System.getProperty("user.dir"), "Current directory should remain the same.");
    }

    @Test
    public void testCdToInvalidDirectory() {
        // Attempt to change to a non-existent directory
        boolean result = cdCommand.execute(new String[]{"invalidDir"});
        assertFalse(result, "Command should fail for an invalid directory.");
        assertEquals(initialDir, System.getProperty("user.dir"), "Current directory should remain the same.");
    }
}
