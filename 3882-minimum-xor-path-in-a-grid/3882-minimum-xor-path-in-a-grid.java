import java.util.BitSet;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // dp[j] stores the reachable XOR values for the current column j
        BitSet[] dp = new BitSet[n];
        for (int j = 0; j < n; j++) {
            dp[j] = new BitSet(1024);
        }
        
        dp[0].set(grid[0][0]);
        
        // First row (only from left)
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
            // First column (only from top)
            int val0 = grid[i][0];
            BitSet prev0 = dp[0];
            BitSet curr0 = new BitSet(1024);
            for (int v = prev0.nextSetBit(0); v >= 0; v = prev0.nextSetBit(v + 1)) {
                curr0.set(v ^ val0);
            }
            dp[0] = curr0;
            
            // Remaining columns (from top and left)
            for (int j = 1; j < n; j++) {
                int val = grid[i][j];
                BitSet top = dp[j];      // currently holds previous row's states
                BitSet left = dp[j - 1]; // holds current row's states
                BitSet curr = new BitSet(1024);
                
                for (int v = top.nextSetBit(0); v >= 0; v = top.nextSetBit(v + 1)) {
                    curr.set(v ^ val);
                }
                for (int v = left.nextSetBit(0); v >= 0; v = left.nextSetBit(v + 1)) {
                    curr.set(v ^ val);
                }
                
                dp[j] = curr;
            }
        }
        
        // The lowest set bit is the minimum XOR
        return dp[n - 1].nextSetBit(0);
    }
}