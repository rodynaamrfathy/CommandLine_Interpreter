package cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import cli.commands.Command;
import cli.commands.PwdCommand;

public class PwdCommandTest {

    @Test
    public void testExecute() {
        // Set up a stream to capture System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Check the user.dir before executing the command
            String userDir = System.getProperty("user.dir");
            System.out.println("Current User Directory: " + userDir);
            
            Command pwdCommand = new PwdCommand();
            boolean result = pwdCommand.execute(new String[]{}); 

            // Continue with your existing assertions
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setOut(originalOut);
        }
    }

}
