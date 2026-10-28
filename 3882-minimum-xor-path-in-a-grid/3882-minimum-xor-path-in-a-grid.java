import java.util.*;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        Set<Integer>[] dp = new HashSet[n];
        for (int j = 0; j < n; j++) dp[j] = new HashSet<>();

        dp[0].add(grid[0][0]);

        // First row: can only come from left
        for (int j = 1; j < n; j++) {
            for (int v : dp[j - 1]) {
                dp[j].add(v ^ grid[0][j]);
            }
        }

        for (int i = 1; i < m; i++) {
            // First column: only from above
            Set<Integer> first = new HashSet<>();
            for (int v : dp[0]) {
                first.add(v ^ grid[i][0]);
            }
            dp[0] = first;

            for (int j = 1; j < n; j++) {
                Set<Integer> cur = new HashSet<>();
                int val = grid[i][j];
                for (int v : dp[j]) cur.add(v ^ val);       // from above
                for (int v : dp[j - 1]) cur.add(v ^ val);   // from left
                dp[j] = cur;
            }
        }

        int min = Integer.MAX_VALUE;
        for (int v : dp[n - 1]) {
            if (v < min) min = v;
        }
        return min;
    }
}