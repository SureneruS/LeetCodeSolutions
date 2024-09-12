import java.util.Arrays;

class TestStream {
    public static void main(String[] args) {
        int[] arr = {2147483647, 2147483647, 2147483647, 2147483647};
        long sum = Arrays.stream(arr).mapToLong(Long::valueOf).sum();
        System.out.println(sum);
    }

    public int maximumCandies(int[] candies, long k) {
        long sum = Arrays.stream(candies).mapToLong(Long::valueOf).sum();
        if (sum > k) {
            return 0;
        }
        int lo = 1;
        int hi = 10e7;

        

    }
}