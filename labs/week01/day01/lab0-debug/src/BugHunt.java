/**
 * Lab0-1 断点调试（老师生成的唯一一段 Day 1 代码）
 * 规格：6 个成绩，60 分及以上算及格。
 * 期望输出：
 *   sum = 415
 *   passCount = 4
 *   riseCount = 2      （有几个成绩比它的上一个成绩高）
 * 实际输出和期望不一致。请只用断点找原因，先不要靠眼睛读代码猜。
 */
public class BugHunt {

    static boolean isPass(int score) {
        return score >= 60;
    }

    public static void main(String[] args) {
        int[] scores = {85, 42, 77, 60, 93, 58};

        int sum = 0;
        int passCount = 0;
        int riseCount = 0;
        int prev = scores[0];

        for (int i = 0; i <= scores.length - 1; i++) {
            int s = scores[i];
            sum += s;
            if (isPass(s)) {
                passCount++;
            }

            if (s > prev) {
                prev = s;
                riseCount++;
            }

        }

        System.out.println("sum = " + sum);
        System.out.println("passCount = " + passCount);
        System.out.println("riseCount = " + riseCount);
    }
}
