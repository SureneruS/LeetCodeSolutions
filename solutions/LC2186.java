import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minSteps(String s, String t) {
        Map<Character, Integer> count = new HashMap<>();
        for (char ch : s.toCharArray()) {
            count.put(ch, count.getOrDefault(ch, 0) + 1);
        }
        for (char ch : t.toCharArray()) {
            count.put(ch, count.getOrDefault(ch, 0) - 1);
        }

        int result = 0;
        for (var val : count.values()) {
            result += Math.abs(val);
        }

        return result;
    }
}