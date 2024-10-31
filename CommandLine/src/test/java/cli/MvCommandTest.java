package cli;

import cli.commands.MvCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class MvCommandTest {
    
    private Path tempDir;
    private Path sourceFile;
    private Path destinationDir;
    
    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory
        tempDir = Files.createTempDirectory("testDir");
        
        // Create a temporary source file
        sourceFile = Files.createTempFile(tempDir, "source", ".txt");
        
        // Create a temporary destination directory
        destinationDir = Files.createTempDirectory(tempDir, "destDir");
    }
    
    @AfterEach
    void tearDown() throws IOException {
        // Delete the temporary source file if it exists
        if (Files.exists(sourceFile)) {
            Files.delete(sourceFile);
        }

        // Delete the destination directory and its contents if it exists
        if (Files.exists(destinationDir)) {
            Files.walk(destinationDir)
                .sorted(Comparator.reverseOrder()) // Ensure files are deleted before directories
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

        // Delete the temporary directory
        if (Files.exists(tempDir)) {
            Files.walk(tempDir)
                .sorted(Comparator.reverseOrder()) // Ensure files are deleted before directories
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    @Test
    void testSuccessfulMove() throws IOException {
        MvCommand mvCommand = new MvCommand();
        String[] args = {sourceFile.toString(), destinationDir.resolve("movedFile.txt").toString()};
        
        // Execute the move command
        boolean result = mvCommand.execute(args);
        
        // Check if the move was successful
        assertTrue(result);
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist");
        assertTrue(Files.exists(destinationDir.resolve("movedFile.txt")), "Destination file should exist");
    }

    @Test
    void testSourceDoesNotExist() {
        MvCommand mvCommand = new MvCommand();
        String[] args = {tempDir.resolve("nonexistent.txt").toString(), destinationDir.resolve("destination.txt").toString()};
        
        // Execute the move command
        boolean result = mvCommand.execute(args);
        
        // Check if the move failed as expected
        assertFalse(result, "Move command should fail when source does not exist");
    }
    
    @Test
    void testDestinationIsDirectory() throws IOException {
        MvCommand mvCommand = new MvCommand();
        String[] args = {sourceFile.toString(), destinationDir.toString()}; // Move to directory
        
        // Execute the move command
        boolean result = mvCommand.execute(args);
        
        // Check if the move was successful
        assertTrue(result);
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist");
        assertTrue(Files.exists(destinationDir.resolve(sourceFile.getFileName())), "Moved file should exist in the destination directory");
    }
}
