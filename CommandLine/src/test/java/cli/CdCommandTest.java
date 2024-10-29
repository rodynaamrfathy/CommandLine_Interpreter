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
    public void testCdToHomeDirectory() {
        // Execute command with no arguments (should go to home directory)
        boolean result = cdCommand.execute(new String[]{});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(System.getProperty("user.home"), System.getProperty("user.dir"), "Current directory should be updated to home directory");
    }

    @Test
    public void testCdToParentDirectory() {
        // Create a temporary directory structure for testing
        File tempDir = new File(initialDir, "testDir");
        tempDir.mkdir();
        File childDir = new File(tempDir, "childDir");
        childDir.mkdir();

        // Change to child directory
        System.setProperty("user.dir", childDir.getAbsolutePath());

        // Execute cd ..
        boolean result = cdCommand.execute(new String[]{".."});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(tempDir.getAbsolutePath(), System.getProperty("user.dir"), "Current directory should be updated to parent directory");

        // Clean up
        childDir.delete();
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

    @Test
    public void testCdToPreviousDirectory() {
        // Create a temporary directory for testing
        File tempDir = new File(initialDir, "testDir");
        tempDir.mkdir();

        // Change to testDir
        cdCommand.execute(new String[]{"testDir"});

        // Change back to previous directory using "-"
        boolean result = cdCommand.execute(new String[]{"-"});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(initialDir, System.getProperty("user.dir"), "Current directory should return to the previous directory");

        // Clean up
        tempDir.delete();
    }

    @Test
    public void testCdToHomeFromCurrentDirectory() {
        // Change to home directory from current
        boolean result = cdCommand.execute(new String[]{"~"});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(System.getProperty("user.home"), System.getProperty("user.dir"), "Current directory should be updated to home directory");
    }
}