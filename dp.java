public class dp {
    /**
     * Calculates the maximum total number of tasks that can be completed over all days.
     * Universal solution handling null, empty arrays, length mismatches, single-element, and arbitrary arrays.
     *
     * @param h Array of high-effort task completions per day
     * @param l Array of low-effort task completions per day
     * @return Maximum total tasks achievable
     * 
     * Time Complexity: O(n)
     * Auxiliary Space: O(1)
     */
    public static int maxTasks(int[] h, int[] l) {
        // Universal guard checks for invalid, null, empty, or mismatched arrays
        if (h == null || l == null) return 0;
        int n = h.length;
        if (n == 0 || l.length != n) return 0;

        // prev2 represents dp[i-2] (max tasks up to day i-2)
        // prev1 represents dp[i-1] (max tasks up to day i-1)
        int prev2 = 0;
        int prev1 = Math.max(0, Math.max(h[0], l[0]));

        for (int i = 1; i < n; i++) {
            // Option 1: Perform no task on day i -> prev1
            // Option 2: Perform low-effort task on day i -> prev1 + l[i]
            // Option 3: Perform high-effort task on day i -> prev2 + h[i] (day i-1 had no task)
            int lowEffort = prev1 + l[i];
            int highEffort = prev2 + h[i];
            int noTask = prev1;

            int curr = Math.max(noTask, Math.max(lowEffort, highEffort));
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public static void main(String[] args) {
        // Test Case 1: Standard Example 1
        int[] h1 = {2, 8, 1};
        int[] l1 = {1, 2, 1};
        System.out.println("Test Case 1 Output: " + maxTasks(h1, l1) + " (Expected: 9)");

        // Test Case 2: Standard Example 2
        int[] h2 = {3, 6, 8, 7, 6};
        int[] l2 = {1, 5, 4, 5, 3};
        System.out.println("Test Case 2 Output: " + maxTasks(h2, l2) + " (Expected: 20)");

        // Test Case 3: Single Element Array
        int[] h3 = {5};
        int[] l3 = {2};
        System.out.println("Test Case 3 Output: " + maxTasks(h3, l3) + " (Expected: 5)");

        // Test Case 4: Empty Array
        int[] h4 = {};
        int[] l4 = {};
        System.out.println("Test Case 4 Output: " + maxTasks(h4, l4) + " (Expected: 0)");

        // Test Case 5: All Zeroes
        int[] h5 = {0, 0, 0};
        int[] l5 = {0, 0, 0};
        System.out.println("Test Case 5 Output: " + maxTasks(h5, l5) + " (Expected: 0)");

        // Test Case 6: High effort always better with skips
        int[] h6 = {10, 20, 30};
        int[] l6 = {1, 1, 1};
        System.out.println("Test Case 6 Output: " + maxTasks(h6, l6) + " (Expected: 40)");
    }
}
