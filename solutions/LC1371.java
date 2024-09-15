import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int findTheLongestSubstring(String s) {
        Map<Integer, Integer> maskPos = new HashMap<>();
        int currentMask = 0;
        int result = 0;
        maskPos.put(0, -1);
        for (int i = 0; i < s.length(); i++) {
            currentMask = flipBit(currentMask, s.charAt(i));
            if (maskPos.containsKey(currentMask)) {
                result = Math.max(result, i - maskPos.get(currentMask));

            }
            else {
                maskPos.put(currentMask, i);
            }
        }

        return result;
    }

    static Map<Character, Integer> vowelMap = Map.of('a', 0, 'e', 1, 'i', 2, 'o', 3, 'u', 4);
    private int flipBit(int mask, char ch) {
        if (vowelMap.containsKey(ch)) {
            return mask ^ (1 << vowelMap.get(ch));
        }
        
        return mask;
    }

    public List<Object[]> getTestCases() {
        return List.of(new Object[][] {
                { "eleetminicoworoep" },
                { "leetcodeisgreat" },
                { "bcbcbc" }
        });
    }
}