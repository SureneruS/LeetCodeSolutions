import java.util.HashSet;
import java.util.Set;

class Solution {
    /*
     * 1 3 4 4 7 1
     * 1 2 4 1 3 2
     */
    public int flipgame(int[] fronts, int[] backs) {
        Set<Integer> notPossible = new HashSet<>();
        for (int i = 0; i < fronts.length; i++) {
            if (fronts[i] == backs[i]) {
                notPossible.add(fronts[i]);
            }
        }

        Integer minGood = Integer.MAX_VALUE;
        for (int i = 0; i < fronts.length; i++) {
            if (!notPossible.contains(fronts[i]) && fronts[i] < minGood) {
                minGood = fronts[i];
            }

            if (!notPossible.contains(backs[i]) && backs[i] < minGood) {
                minGood = backs[i];
            }
        }

        return minGood != Integer.MAX_VALUE ? minGood : 0;
    }
}