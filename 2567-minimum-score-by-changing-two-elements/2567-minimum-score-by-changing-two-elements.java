import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        
        // Option 1: Change the two smallest elements (indices 0 and 1)
        int diff1 = nums[n - 1] - nums[2];
        
        // Option 2: Change the two largest elements (indices n-2 and n-1)
        int diff2 = nums[n - 3] - nums[0];
        
        // Option 3: Change the smallest and the largest element (indices 0 and n-1)
        int diff3 = nums[n - 2] - nums[1];
        
        return Math.min(diff1, Math.min(diff2, diff3));
    }
}