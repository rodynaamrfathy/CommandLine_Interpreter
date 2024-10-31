package cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cli.commands.RmCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RmCommandTest {

    private RmCommand rmCommand;
    private Path testFilePath;
    private Path testDirPath;

    @BeforeEach
    public void setUp() throws IOException {
        rmCommand = new RmCommand();
        
        // Create a temporary test file and directory
        testFilePath = Files.createTempFile("testfile", ".txt");
        testDirPath = Files.createTempDirectory("testDir");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up: delete the test file and directory after each test
        if (Files.exists(testFilePath)) {
            Files.delete(testFilePath);
        }
        if (Files.exists(testDirPath)) {
            Files.delete(testDirPath);
        }
    }

    @Test
    public void testExecute_FileExists_DeletesFile() {
        // Arrange
        String[] args = {testFilePath.toString()};  // Use the absolute path
        
        // Act
        boolean result = rmCommand.execute(args);

        // Assert
        assertTrue(result);
        assertFalse(Files.exists(testFilePath));
    }

    @Test
    public void testExecute_FileDoesNotExist() {
        // Arrange
        String[] args = {"nonexistent.txt"};

        // Act
        boolean result = rmCommand.execute(args);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testExecute_DirectoryWithoutRecursiveFlag() {
        // Arrange
        String[] args = {testDirPath.toString()}; // Use the absolute path

        // Act
        boolean result = rmCommand.execute(args);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testExecute_DirectoryWithRecursiveFlag() {
        // Arrange
        String[] args = {"-r", testDirPath.toString()}; // Use the absolute path

        // Act
        boolean result = rmCommand.execute(args);

        // Assert
        assertTrue(result);
        assertFalse(Files.exists(testDirPath));
    }
}
