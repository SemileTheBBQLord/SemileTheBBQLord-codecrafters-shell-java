import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
  public static void main(String[] args) throws Exception {
    String[] commands = {"exit", "echo", "type"};
    String[] paths = System.getenv("PATH").split(":");
    try (Scanner scanner = new Scanner(System.in)) {
      boolean isRunning = true;
      while (isRunning) {
        System.out.print("$ ");
        String input = scanner.nextLine();
        String command = input.split(" ")[0];
        String rest = input.split(" ").length > 1 ? input.split(" ", 2)[1] : "";
        switch (command) {
        case "exit":
          isRunning = false;
          break;
        case "echo":
          System.out.println(rest);
          break;
        case "type":
          if (Arrays.asList(commands).contains(rest))
            System.out.println(rest + " is a shell builtin");
          else {
            boolean foundFile = false;
            for (String path : paths) {
              // Check if the file exists in the path and is executable
              if (new File(path + "/" + rest).exists() &&
                  new File(path + "/" + rest).canExecute()) {
                System.out.println(rest + " is " + path + "/" + rest);
                foundFile = true;
                break;
              }
            }
            if (!foundFile)
              System.out.println(rest + ": not found");
          }
          break;
        default:
          System.out.println(input + ": command not found");
          break;
        }
      }
      System.exit(0);
    }
  }
}