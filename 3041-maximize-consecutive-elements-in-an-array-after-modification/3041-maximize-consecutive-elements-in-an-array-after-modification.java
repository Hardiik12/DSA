import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        // dp[v] = max length of consecutive sequence ending at value v
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 0;
        
        for (int x : nums) {
            // Read the previous states BEFORE updating them
            int lenEndAtXMinus1 = dp.getOrDefault(x - 1, 0);
            int lenEndAtX = dp.getOrDefault(x, 0);
            
            // Option 1: leave x as x. Extends sequence ending at x-1.
            int len1 = lenEndAtXMinus1 + 1;
            // Option 2: increase x to x+1. Extends sequence ending at x.
            int len2 = lenEndAtX + 1;
            
            // Update dp states
            dp.put(x, Math.max(dp.getOrDefault(x, 0), len1));
            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0), len2));
            
            maxLen = Math.max(maxLen, Math.max(len1, len2));
        }
        
        return maxLen;
    }
}