import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        boolean[] done = new boolean[nums.length];
        return permuteRemaining(nums, done);
    }

    List<List<Integer>> permuteRemaining(int[] nums, boolean[] done) {
        List<List<Integer>> result = new ArrayList<>();

        boolean allDone = true;
        for (int i = 0; i < nums.length; i++) {
            if (!done[i]) {
                allDone = false;
                done[i] = true;
                var subPerm = permuteRemaining(nums, done);
                for (var p : subPerm) {
                    p.add(nums[i]);
                    result.add(p);
                }
                done[i] = false;
            }
        }

        if (allDone) {
            result.add(new ArrayList<>());
        }

        return result;
    }
}