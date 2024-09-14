import java.util.HashMap;
import java.util.Map;

/**
 * LC76
 */
public class LC76 {

    public static void main(String[] args) {
        Solution sol = new Solution();

        System.out.println(sol.minWindow("ADOBECODEBANC", "ABC"));
    }
}

class Solution {
    public String minWindow(String s, String t) {
        Map<Character, Integer> tMap = new HashMap<>();
        for (char ch : t.toCharArray()) {
            tMap.put(ch, tMap.getOrDefault(ch, 0) + 1);
        }

        int start = 0;
        int end = 0;
        int minStart = 0, minEnd = Integer.MAX_VALUE;
        while (++end <= s.length()) {
            char current = s.charAt(end - 1);
            if (!tMap.containsKey(current)) {
                continue;
            }

            tMap.put(current, tMap.get(current) - 1);
            if (allSatisfied(tMap)) {
                char startChar = s.charAt(start);
                while (!tMap.containsKey(startChar) || tMap.get(startChar) < 0) {
                    if (tMap.containsKey(startChar)) {
                        tMap.put(startChar, tMap.get(startChar) + 1);
                    }
                    startChar = s.charAt(++start);
                }

                if (end - start < minEnd - minStart) {
                    minStart = start;
                    minEnd = end;
                }
            }
        }

        return minEnd == Integer.MAX_VALUE ? "" : s.substring(minStart, minEnd);
    }

    private boolean allSatisfied(Map<Character, Integer> tMap) {
        for(var value : tMap.values()) {
            if (value > 0) {
                return false;
            }
        }
        return true;
    }
}