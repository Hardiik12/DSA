import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int low = 1, high = n;
        int ans = n;
        
        // Random base to prevent hash collisions
        long B = 131 + new Random().nextInt(100000) * 2 + 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUniqueSubarray(nums, mid, B)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return ans;
    }
    
    private boolean hasUniqueSubarray(int[] nums, int L, long B) {
        int n = nums.length;
        if (L > n) return false;
        
        long highestPower = 1;
        for (int i = 0; i < L - 1; i++) {
            highestPower *= B;
        }
        
        long hash = 0;
        for (int i = 0; i < L; i++) {
            hash = hash * B + nums[i];
        }
        
        Set<Long> seenOnce = new HashSet<>();
        Set<Long> seenMultiple = new HashSet<>();
        
        seenOnce.add(hash);
        
        for (int i = L; i < n; i++) {
            hash = (hash - nums[i - L] * highestPower) * B + nums[i];
            
            if (!seenMultiple.contains(hash)) {
                if (seenOnce.contains(hash)) {
                    seenOnce.remove(hash);
                    seenMultiple.add(hash);
                } else {
                    seenOnce.add(hash);
                }
            }
        }
        
        return !seenOnce.isEmpty();
    }
}