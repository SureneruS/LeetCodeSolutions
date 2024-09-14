class Solution {
    /*
     * n = 5
     * goal = 6
     * k = 0
     * 1 f(0) 1 f(4)
     * 1 f(1) 1 f(3)
     * 1 f(2) 1 f(2)
     * 1 f(3) 1 f(1)
     * 1 f(4) 1 f(0)
     * 1 f(5)
     * f(1, 1, ?) = 1
     * f(n, g, k) = n * f(n - 1, g - 1, k) +  ( n - k) * f(n, g - 1, k)
     * 1 2 3 1 2 3
     * 1 2 3 
     * 
     */
    public int numMusicPlaylists(int n, int goal, int k) {
        long[][] dp = new long[n + 1][goal + 1];
        dp[0][0] = 1;
        int mod = 1000000007;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= goal; j++) {
                dp[i][j] = (i * dp[i - 1][j - 1]) % mod;
                if (i > k) {
                    dp[i][j] = (dp[i][j] + (dp[i][j - 1] * (i - k)) % mod ) % mod;
                }
            }
        }

        return (int)dp[n][goal];
    }
}