import java.util.Random;
import java.util.Scanner;

public class LotteryMachine {


    public static void main(String[] args) {
        System.out.println("Insert your lucky numbers:");
        int userLucky[] = new int[6];
        Scanner me = new Scanner(System.in);
        
        for (int i = 0; i < userLucky.length; i++) {
            int userIn = me.nextInt();
            userLucky[i] = userIn;
        }
        
        int luckyNumbers[] = new int[6];
        Random rand = new Random();
        for (int i = 0; i < luckyNumbers.length; i++) {
            luckyNumbers[i] = rand.nextInt(49);
        }
        
        int count =0;
        for (int i = 0; i < luckyNumbers.length; i++) {
            for (int j = 0; j < userLucky.length; j++) {
                if (userLucky[i] == luckyNumbers[j]) {
                    count++;
                }
            }
        }
        System.out.println("Your lucky numbers:");
        for (int i = 0; i < luckyNumbers.length; i++) {
            System.out.print(userLucky[i]+"\t");
        }
        
        System.out.println("\nToday's lucky numbers:");
        for (int i = 0; i < luckyNumbers.length; i++) {
            System.out.print(luckyNumbers[i]+"\t");
        }
        
        System.out.println("");
        if (count <= 1) {
            System.out.println("Better luck next time");
        }
        else if (count == 2){
            System.out.println("You won R100");
        }
        else if (count == 3){
            System.out.println("You won R1000");
        }
        else if (count == 4){
            System.out.println("You won R10 000");
        }
        else if (count == 5){
            System.out.println("You won R100 000");
        }
        else{
            System.out.println("Congradulations!!!!! you won the won the jackpot of R1000 000");
        }
        
        
    }
    
}
