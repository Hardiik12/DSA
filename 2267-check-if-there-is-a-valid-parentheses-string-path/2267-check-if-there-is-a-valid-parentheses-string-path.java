class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int totalLen = m + n - 1;

        // A valid parentheses string must have even length
        if (totalLen % 2 != 0) return false;
        // Must start with '(' and end with ')'
        if (grid[0][0] != '(' || grid[m - 1][n - 1] != ')') return false;

        int maxBal = totalLen;
        boolean[][][] dp = new boolean[m][n][maxBal + 1];
        dp[0][0][1] = true; // starting '(' gives balance 1

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;

                char c = grid[i][j];
                int delta = (c == '(') ? 1 : -1;
                int remaining = (m - 1 - i) + (n - 1 - j);

                for (int b = 0; b <= maxBal; b++) {
                    int prev = b - delta;
                    if (prev < 0 || prev > maxBal) continue;

                    boolean reachable = false;
                    if (i > 0 && dp[i - 1][j][prev]) reachable = true;
                    if (!reachable && j > 0 && dp[i][j - 1][prev]) reachable = true;

                    if (reachable) {
                        // Prune: balance cannot exceed remaining steps,
                        // and the difference must be even to reach 0.
                        if (b <= remaining && ((remaining - b) & 1) == 0) {
                            dp[i][j][b] = true;
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}