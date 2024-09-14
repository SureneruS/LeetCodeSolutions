class Solution {
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        int[] sumDP = new int[n];
        int[] effDP = new int[n];

        solve(speed, efficiency, n - 1, k, n, sumDP, effDP);

        return sumDP[n - 1] * effDP[n - 1];
    }

    private void solve(int[] speed, int[] efficiency, int i, int k, int n, int[] sumDP, int[] effDP) {
        if (k <= 0 || i < 0) {
            return;
        }

        if (sumDP[i] != 0 || effDP[i] != 0) {
            return;
        }

        int speedi = speed[i];
        int effi = efficiency[i];
        int perfi = speedi * effi;
    }
}