import java.util.Arrays;

class LC2226 {

    public int maximumCandies(int[] candies, long k) {
        long sum = Arrays.stream(candies).mapToLong(Long::valueOf).sum();
        if (sum < k) {
            return 0;
        }
        int lo = 1;
        int hi = 10000000;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (possible(mid, candies, k)) {
                lo = mid + 1;
            }
            else {
                hi = mid - 1;
            }
        }
        
        return lo - 1;
    }

    public static boolean possible(int val, int[] candies, long k) {
        long piles = Arrays.stream(candies).map(c -> c / val).mapToLong(Long::valueOf).sum();
        // System.out.println(val + " " + piles);
        return k <= piles;
    }
}