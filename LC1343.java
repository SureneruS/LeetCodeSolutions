class Solution {
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int sum = 0;
        int sumThreshold = threshold * k;
        
        for (int i = 0; i < k && i < arr.length; i++) {
            sum += arr[i];
        }

        int count = 0;
        if (sum >= sumThreshold) {
            count++;
        }

        for (int i = k; i < arr.length; i++) {
            sum += arr[i] - arr[i - k];
            if (sum >= sumThreshold) {
                count++;
            }
        }

        return count;
    }
}