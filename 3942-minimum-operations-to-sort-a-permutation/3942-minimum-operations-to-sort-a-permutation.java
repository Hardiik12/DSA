class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int tA = -1, tB = -1;

        // ---- Type A: sorted = L^t(nums) ----
        int dA = (0 - nums[0]) % n;
        if (dA < 0) dA += n;
        boolean okA = true;

        for (int j = 0; j < n; j++) {
            int diff = (j - nums[j]) % n;
            if (diff < 0) diff += n;
            if (diff != dA) {
                okA = false;
                break;
            }
        }
        if (okA) tA = dA;

        // ---- Type B: sorted = L^t(reverse(nums)) ----
        // reverse(nums)[j] = nums[n - 1 - j]
        int dB = (0 - nums[n - 1]) % n;
        if (dB < 0) dB += n;
        boolean okB = true;

        for (int j = 0; j < n; j++) {
            int rev = nums[n - 1 - j];
            int diff = (j - rev) % n;
            if (diff < 0) diff += n;
            if (diff != dB) {
                okB = false;
                break;
            }
        }
        if (okB) tB = dB;

        int ans = Integer.MAX_VALUE;

        // Cost for a pure rotation L^t
        if (tA != -1) {
            int cost = Math.min(tA, (n - tA) % n + 2);
            ans = Math.min(ans, cost);
        }

        // Cost for a reflection L^t R
        if (tB != -1) {
            int cost = Math.min(tB + 1, (n - tB) % n + 1);
            ans = Math.min(ans, cost);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
