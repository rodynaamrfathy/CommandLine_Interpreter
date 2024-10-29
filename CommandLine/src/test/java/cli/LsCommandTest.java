package cli;

import cli.commands.LsCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LsCommandTest {
    private Path tempDir; // Use Path for easier manipulation
    private LsCommand lsCommand;
    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() throws IOException {
        lsCommand = new LsCommand(); // Initialize the LsCommand instance

        // Redirect output to capture printed statements
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Create a temporary directory for testing
        tempDir = Files.createTempDirectory("testDir");

        // Create test files
        Files.createFile(tempDir.resolve("file1.txt")); // Regular file
        Files.createFile(tempDir.resolve(".hiddenFile")); // Hidden file
        Files.createDirectory(tempDir.resolve("subDir")); // Subdirectory

        // Change the user.dir property to the temporary directory
        System.setProperty("user.dir", tempDir.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up test directory
        Files.walk(tempDir)
            .sorted((a, b) -> b.compareTo(a)) // Delete in reverse order
            .forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        System.clearProperty("user.dir"); // Clear the user.dir property
        System.setOut(originalOut); // Restore original output
    }

    @Test
    public void testLsNormal() {
        // Execute the ls command
        boolean result = lsCommand.execute(new String[]{});
        assertTrue(result, "Command should execute successfully.");

        // Capture output and check for expected files
        String output = outputStream.toString();
        assertTrue(output.contains("file1.txt"), "Output should contain 'file1.txt'");
        assertFalse(output.contains(".hiddenFile"), "Output should not contain hidden files");
    }

    @Test
    public void testLsWithHiddenFiles() {
        // Execute the ls command with -a
        boolean result = lsCommand.execute(new String[]{"-a"});
        assertTrue(result, "Command should execute successfully.");

        // Capture output and check for expected files
        String output = outputStream.toString();
        assertTrue(output.contains("file1.txt"), "Output should contain 'file1.txt'");
        assertTrue(output.contains(".hiddenFile"), "Output should contain hidden files");
    }

    @Test
    public void testLsWithRecursiveOption() {
        // Execute the ls command with -r
        boolean result = lsCommand.execute(new String[]{"-r"});
        assertTrue(result, "Command should execute successfully.");

        // Capture output and check for expected files and directory
        String output = outputStream.toString();
        assertTrue(output.contains("file1.txt"), "Output should contain 'file1.txt'");
        assertTrue(output.contains("subDir"), "Output should contain 'subDir'");
        assertFalse(output.contains(".hiddenFile"), "Output should not contain hidden files");
    }
}
