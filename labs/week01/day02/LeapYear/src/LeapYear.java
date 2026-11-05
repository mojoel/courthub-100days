public class LeapYear {

    // 方法 1：判断闰年
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 400 == 0 || year % 100 != 0);
    }

    // 方法 2：main 入口
    public static void main(String[] args) {
        // 测试 4 个年份
        System.out.println("1900 是闰年吗？" + isLeapYear(1900));
        System.out.println("2000 是闰年吗？" + isLeapYear(2000));
        System.out.println("2024 是闰年吗？" + isLeapYear(2024));
        System.out.println("2100 是闰年吗？" + isLeapYear(2100));
    }
}