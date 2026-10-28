import java.util.BitSet;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // 1D array of BitSets. dp[j] stores the reachable XORs for column j.
        // 1024 bits per column, perfectly packed.
        BitSet[] dp = new BitSet[n];
        for (int j = 0; j < n; j++) {
            dp[j] = new BitSet(1024);
        }
        
        // Start cell
        dp[0].set(grid[0][0]);
        
        // First row: only from the left
        for (int j = 1; j < n; j++) {
            int val = grid[0][j];
            BitSet prev = dp[j - 1];
            BitSet curr = dp[j];
            for (int v = prev.nextSetBit(0); v >= 0; v = prev.nextSetBit(v + 1)) {
                curr.set(v ^ val);
            }
        }
        
        // Remaining rows
        for (int i = 1; i < m; i++) {
            // First column: only from above (reusing dp[0])
            int val0 = grid[i][0];
            BitSet temp0 = new BitSet(1024);
            BitSet prev0 = dp[0];
            for (int v = prev0.nextSetBit(0); v >= 0; v = prev0.nextSetBit(v + 1)) {
                temp0.set(v ^ val0);
            }
            dp[0] = temp0;
            
            // Rest of the columns
            for (int j = 1; j < n; j++) {
                int val = grid[i][j];
                BitSet temp = new BitSet(1024);
                
                // From above: dp[j] currently holds the previous row's values
                BitSet fromTop = dp[j];
                for (int v = fromTop.nextSetBit(0); v >= 0; v = fromTop.nextSetBit(v + 1)) {
                    temp.set(v ^ val);
                }
                
                // From left: dp[j-1] holds the current row's values
                BitSet fromLeft = dp[j - 1];
                for (int v = fromLeft.nextSetBit(0); v >= 0; v = fromLeft.nextSetBit(v + 1)) {
                    temp.set(v ^ val);
                }
                
                dp[j] = temp; // Replace old column with new
            }
        }
        
        // Find the minimum reachable XOR in the last column
        return dp[n - 1].nextSetBit(0);
    }
}