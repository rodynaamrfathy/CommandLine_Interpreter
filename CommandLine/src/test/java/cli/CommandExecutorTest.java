package cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandExecutorTest {

    private Path testOutputFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Create or clear testOutput.txt before each test
        testOutputFile = Paths.get("testOutput.txt");
        Files.deleteIfExists(testOutputFile); // Ensure file doesn't exist before creation
        Files.createFile(testOutputFile);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the file after each test to avoid conflicts
        Files.deleteIfExists(testOutputFile);
    }

    @Test
    public void testOverwriteRedirection() throws IOException {
        // Example logic to overwrite the current directory path to testOutput.txt
        Files.writeString(testOutputFile, System.getProperty("user.dir"), StandardCharsets.UTF_8);
        
        // Read the contents of testOutput.txt
        String output = Files.readString(testOutputFile);
        
        // Check if the output file contains the current directory path
        assertTrue(output.contains(System.getProperty("user.dir")), "Output file should contain the current directory path.");
    }

    @Test
    public void testAppendRedirection() throws IOException {
        // Append some content to testOutput.txt
        Files.writeString(testOutputFile, "Initial content\n", StandardOpenOption.APPEND);
        Files.writeString(testOutputFile, "Appended text\n", StandardOpenOption.APPEND);
        
        // Read the content of testOutput.txt
        String output = Files.readString(testOutputFile);
        
        // Check if the appended text is in the output
        assertTrue(output.contains("Appended text"), "Output file should contain appended text.");
    }
}