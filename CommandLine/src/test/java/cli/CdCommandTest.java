package cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        // Store the initial working directory and create a new instance of CdCommand
        initialDir = System.getProperty("user.dir");
        cdCommand = new CdCommand();
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
        
        // Clean up any existing directory
        if (tempDir.exists()) {
            try {
                Files.delete(tempDir.toPath());
            } catch (IOException e) {
                System.err.println("Failed to delete existing temp directory: " + e.getMessage());
            }
        }
        
        // Attempt to create the new directory
        assertTrue(tempDir.mkdir(), "Temporary directory should be created successfully.");
    
        try {
            // Execute cd to the new directory
            boolean result = cdCommand.execute(new String[]{"testDir"});
            assertTrue(result, "Command should execute successfully.");
            assertEquals(tempDir.getAbsolutePath(), System.getProperty("user.dir"), "Current directory should be updated to the test directory.");
        } finally {
            // Clean up: Ensure the temporary directory is deleted
            if (tempDir.exists()) {
                try {
                    Files.delete(tempDir.toPath());
                } catch (IOException e) {
                    System.err.println("Failed to delete temp directory: " + e.getMessage());
                }
            }
        }
    }
    
    
    
       @Test
    public void testCdToInvalidDirectory() {
        // Attempt to change to a non-existent directory
        boolean result = cdCommand.execute(new String[]{"invalidDir"});
        assertFalse(result, "Command should fail for an invalid directory.");
        assertEquals(initialDir, System.getProperty("user.dir"), "Current directory should remain the same.");
    }

    @Test
    public void testCdToHomeDirectory() {
        // Execute command with "~" (should change to the user's home directory)
        boolean result = cdCommand.execute(new String[]{"~"});
        assertTrue(result, "Command should execute successfully.");
        assertEquals(System.getProperty("user.home"), System.getProperty("user.dir"), "Current directory should be updated to the home directory.");
    }
}
