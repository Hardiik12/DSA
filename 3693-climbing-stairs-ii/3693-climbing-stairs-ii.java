class Solution {
    public int climbStairs(int n, int[] costs) {
        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int jump = 1; jump <= 3; jump++) {
                int j = i - jump;
                if (j < 0) break;
                if (dp[j] == Long.MAX_VALUE) continue;
                long cost = dp[j] + costs[i - 1] + (long) jump * jump;
                dp[i] = Math.min(dp[i], cost);
            }
        }

        return (int) dp[n];
    }
}