import java.util.Arrays;

class Solution {
    /**
     * Calculates maximum tasks completed over all days.
     * Universal solution handling null, empty arrays, length mismatches, single-element, and arbitrary arrays.
     *
     * @param h Array of high-effort task completions per day
     * @param l Array of low-effort task completions per day
     * @return Maximum total tasks achievable
     * 
     * Time Complexity: O(n)
     * Auxiliary Space: O(1)
     */
    public int maxTasks(int[] h, int[] l) {
        if (h == null || l == null) return 0;
        int n = h.length;
        if (n == 0 || l.length != n) return 0;

        int prev2 = 0; // dp[i-2]
        int prev1 = Math.max(0, Math.max(h[0], l[0])); // dp[i-1] for i=1

        for (int i = 1; i < n; i++) {
            int lowEffort = prev1 + l[i];
            int highEffort = prev2 + h[i];
            int noTask = prev1;

            int curr = Math.max(noTask, Math.max(lowEffort, highEffort));
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }
}
