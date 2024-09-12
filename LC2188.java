import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    /*
     * [2, 6] -> [7, 6]
     * [4] -> [9]
     * 
     * [1] -> [7]
     * [2, 4] -> [8, 4]
     * 
     * For each tyre find maximum lap
     * 7,
     * 8, 12 
     */
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        int[] lapCost = new int[numLaps + 1];
        Arrays.fill(lapCost, Integer.MAX_VALUE);
        for (var tyre : tires) {
            int totalCost = changeTime;
            long nextCost = tyre[0];
            int lap = 1;
            while (lap <= numLaps && nextCost < tyre[0] + changeTime) {
                totalCost += nextCost;
                lapCost[lap] = Math.min(lapCost[lap], totalCost);
                nextCost *= tyre[1];
                lap++;
            }
        }

        for (int i = 1; i <= numLaps; i++) {
            System.out.println(lapCost[i]);
        }

        Map<Integer, Integer> dp = new HashMap<>();

        return solve(lapCost, numLaps, dp) - changeTime;
    }

    private int solve(int[] lapCost, int numLaps, Map<Integer, Integer> dp) {
        if (numLaps == 0) {
            return 0;
        }

        if (dp.containsKey(numLaps)) {
            return dp.get(numLaps);
        }

        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= numLaps && lapCost[i] != Integer.MAX_VALUE; i++) {
            result = Math.min(result, lapCost[i] + solve(lapCost, numLaps - i, dp));
        }

        dp.put(numLaps, result);
        return result;
    }

    
}