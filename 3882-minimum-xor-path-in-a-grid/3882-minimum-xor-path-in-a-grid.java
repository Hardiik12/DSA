class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // We use a 2D array flattened to 1D: dp[col][16] where each long represents 64 bits (XORs)
        // Size: n columns * 16 longs = n * 16
        long[] prev = new long[n * 16];
        long[] curr = new long[n * 16];
        
        // Initialize start cell
        int startVal = grid[0][0];
        prev[startVal >> 6] |= 1L << (startVal & 63);
        
        // First row
        for (int j = 1; j < n; j++) {
            int val = grid[0][j];
            int colBase = j * 16;
            int prevColBase = (j - 1) * 16;
            for (int k = 0; k < 16; k++) {
                long bits = prev[prevColBase + k];
                while (bits != 0) {
                    int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                    int next = v ^ val;
                    prev[colBase + (next >> 6)] |= 1L << (next & 63);
                    bits &= bits - 1;
                }
            }
        }
        
        // Remaining rows
        for (int i = 1; i < m; i++) {
            // Clear current row's first column block
            int firstColBase = 0;
            for (int k = 0; k < 16; k++) curr[firstColBase + k] = 0;
            
            // First column: only from above
            int val0 = grid[i][0];
            for (int k = 0; k < 16; k++) {
                long bits = prev[firstColBase + k];
                while (bits != 0) {
                    int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                    int next = v ^ val0;
                    curr[firstColBase + (next >> 6)] |= 1L << (next & 63);
                    bits &= bits - 1;
                }
            }
            
            // Remaining columns
            for (int j = 1; j < n; j++) {
                int val = grid[i][j];
                int colBase = j * 16;
                int leftColBase = (j - 1) * 16;
                
                // Clear current column block in 'curr'
                for (int k = 0; k < 16; k++) curr[colBase + k] = 0;
                
                // From above (prev[colBase])
                for (int k = 0; k < 16; k++) {
                    long bits = prev[colBase + k];
                    while (bits != 0) {
                        int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                        int next = v ^ val;
                        curr[colBase + (next >> 6)] |= 1L << (next & 63);
                        bits &= bits - 1;
                    }
                }
                
                // From left (curr[leftColBase])
                for (int k = 0; k < 16; k++) {
                    long bits = curr[leftColBase + k];
                    while (bits != 0) {
                        int v = (k << 6) | Long.numberOfTrailingZeros(bits);
                        int next = v ^ val;
                        curr[colBase + (next >> 6)] |= 1L << (next & 63);
                        bits &= bits - 1;
                    }
                }
            }
            
            // Swap prev and curr
            long[] temp = prev;
            prev = curr;
            curr = temp;
        }
        
        // Find minimum reachable XOR in the last column
        int lastColBase = (n - 1) * 16;
        for (int v = 0; v < 1024; v++) {
            if ((prev[lastColBase + (v >> 6)] & (1L << (v & 63))) != 0) {
                return v;
            }
        }
        return -1;
    }
}