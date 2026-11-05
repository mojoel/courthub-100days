import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    public static void main(String[] args) {
        Random rd = new Random();
        int target = rd.nextInt(100)+1;
        int count = 0;
        Scanner  sc = new Scanner(System.in);
        while (true) {
            int num = sc.nextInt();
            count++;
            if(num < target){
                System.out.println("猜小了");
            }
            else if(num > target)
            {
                System.out.println("猜大了");
            }
            else
            {
                System.out.println("猜对了");
                break;
            }
        }
        System.out.println("一共猜了 " + count + " 次");
    }
}
