public class dp {
    /**
     * Calculates the maximum total number of tasks that can be completed over all days.
     * 
     * Time Complexity: O(n)
     * Auxiliary Space: O(1)
     */
    public static int maxTasks(int[] h, int[] l) {
        int n = h.length;
        if (n == 0) return 0;

        // prev2 represents dp[i-2] (max tasks up to day i-2)
        // prev1 represents dp[i-1] (max tasks up to day i-1)
        int prev2 = 0;
        int prev1 = Math.max(h[0], l[0]);

        for (int i = 1; i < n; i++) {
            // Option 1: Perform low-effort task today -> prev1 + l[i]
            // Option 2: Perform high-effort task today -> prev2 + h[i] (day i-1 had no task)
            int curr = Math.max(prev1 + l[i], prev2 + h[i]);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public static void main(String[] args) {
        // Example 1
        int[] h1 = {2, 8, 1};
        int[] l1 = {1, 2, 1};
        System.out.println("Example 1 Output: " + maxTasks(h1, l1)); // Expected: 9

        // Example 2
        int[] h2 = {3, 6, 8, 7, 6};
        int[] l2 = {1, 5, 4, 5, 3};
        System.out.println("Example 2 Output: " + maxTasks(h2, l2)); // Expected: 20
    }
}
