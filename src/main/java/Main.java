import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage

        Scanner scanner = new Scanner(System.in);
        /*
        String path_commands = System.getenv("PATH");
        String[] path_command = path_commands.split(";");
        */
        String[] paths = System.getenv("PATH").split(":");
        Random r = new Random();
        String input, input2;
        int status;
        int j = r.nextInt(1000);
        
        for (int i = 0;i < j;i++){
        System.out.print("$ ");
        input = scanner.next();
        
        if (input.equalsIgnoreCase("exit")) {
          status = scanner.nextInt();
          System.exit(status);
        }
        else if (input.equalsIgnoreCase("echo")) {
          input2 = scanner.nextLine();
          System.out.println(input2.trim());
        }
        else if(input.equalsIgnoreCase("type")) {
          input2 = scanner.nextLine();
          input2 = input2.trim();
          if (input2.equalsIgnoreCase("exit") || input2.equalsIgnoreCase("echo") || input2.equalsIgnoreCase("type")){
          System.out.println(input2 + " is a shell builtin");
          }
           else {
            boolean foundFile = false;
            for (String path : paths) {
              // Check if the file exists in the path and is executable
              if (new File(path + "/" + input2).exists() &&
                  new File(path + "/" + input2).canExecute()) {
                System.out.println(input2 + " is " + path + "/" + input2);
                foundFile = true;
                break;
              }
            }
            if (!foundFile)
              System.out.println(input2 + ": not found");
          }          
          /*for (int n = 0; n < path_command.length; n++) {
            File file = new File(path_command[n], input2);
            if (file.exists()) {
            System.out.println(input2 + " is " + file.getPath());
            }
           }
           */
        }
         else if (input.equalsIgnoreCase("export")) {
          input2 = scanner.nextLine().trim();
          if (input2.startsWith("PATH=")) {
           String newPath = input2.substring(5); // Remove "PATH="
           paths = newPath.split(";");
           System.out.println("PATH updated.");
         }
         else {
          System.out.println(input + ": command not found");
         }
        
        }
    
    }
}
