class Solution {
    public int minOperations(int[] nums, int x) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }

        int target = total - x;

        // Impossible to reduce x to 0
        if (target < 0) return -1;

        // We must remove all elements
        if (target == 0) return nums.length;

        int left = 0;
        int sum = 0;
        int maxLen = 0;

        // Find longest subarray with sum == target
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > target) {
                sum -= nums[left++];
            }

            if (sum == target) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        return maxLen == 0 ? -1 : nums.length - maxLen;
    }
}