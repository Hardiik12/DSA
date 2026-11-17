import java.util.Arrays;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int len = j - i + 1;
                if (len == 1) {
                    cost[i][j] = Integer.MAX_VALUE / 2; // Invalid length
                    continue;
                }
                
                int minChanges = Integer.MAX_VALUE;
                for (int d = 1; d < len; d++) {
                    if (len % d == 0) {
                        int currentChanges = 0;
                        for (int r = 0; r < d; r++) {
                            int left = i + r;
                            int right = i + (len / d - 1) * d + r;
                            
                            while (left < right) {
                                if (s.charAt(left) != s.charAt(right)) {
                                    currentChanges++;
                                }
                                left += d;
                                right -= d;
                            }
                        }
                        minChanges = Math.min(minChanges, currentChanges);
                    }
                }
                cost[i][j] = minChanges;
            }
        }
        
        int[][] dp = new int[n + 1][k + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        
        for (int i = 2; i <= n; i++) { // Start from 2 since min length is 2
            for (int j = 1; j <= k; j++) {
                // p must be such that length of substring s[p..i-1] is at least 2
                // so p <= i - 2
                for (int p = 0; p <= i - 2; p++) {
                    if (dp[p][j - 1] != Integer.MAX_VALUE / 2) {
                        dp[i][j] = Math.min(dp[i][j], dp[p][j - 1] + cost[p][i - 1]);
                    }
                }
            }
        }
        
        return dp[n][k];
    }
}