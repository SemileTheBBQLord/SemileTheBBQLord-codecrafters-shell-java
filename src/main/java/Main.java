import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    /*
    The canRunExternal class is used to verify if the inputs is a executable file and can run it by testing 
    the array parameter with the ProcessBuilder class and the try and catch function.
    */
    public static boolean canRunExternal(String[] in) { 
        try {
            ProcessBuilder chkprocess = new ProcessBuilder(in);
            Process verify = chkprocess.start();
            return verify.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
    //This cdHandler class is used to change the current directory into a new directory path.
    public static File cdHandler(File currentDir, String dir) {
    
     Path currentPath = Paths.get(currentDir.getPath());
     Path newPath = currentPath.resolve(dir).toAbsolutePath().normalize();
     File newDir = newPath.toFile();
     if (newDir.isDirectory()) {
       return newDir;
     }
     System.out.println(newDir + ": No such file or directory");
     return currentDir;
    }
   
    
    public String getFilePath() {
     String filePath = System.getenv("PATH");
     return filePath;
    }

    public File getCurrentDirectory() {
     File currentDirectory = new File("user.dir");
     return currentDirectory;
    }

    public String[] splitPathsinWindows(String path) {
     String[] paths = path.split(":");
     return paths;
    }

    public void promptUserInput(String prompt){
        Scanner scanner = new Scanner(System.in);
        inputtouse = scanner.nextLine();
        result(inputtouse);
        
    }

    public String result(String input4) {
        switch (input4) {
            case "echo":
                echoPrompt();
                break;
            case "exit":
                System.exit(0);
                break;
            case "type":
                typePrompt(input4);
                break;
            case "pwd":
                pwdPrompt();
                break;
            case "cd":
                cdPrompt();
                break;
            default:
                if(canRunExternal(inputs) == true) {
                 ProcessBuilder openFile = new ProcessBuilder(inputs);
                 Process process = openFile.start();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 String s = null;
                 while ((s = reader.readLine()) != null) {
                 System.out.println(s);
                 }
                } 
          else{
          System.out.println(inputs[0] + ": command not found");
          }
                return 1;
                break;
        }
    }

    public void echoPrompt(){
        if (args.length == 0){
            System.out.print("echo \n");
            return;
        }
        else {
            printEntireMessage();
        }
    }

    public void printEntireMessage(){
        for (int i = 0; i < args.length; i++){
            System.out.print(args[i] + " ");
        }
        System.out.print("\n");
    }

    public void typePrompt(String input5){
        switch (input5){
            case "echo":
                System.out.println("echo is a shell builtin");
                break;
            case "exit":
                System.out.println("exit is a shell builtin");
                break;
            case "type":
                System.out.println("type is a shell builtin");
                break;
            case "pwd":
                System.out.println("pwd is a shell builtin");
                break;
            case "cd":
                System.out.println("cd is a shell builtin");
                break;
            default:
           //Utilizes the boolean variable and the for loop to check if the second and above elements in the inputs array exists in the path directory.
           //This is mainly used because the inputs and the path the system checks are in a array.
            boolean foundFile = false;
            for (String path : currentPaths) {
             if (new File(path + "/" + inputs[1]).exists() && new File(path + "/" + inputs[1]).canExecute()) {
              System.out.println(inputs[1] + " is " + path + "/" + inputs[1]);
             foundFile = true;
             break;
              }
             }
             if (!foundFile) {
              System.out.println(inputs[1] + ": not found");
              break;
             }
            break;
        }
    }

    public void pwdPrompt(){
        System.out.println(currentDirectories.getAbsolutePath());
    }

    public void cdPrompt(){
        File f = currentDirectories;
        if (inputs.length < 2){
            System.out.println("cd: missing operand");
            return;
        }
        else if (inputs[1].equals("~")){
           f = new File(System.getenv("HOME"));
           break;
           }
        f = cdHandler(f, inputs[1]);
    }






    //This is where the main program starts.
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in); 
        
        String filePath = getFilePath(); 
        File currentDirectories = getCurrentDirectory(); 
        String[] currentPaths = splitPathsinWindows(filePath); 
        
        Random r = new Random();
        String input;
        String[] inputs;
        int status, numberOfSpaces;
        int j = r.nextInt(1000); //To restrict the random number generation to 1 to 1000.
        
        numberOfSpaces = 0;
        
        //Utilized a loop so that the system can initiate as many commands as possible in the console based on the entered inputs.
        
        for (int i = 0;i < j;i++){
        System.out.print("$ ");

        promptUserInput(args[0]); 
        input = scanner.nextLine();
        inputs = input.split(" "); 
    
    }
}
