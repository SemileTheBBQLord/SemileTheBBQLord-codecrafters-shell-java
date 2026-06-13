import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    
    String input;
    String[] inputs;
    String filePath;
    String[] currentPaths;
    File currentDirectories;
    public void main(String[] args) throws Exception {
        
        getFilePath(); 
        getCurrentDirectory(); 
        splitPathsinWindows(filePath); 
        
        boolean isRunning = true;

        
        startShell(isRunning);
        
 }
    public void startShell( boolean isRunning) throws Exception {
        while (isRunning = true){
        System.out.print("$ ");
        promptUserInput(); 
     }
    }

    public void getFilePath() {
     filePath = System.getenv("PATH");
    }

    public void getCurrentDirectory() {
     currentDirectories = new File(" ");
    }

    public void splitPathsinWindows(String path) {
     String[] paths = path.split(":");
     currentPaths = paths;
    }

    public void promptUserInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        input = scanner.nextLine();
        inputs = input.split(" "); 

        verificationOfInput(inputs);

    }

    public void verificationOfInput(String[] inputarray){
        result(inputarray[0]);

        for (int i = 0; i < inputarray.length; i++){
            if (inputarray[i].equals("|")){
                i++;
                result(inputarray[i]);
                break;
            }
        }


    }

    public void result(String inputToCheck){
        switch (inputToCheck) {
            case "echo":
                echoPrompt();
                break;
            case "exit":
                System.exit(0);
                break;
            case "type":
                typePrompt(inputs[1]);
                break;
            case "pwd":
                pwdPrompt();
                break;
            case "cd":
                cdPrompt();
                break;
            case "cat":
                catPrompt();
                break;
            case "wc":
                catPrompt();
                break;
            case "head":
                catPrompt();
                break;
            case "tail":
                catPrompt();
                break;
            default:
                try {
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
                }} catch (Exception e) {
                System.out.println(inputs[0] + ": command not found");
                }
                break;
        }
    }

    public void echoPrompt(){
        if (inputs.length == 0){
            System.out.print("echo \n");
        }
        else {
            printEntireMessage();
        }
        System.exit(1);
    }

    public void printEntireMessage(){
        for (int i = 0; i < inputs.length; i++){
            System.out.print(inputs[i] + " ");
        }
        System.out.print("\n");
        System.exit(1);
    }

    public void typePrompt(String checkInputType){
        switch (checkInputType){
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
                pathInspection(checkInputType);
            break;
        }
    }
    public void pathInspection(String checkInputType){
        boolean foundFile = false;
        for (String path : currentPaths) {
         if (new File(path + "/" + checkInputType).exists() && new File(path + "/" + checkInputType).canExecute()) {
          System.out.println(checkInputType + " is " + path + "/" + checkInputType);
         foundFile = true;
         break;
           }
         }
         if (!foundFile) {
          System.out.println(checkInputType + ": not found");
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
           }
        f = cdHandler(f, inputs[1]);
    }
    
    public static boolean canRunExternal(String[] in) { 
        try {
            ProcessBuilder chkprocess = new ProcessBuilder(in);
            Process verify = chkprocess.start();
            return verify.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }

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
    
    public void catPrompt() {

    }
    
}