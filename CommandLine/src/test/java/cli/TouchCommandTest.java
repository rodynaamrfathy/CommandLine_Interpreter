package cli;

import cli.commands.TouchCommand;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TouchCommandTest {

    private TouchCommand touchCommand;
    private final String testDir = "testDir"; // Directory for testing
    private final String existingFileName = "existingfile.txt";
    private final String newFileName = "createdFile.txt"; // New name for created file

    @BeforeEach
    void setUp() throws IOException {
        touchCommand = new TouchCommand(); // Initialize TouchCommand instance
        Files.createDirectories(Path.of(testDir)); // Create the test directory
        File existingFile = new File(testDir, existingFileName);
        if (existingFile.createNewFile()) {
            // File created successfully for testing
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up by deleting test directory and its contents
        Files.walk(Path.of(testDir))
             .map(Path::toFile)
             .forEach(File::delete);

        // Also delete the newly created file in the current directory
        File newFile = new File(System.getProperty("user.dir"), newFileName);
        if (newFile.exists()) {
            newFile.delete();
        }
    }



    @Test
    void testCreateFileInSpecifiedDirectory() {
        String[] args = {newFileName, testDir}; // Use filename and test directory
        boolean result = touchCommand.execute(args);
        assertTrue(result, "File should be created successfully in the specified directory.");
        assertTrue(Files.exists(Path.of(testDir, newFileName)), "File should exist in the specified directory.");
    }

    @Test
    void testTouchExistingFile() {
        String[] args = {existingFileName, testDir}; // Existing file in test directory
        boolean result = touchCommand.execute(args);
        assertTrue(result, "Touching existing file should return true.");
        assertTrue(Files.exists(Path.of(testDir, existingFileName)), "Existing file should still exist.");
    }

    @Test
    void testTouchInvalidDirectory() {
        String[] args = {newFileName, "/invalid/path"}; // Invalid directory
        boolean result = touchCommand.execute(args);
        assertFalse(result, "Expected failure due to invalid directory.");
        assertFalse(Files.exists(Path.of("/invalid/path", newFileName)), "File should not be created in an invalid directory.");
    }

    @Test
    void testTouchWithInvalidArguments() {
        String[] args = {}; // No arguments
        boolean result = touchCommand.execute(args);
        assertFalse(result, "Expected failure due to no arguments.");
    }

    @Test
    void testTouchFileWithSpecialCharacters() {
        String[] args = {"test@#$.txt", testDir}; // File name with special characters
        boolean result = touchCommand.execute(args);
        assertTrue(result, "File with special characters should be created successfully.");
        assertTrue(Files.exists(Path.of(testDir, "test@#$.txt")), "File should exist with special characters.");
    }
}
