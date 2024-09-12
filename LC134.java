public class LC134 {

}

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int[] circuit = new int[n * 2];

        for (int i = 0; i < 2*n + 1; i++) {
            circuit[i] = gas[i%n] - cost[i%n];
        }

        int left = 0;
        int right = 1;

        int fuel = 0;

        while (right <= circuit.length) {
            fuel += circuit[right - 1];
            if (fuel < 0) {
                fuel = 0;
                left = right;
            }
            else {
                if (right - left - 1 == n) {
                    return left;
                }
            }
            right++;
        }

        return -1;
    }
}