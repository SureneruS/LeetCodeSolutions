import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        List<List<Integer>> richerThan = new ArrayList<>();
        for (int i = 0; i < quiet.length; i++) {
            richerThan.add(new ArrayList<>());
        }
        
        for (int i = 0; i < richer.length; i++) {
            richerThan.get(richer[i][1]).add(richer[i][0]);
        }

        int[] loudAndRichList = new int[quiet.length];
        Arrays.fill(loudAndRichList, -1);
        for (int i = 0; i < quiet.length; i++) {
            loudAndRichList[i] = findLoudAndRichFor(i, richerThan, loudAndRichList, quiet);
        }

        return loudAndRichList;
    }

    private int findLoudAndRichFor(int i, List<List<Integer>> richerThan, int[] loudAndRichList, int[] quiet) {
        if (loudAndRichList[i] != -1) {
            return loudAndRichList[i];
        }

        loudAndRichList[i] = i;
        for (var richer : richerThan.get(i)) {
            var candidate = findLoudAndRichFor(richer, richerThan, loudAndRichList, quiet);
            if (quiet[candidate] < quiet[loudAndRichList[i]]) {
                loudAndRichList[i] = candidate;
            }
        }

        return loudAndRichList[i];
    }
    
}