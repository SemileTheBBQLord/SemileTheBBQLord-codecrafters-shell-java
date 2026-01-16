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
        /*
        In this function, the ProcessBuilder class is used with the parameter variable to later 
        use the start method from the class Process. If the start method executes a file with the variable, 
        then it knows that variable is a executable file and returns the boolean class as true while if the start 
        function causes a error, then it returns the class as false and verifies that the input is not a executable file.
        */
            ProcessBuilder chkprocess = new ProcessBuilder(in);
            Process verify = chkprocess.start();
            return verify.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
    //This cdHandler class is used to change the current directory into a new directory path.
    public static File cdHandler(File currentDir, String dir) {
    /*
    In this function, it uses the Path class to get the current dirctory from the file parameter with the getPath method.
    Then, an another Path variable is created and serves as a new directory by using a resolove method to combine the current directory with
    the dir String variable, convert it to a absolute path, and returns the path without the redudant name elements to get the exact 
    right directory to obtain and prevent potential confusion problems within the program. Created a File newDir variable that serves as the 
    new directory from the newPath variable coverted into a File variable with the toFile method to use a isDirectory method verify if the newDir variable 
    is a directory alongside with the condition based if the method is verified true or false because the isDirectory method is a efficient way to verify the new directory. 
    If the condition's true, then it returns a newDir variable as a new directory and if not, then it prints out a message to 
    the user that there's no such file or directory and returns the current directory instead. 
    */
     Path currentPath = Paths.get(currentDir.getPath());
     Path newPath = currentPath.resolve(dir).toAbsolutePath().normalize();
     File newDir = newPath.toFile();
     if (newDir.isDirectory()) {
       return newDir;
     }
     System.out.println(newDir + ": No such file or directory");
     return currentDir;
    }
    
    //This is where the main program starts.
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in); //Created a scanner object to enter inputs to utilize with.
        
        String filePath = System.getenv("PATH"); //created a variable that represents the path of this program that can be utilized later.
        File f = new File(""); //Represents the current directory.
        String[] paths = filePath.split(":"); //To be used in the 'type' command.
        
        Random r = new Random();
        String input;
        String[] inputs;
        int status, numberOfSpaces;
        int j = r.nextInt(1000); //To restrict the random number generation to 1 to 1000.
        
        numberOfSpaces = 0;
        
        //Utilized a loop so that the system can initiate as many commands as possible in the console based on the entered inputs.
        
        for (int i = 0;i < j;i++){
        System.out.print("$ ");
        
        input = scanner.nextLine(); //This variable serves as a user's entered input value.
        
        //Splits the input variable into multiple strings so that the system can identify and perform commands based on the variable.
        inputs = input.split(" "); 
        
        /*A switch statement is used rather than the else if to make the code look less repitive and efficient. It is also
        cause switch statements are case sensitive and doesn't use as much performance as the ifs and elses.
        */
        switch (inputs[0]){
         case "echo":
         /*Utilized a for loop to print out the rest of the variables in the array in a single line except the first element cause the array is utilized in 
         this command, so the system needs a loop to fully present the message from the array.
         */
          for (int z = 1; z < inputs.length; z++){
           System.out.print(inputs[z] + " ");
          }
          System.out.print("\n"); //to prevent the error of not recieving the expected prompt of " " after the echo command.
          break;
         case "exit":
          //Serve as a suitable method for the user to fully exit the program.
          status = Integer.parseInt(inputs[1]);
          System.exit(status);     
          break;
         case "type":
          //For this one, another switch statement is made to minimize the performance usage and deal with the repeating results.
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
           //Utilizes the boolean variable and the for loop to check if the second and above elements in the inputs array exists in the path directory.
           //This is mainly used because the inputs and the path the system checks are in a array.
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
          //utilizes the condition and utilizing the equals method because its accurate in checking specific letters or chars in a string variable.
           if (inputs[1].equals("~")){
           f = new File(System.getenv("HOME"));
           break;
           }
           else {
           //It's utilized like this because its simpler and efficient to create a class that can handles directories rather than manually creating it in a main program.
           f = cdHandler(f, inputs[1]); //Uses the cdHandler class to change the current directory into a new directory based on the second element of inputs variable.
           }
           break;
          default:
          //Created these lists of code in the default condition cause it can't be easily implemented when creating a specific condition in a switch statement, so it's implemented in a default condition instead.
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
         } //end of first switch function.        
        }
    
    }
}
