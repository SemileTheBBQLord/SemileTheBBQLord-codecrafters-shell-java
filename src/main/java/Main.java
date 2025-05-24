import java.io.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage

        Scanner scanner = new Scanner(System.in);
        
        String filePath = System.getenv("PATH");
        String[] paths = filePath.split(":");
        
        Random r = new Random();
        String input, input2;
        int status;
        int j = r.nextInt(1000);
        
        for (int i = 0;i < j;i++){
        System.out.print("$ ");
        input = scanner.next();
        
        switch (input){
         case "echo":
          input2 = scanner.nextLine();
          System.out.println(input2.trim());
          break;
         case "exit":
          status = scanner.nextInt();
          System.exit(status);     
          break;
         case "type":
          input2 = scanner.nextLine();
          input2 = input2.trim();
          switch (input2){
           case "echo":
            System.out.println(input2 + " is a shell builtin");
            break;
           case "exit":
            System.out.println(input2 + " is a shell builtin");
            break;
           case "type":
            System.out.println(input2 + " is a shell builtin");
            break;
           default:
            boolean foundFile = false;
            for (String path : paths) {
             if (new File(path + "/" + input2).exists() && new File(path + "/" + input2).canExecute()) {
              System.out.println(input2 + " is " + path + "/" + input2);
             foundFile = true;
             break;
              }
             }
             if (!foundFile)
              System.out.println(input2 + ": not found");
              break;
           }
           break;
          default:
          
          ProcessBuilder openFile = new ProcessBuilder(input);
          Process process = openFile.start();
          BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String s = null;
           while ((s = reader.readLine()) != null) {
            System.out.println(s);
           }
          /*
          else{
          System.out.println(input + ": command not found");
          }
          */
          break;
         }        
        }
    
    }
}
