import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<Object[]> getTestCases() {
        return List.of(new Object[][] {
                { "catsanddog", List.of("cat", "cats", "and", "sand", "dog") },
                { "pineapplepenapple", List.of("apple", "pen", "applepen", "pine", "pineapple") },
                { "catsandog", List.of("cats", "dog", "sand", "and", "cat") } });
    }
}