import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> wordsFreq = new HashMap<>();
        for (var word : words) {
            wordsFreq.put(word, wordsFreq.getOrDefault(word, 0) + 1);
        }
        Integer wordSize = words[0].length();
        Integer wordCount = words.length;
        Integer requiredSize = wordSize * wordCount;

        List<Integer> result = new ArrayList<>();
        Map<Integer, Map<String, Integer>> dp = new HashMap<>();

        for (int i = 0; i + requiredSize <= s.length(); i++) {
            var left = getWordsFromIndex(i, s, wordSize, wordsFreq, dp);
            var right = getWordsFromIndex(i + requiredSize, s, wordSize, wordsFreq, dp);
            // System.out.println(i + " " + left + " " + (i + requiredSize) + " " + right);
            int counter = 0;
            for (var word : left.keySet()) {
                if (left.get(word) - right.getOrDefault(word, 0) == wordsFreq.get(word)) {
                    counter++;
                }
            }

            if (counter == wordCount) {
                result.add(i);
            }
        }

        return result;
    }

    private Map<String, Integer> getWordsFromIndex(int index, String s, int wordSize, Map<String, Integer> wordsFreq, Map<Integer, Map<String, Integer>> dp) {
        if (index + wordSize > s.length()) {
            return Map.of();
        }

        if (dp.containsKey(index)) {
            return dp.get(index);
        }

        String currentWord = s.substring(index, index + wordSize);
        if (!wordsFreq.containsKey(currentWord)) {
            return Map.of();
        }

        Map<String, Integer> encountered = new HashMap<>(getWordsFromIndex(index + wordSize, s, wordSize, wordsFreq, dp));
        // System.out.println(index + ": " + encountered);
        encountered.put(currentWord, encountered.getOrDefault(currentWord, 0) + 1);
        // System.out.println(index + ": " + encountered);
        
        dp.put(index, encountered);
        // System.out.println(index + ": " + encountered);        
        // System.out.println(index + " " + dp);
        return encountered;
    }
}

/*
 * ab bc ca
 * 
 * abccaab
 * abbcabcab
 * abbcxxabbcca
 * 
 * ab cd bc de fg
 * abcdefgabcd
 */