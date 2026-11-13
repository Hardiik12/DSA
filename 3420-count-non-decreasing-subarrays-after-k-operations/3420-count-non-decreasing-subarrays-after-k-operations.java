class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;
        
        // Stack stores pairs: {value, count}. 
        // Values are strictly decreasing from front to back.
        java.util.ArrayDeque<long[]> stack = new java.util.ArrayDeque<>();
        long cost = 0;
        int r = n - 1; // right boundary of the window
        
        // l is the left boundary, moving from right to left
        for (int l = n - 1; l >= 0; l--) {
            long v = nums[l];
            long cnt = 1;
            
            // Merge elements in the stack that are smaller than v
            // (They must be raised to v to maintain non-decreasing order)
            while (!stack.isEmpty() && stack.peekFirst()[0] <= v) {
                long[] top = stack.pollFirst();
                cost += (v - top[0]) * top[1];
                cnt += top[1];
            }
            stack.addFirst(new long[]{v, cnt});
            
            // Shrink the window from the right if the cost is too large
            while (cost > k && r >= l) {
                long[] bottom = stack.peekLast();
                
                // If the rightmost element was raised by the stack, subtract its contribution
                if (nums[r] < bottom[0]) {
                    cost -= (bottom[0] - nums[r]);
                }
                
                bottom[1]--;
                if (bottom[1] == 0) {
                    stack.pollLast();
                }
                r--;
            }
            
            // Number of valid subarrays starting at l is (r - l + 1)
            ans += (r - l + 1);
        }
        
        return ans;
    }
}