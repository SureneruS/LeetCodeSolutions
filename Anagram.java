import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Anagram implements Comparable<Anagram> {
    String sorted;
    String original;
    
    Anagram(String original) {
        char[] temp = original.toCharArray();
        Arrays.sort(temp);
        this.sorted = new String(temp);
        this.original = original;
    }

    @Override
    public int compareTo(Anagram that) {
        return this.sorted.compareTo(that.sorted);
    }

    public String toString() {
        return String.format("Anagram{sorted=%s, original=%s}", sorted, original);
    }
}

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<Anagram> anagrams = new ArrayList<>();
        for (var word : strs) {
            anagrams.add(new Anagram(word));
        }
        Collections.sort(anagrams);
        System.out.println(anagrams);
        List<List<String>> result = new ArrayList<>();
        List<String> currentAnagrams = new ArrayList<>();
        String lastAnagram = "";
        
        for (var anagram : anagrams) {
            if (lastAnagram == "" || !lastAnagram.equals(anagram.sorted)) {
                currentAnagrams = new ArrayList<>();
                result.add(currentAnagrams);
            }

            currentAnagrams.add(anagram.original);
            lastAnagram = anagram.sorted;
        }
        
        return result;
    }
}