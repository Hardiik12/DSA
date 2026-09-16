class Solution {
    public int numberOfSets(int n, int k) {
         int MOD = 1_000_000_007;
        
        long[][] dp0 = new long[n + 1][k + 1];
        long[][] dp1 = new long[n + 1][k + 1];
        
        dp0[0][0] = 1;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dp0[i + 1][j] = (dp0[i + 1][j] + dp0[i][j]) % MOD;
                dp1[i + 1][j] = (dp1[i + 1][j] + dp0[i][j]) % MOD;
                dp1[i + 1][j] = (dp1[i + 1][j] + dp1[i][j]) % MOD;
                
                if (j < k) {
                    dp0[i + 1][j + 1] = (dp0[i + 1][j + 1] + dp1[i][j]) % MOD;
                    dp1[i + 1][j + 1] = (dp1[i + 1][j + 1] + dp1[i][j]) % MOD;
                }
            }
        }
        
        return (int) dp0[n][k];
    }
}