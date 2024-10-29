package cli;

import java.io.File;
import java.io.FileWriter;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cli.commands.CatCommand;

public class CatCommandTest {
    private String initialDir;
    private CatCommand catCommand;
    private File testFile;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() throws Exception {
        initialDir = System.getProperty("user.dir");
        catCommand = new CatCommand();

        // Create a temporary test file
        testFile = new File(initialDir, "testfile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, this is a test file.\n");
            writer.write("It contains multiple lines.\n");
            writer.write("This is the third line.\n");
        }

        // Redirect System.out to suppress output
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        // Clean up: delete the test file after each test
        if (testFile.exists()) {
            testFile.delete();
        }

        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testCatCommandWithValidFile() {
        // Execute the command on the valid test file
        boolean result = catCommand.execute(new String[]{"testfile.txt"});

        // Check if the command executed successfully
        assertTrue(result, "Command should execute successfully.");
        // Output will be printed to console, but we can check if it did not fail.
    }

    @Test
    public void testCatCommandWithInvalidFile() {
        // Execute the command on a non-existent file
        boolean result = catCommand.execute(new String[]{"invalidfile.txt"});

        // Check if the command failed
        assertFalse(result, "Command should fail for an invalid file.");
    }

    @Test
    public void testCatCommandWithoutArguments() {
        // Execute the command without any arguments
        boolean result = catCommand.execute(new String[]{});

        // Check if the command failed and shows usage message
        assertFalse(result, "Command should fail without arguments.");
    }
}
