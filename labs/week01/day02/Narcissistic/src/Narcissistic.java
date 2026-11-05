public class Narcissistic {
    public static boolean isNarcissistic(int num) {
        int ge = num % 10;
        int shi = num / 10 % 10;
        int bai = num / 100;
        return num == ge * ge * ge + shi * shi * shi + bai * bai * bai;
    }
    public static void main(String[] args) {
        for (int i = 100; i <= 999; i++) {
            if (isNarcissistic(i)) {
                System.out.print(i+" ");
            }
        }
    }
}