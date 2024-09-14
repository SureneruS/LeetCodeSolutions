import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LC139
 */
public class LC140 {
    public static void main(String[] args) {
        Object[] testCase = { "somewords", List.of("some", "words") };
        executeAndPrint(Solution.class, testCase);
    }

    private static void executeAndPrint(Class<?> solutionClass, Object... params) {
        try {
            Object solutionObject = solutionClass.getDeclaredConstructor().newInstance();

            Method solutionMethod = Arrays.stream(solutionClass.getDeclaredMethods())
                    .filter(method -> Modifier.isPublic(method.getModifiers())).findFirst().orElseThrow();
            var solutionResult = solutionMethod.invoke(solutionObject, params);
            System.out.println(solutionResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int[] dp = new int[s.length()];

        return solve(0, s, dict, dp) == 1;
    }

    private int solve(int i, String s, Set<String> dict, int[] dp) {
        if (i == s.length()) {
            return 1;
        }

        if (dp[i] != 0) {
            return dp[i];
        }

        dp[i] = 1;
        for (int j = i + 1; j <= s.length(); j++) {
            if (dict.contains(s.substring(i, j))) {
                if (solve(j, s, dict, dp) == 1) {
                    return 1;
                }
            }
        }

        return dp[i] = -1;
    }
}