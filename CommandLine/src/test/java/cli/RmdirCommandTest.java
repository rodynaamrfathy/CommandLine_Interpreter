package cli;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cli.commands.RmdirCommand;

public class RmdirCommandTest {
    private String initialDir;
    private RmdirCommand rmdirCommand;
    private File testDir;

    @BeforeEach
    public void setUp() {
        initialDir = System.getProperty("user.dir");
        rmdirCommand = new RmdirCommand();
        testDir = new File(initialDir, "testDir");

        // Ensure the directory is deleted and then recreate it
        if (testDir.exists()) {
            testDir.delete();
        }
        testDir.mkdir();
    }

    @AfterEach
    public void tearDown() {
        // Clean up by deleting the directory if it still exists
        if (testDir.exists()) {
            testDir.delete();
        }
    }

    @Test
    public void testRmdirCommandWithExistingEmptyDirectory() {
        // Execute the command to remove an existing empty directory using absolute path
        boolean result = rmdirCommand.execute(new String[]{testDir.getAbsolutePath()});

        // Check if the command executed successfully and the directory was removed
        assertTrue(result, "Command should execute successfully for an existing empty directory.");
        assertFalse(testDir.exists(), "The test directory should have been removed.");
    }
}
