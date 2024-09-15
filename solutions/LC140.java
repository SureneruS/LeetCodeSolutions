import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        List<List<String>> dp = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            dp.add(null);
        }

        return solve(0, s, dict, dp);
    }

    private List<String> solve(int i, String s, Set<String> dict, List<List<String>> dp) {
        if (i == s.length()) {
            return List.of("");
        }

        if (dp.get(i) != null) {
            return dp.get(i);
        }

        dp.set(i, new ArrayList<>());
        for (int j = i + 1; j <= s.length(); j++) {
            if (dict.contains(s.substring(i, j))) {
                List<String> possibilities = solve(j, s, dict, dp);
                for (var possibility : possibilities) {
                    if (possibility == "") {
                        dp.get(i).add(s.substring(i, j));
                    }
                    else {
                        dp.get(i).add(s.substring(i, j) + " " + possibility);
                    }
                }
            }
        }

        return dp.get(i);
    }

    public List<Object[]> getTestCases() {
        return List.of(new Object[][] {
                { "catsanddog", List.of("cat", "cats", "and", "sand", "dog") },
                { "pineapplepenapple", List.of("apple", "pen", "applepen", "pine", "pineapple") },
                { "catsandog", List.of("cats", "dog", "sand", "and", "cat") } });
    }
}