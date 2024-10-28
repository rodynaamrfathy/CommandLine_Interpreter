package cli;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cli.commands.CdCommand;

public class CdCommandTest {
    private String initialDir;

    @BeforeEach
    public void setUp() {
        initialDir = System.getProperty("user.dir");
    }

    @Test
    public void testCdToHomeDirectory() {
        CdCommand command = new CdCommand("~");
        String expectedHomeDir = System.getProperty("user.home");

        boolean result = command.execute();
        assertTrue(result, "Command should execute successfully.");
        assertEquals(expectedHomeDir, System.getProperty("user.dir"), "Current directory should be updated to home directory");
    }

    @Test
    public void testCdToParentDirectory() {
        CdCommand command = new CdCommand("..");
        String expectedParentDir = new File(initialDir).getParent();

        boolean result = command.execute();
        assertTrue(result, "Command should execute successfully.");
        assertEquals(expectedParentDir, System.getProperty("user.dir"), "Current directory should be updated to parent directory");
    }

    @Test
    public void testCdToCurrentDirectory() {
        CdCommand command = new CdCommand(".");
        boolean result = command.execute();
        assertTrue(result, "Command should execute successfully.");
        assertEquals(initialDir, System.getProperty("user.dir"), "Current directory should remain the same.");
    }

    @Test
    public void testCdToInvalidDirectory() {
        CdCommand command = new CdCommand("invalidDir");
        boolean result = command.execute();
        assertFalse(result, "Command should fail for an invalid directory.");
    }

    @Test
    public void testCdToPreviousDirectory() throws IOException {
        File tempDir = new File(initialDir, "testDir");
        tempDir.mkdir();

        CdCommand commandChange = new CdCommand("testDir");
        commandChange.execute();

        CdCommand commandPrevious = new CdCommand("-");
        boolean result = commandPrevious.execute();
        assertTrue(result, "Command should execute successfully.");
        assertEquals(initialDir, System.getProperty("user.dir"), "Current directory should return to the previous directory");

        tempDir.delete();
    }
}
