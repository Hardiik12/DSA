import java.util.Arrays;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        // dp[j] stores reachable XORs for column j using a bitmask.
        // Since XOR < 1024, we need 1024 bits, which fits in 16 longs (16 * 64 = 1024).
        long[][] dp = new long[n][16];
        
        // Initialize starting cell (0, 0)
        dp[0][grid[0][0] >> 6] |= 1L << (grid[0][0] & 63);
        
        // First row: can only come from the left
        for (int j = 1; j < n; j++) {
            int val = grid[0][j];
            for (int k = 0; k < 16; k++) {
                long bits = dp[j - 1][k];
                while (bits != 0) {
                    int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                    int next = v ^ val;
                    dp[j][next >> 6] |= 1L << (next & 63);
                    bits &= bits - 1; // clear lowest set bit
                }
            }
        }
        
        // Remaining rows
        for (int i = 1; i < m; i++) {
            // First column: can only come from above
            int val0 = grid[i][0];
            long[] nextFirst = new long[16];
            for (int k = 0; k < 16; k++) {
                long bits = dp[0][k];
                while (bits != 0) {
                    int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                    int next = v ^ val0;
                    nextFirst[next >> 6] |= 1L << (next & 63);
                    bits &= bits - 1;
                }
            }
            dp[0] = nextFirst;
            
            // Rest of the columns
            for (int j = 1; j < n; j++) {
                int val = grid[i][j];
                long[] cur = new long[16];
                
                // From above
                for (int k = 0; k < 16; k++) {
                    long bits = dp[j][k];
                    while (bits != 0) {
                        int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                        int next = v ^ val;
                        cur[next >> 6] |= 1L << (next & 63);
                        bits &= bits - 1;
                    }
                }
                
                // From left
                for (int k = 0; k < 16; k++) {
                    long bits = dp[j - 1][k];
                    while (bits != 0) {
                        int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                        int next = v ^ val;
                        cur[next >> 6] |= 1L << (next & 63);
                        bits &= bits - 1;
                    }
                }
                dp[j] = cur;
            }
        }
        
        // Find the minimum XOR in the last column
        for (int v = 0; v < 1024; v++) {
            if ((dp[n - 1][v >> 6] & (1L << (v & 63))) != 0) {
                return v;
            }
        }
        return -1;
    }
}