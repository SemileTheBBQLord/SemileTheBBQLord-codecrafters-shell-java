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
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        
        String filePath = System.getenv("PATH");
        File f = new File("");
        String[] paths = filePath.split(":");
        
        Random r = new Random();
        String input;
        String[] inputs;
        int status;
        int j = r.nextInt(1000);
        
        for (int i = 0;i < j;i++){
        System.out.print("$ ");
        input = scanner.nextLine();
        inputs = input.split(" ");
        
        switch (inputs[0]){
         case "echo":
          for (int z = 1; z < inputs.length; z++){
          if (inputs[z].contains("'")) {
           inputs[z] = inputs[z].replace("'", "");
           System.out.print(inputs[z] + " ");
           }
          else {
           System.out.print(inputs[z] + " ");
           }
          }
          System.out.print("\n");
          break;
         case "exit":
          status = Integer.parseInt(inputs[1]);
          System.exit(status);     
          break;
         case "type":
          switch (inputs[1]){
           case "echo":
            System.out.println(inputs[1] + " is a shell builtin");
            break;
           case "exit":
            System.out.println(inputs[1] + " is a shell builtin");
            break;
           case "type":
            System.out.println(inputs[1] + " is a shell builtin");
            break;
           case "pwd":
            System.out.println(inputs[1] + " is a shell builtin");
            break;
           case "cd":
            System.out.println(inputs[1] + " is a shell builtin");
            break;
           default:
            boolean foundFile = false;
            for (String path : paths) {
             if (new File(path + "/" + inputs[1]).exists() && new File(path + "/" + inputs[1]).canExecute()) {
              System.out.println(inputs[1] + " is " + path + "/" + inputs[1]);
             foundFile = true;
             break;
              }
             }
             if (!foundFile)
              System.out.println(inputs[1] + ": not found");
              break;
           }
           break;
          case "pwd":
           System.out.println(f.getAbsolutePath());
           break;
          case "cd":
           if (inputs[1].equals("~")){
           f = new File(System.getenv("HOME"));
           break;
           }
           else {
           f = cdHandler(f, inputs[1]);
           }
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
          
          break;
         }        
        }
    
    }
}
