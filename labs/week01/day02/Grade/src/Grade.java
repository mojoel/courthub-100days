public class Grade {
    public static String toGrade(int score) {
        switch(score / 10) {
            case (6):
                return "D";
            case (7):
                return "C";
            case (8):
                return "B";
            case (10):
            case (9):
                return "A";
            default:
                return "F";
        }
    }
    public static String toGradeArrow(int score) {
       return switch (score / 10) {
            case 6 -> "D";
            case 7 -> "C";
            case 8 -> "B";
            case 9,10 -> "A";
            default -> "F";
       };
    }
    public static void main(String[] args) {
        System.out.println(toGradeArrow(100));
        System.out.println(toGradeArrow(85));
        System.out.println(toGradeArrow(60));
        System.out.println(toGradeArrow(59));
    }
}
