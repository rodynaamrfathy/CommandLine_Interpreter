package cli.commands; // Ensure this matches your package structure

public class HelpCommand implements Command {

    @Override
    public boolean  execute(String[] args) {
        System.out.println("Available Commands and Usage:");
        System.out.println();

        System.out.println("pwd                          - Prints the current directory path.");
        System.out.println("cd <dir>                     - Changes the current directory to <dir>.");
        System.out.println("ls                           - Lists files and directories in the current directory.");
        System.out.println("ls -a                        - Lists all files, including hidden files.");
        System.out.println("ls -r                        - Lists files and directories in reverse order.");
        System.out.println("mkdir <dir>                  - Creates a new directory with the name <dir>.");
        System.out.println("mkdir -p <dir/subdir>        - Creates directories recursively.");
        System.out.println("rmdir <dir>                  - Removes the specified directory if it is empty.");
        System.out.println("touch <file>                 - Creates a new file or updates the timestamp of an existing file.");
        System.out.println("mv <source> <destination>    - Moves or renames a file or directory.");
        System.out.println("rm <file>                    - Deletes the specified file.");
        System.out.println("cat <file>                   - Displays the content of the specified file.");
        System.out.println("> <file>                     - Redirects output to a file, overwriting its contents.");
        System.out.println(">> <file>                    - Redirects output to a file, appending to its contents.");
        System.out.println("|                            - Pipes the output of one command as input to another command.");
        System.out.println();
        return true;
    }
}
