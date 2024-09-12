class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            System.out.println(mid);
            if (mid != 0 && nums[mid - 1] > nums[mid]) {
                right = mid;
            }
            else if (mid != nums.length - 1 && nums[mid + 1] > nums[mid]) {
                left = mid + 1;
            }
            else {
                return mid;
            }
        }

        return -1;
    }
}