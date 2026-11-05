public class Reverse {
    public static int reverse(int num) {
        long result = 0;
        while (num != 0) {
            int digit = num % 10;
            result = result * 10 + digit;
            num = num / 10;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE ) {
            return 0;
        }
        return (int) result;
    }
    public static void main(String[] args){
        System.out.println(reverse(1200));
        System.out.println(reverse(-123));
        System.out.println(reverse(123));
        System.out.println(reverse(1534236469));
    }
}