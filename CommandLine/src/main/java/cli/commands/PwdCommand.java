package main.java.cli.commands;

public class PwdCommand  implements Command{

    @Override
    public void execute() {

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current directory: " + currentDir);
    }


}
