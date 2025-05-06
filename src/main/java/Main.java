import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage

        Scanner scanner = new Scanner(System.in);
        
        Random r = new Random();
        
        int j = r.nextInt(1000);
        for (int i = 0;i < j;i++){
        System.out.print("$ ");
        String input = scanner.nextLine();
        if (input == "exit 0"){
        System.exit(0);
        }
        System.out.println(input + ": command not found");
        }
    
    }
}
