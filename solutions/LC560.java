import java.util.HashMap;
import java.util.Map;

class LC560 {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);
        int prefixSum = 0;
        int result = 0;
        for (int num: nums) {
            prefixSum += num;
            result += prefixCount.getOrDefault(prefixSum - k, 0);
            prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
        }

        return result;
    }
}