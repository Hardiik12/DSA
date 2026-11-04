class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n];
        Arrays.fill(cost, Long.MAX_VALUE);
        
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int[] uniq = new int[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i-1]) uniq[m++] = sorted[i];
        }
        
        int[] bitCnt = new int[m + 1];
        long[] bitSum = new long[m + 1];
        long windowSum = 0;
        
        for (int i = 0; i < n; i++) {
            int ci = lowerBound(uniq, m, nums[i]) + 1;
            bitAdd(bitCnt, ci, 1);
            bitAddSum(bitSum, ci, nums[i]);
            windowSum += nums[i];
            
            if (i >= x) {
                int out = nums[i - x];
                int co = lowerBound(uniq, m, out) + 1;
                bitAdd(bitCnt, co, -1);
                bitAddSum(bitSum, co, -out);
                windowSum -= out;
            }
            
            if (i >= x - 1) {
                int start = i - x + 1;
                int rank = (x + 1) / 2;
                int medIdx = bitKth(bitCnt, m, rank);
                int med = uniq[medIdx - 1];
                int cntLe = bitQuery(bitCnt, medIdx);
                long sumLe = bitQuerySum(bitSum, medIdx);
                int cntLt = bitQuery(bitCnt, medIdx - 1);
                long sumLt = bitQuerySum(bitSum, medIdx - 1);
                cost[start] = (long) med * cntLt - sumLt
                            + (windowSum - sumLe) - (long) med * (x - cntLe);
            }
        }
        
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[n + 1][k + 1];
        for (long[] row : dp) Arrays.fill(row, INF);
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = dp[i-1][j];
                if (i >= x && cost[i-x] != Long.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-x][j-1] + cost[i-x]);
                }
            }
        }
        return dp[n][k];
    }
    
    private int lowerBound(int[] arr, int m, int val) {
        int lo = 0, hi = m - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] >= val) hi = mid - 1;
            else lo = mid + 1;
        }
        return lo;
    }
    private void bitAdd(int[] bit, int i, int v) {
        for (; i < bit.length; i += i & -i) bit[i] += v;
    }
    private void bitAddSum(long[] bit, int i, long v) {
        for (; i < bit.length; i += i & -i) bit[i] += v;
    }
    private int bitQuery(int[] bit, int i) {
        int s = 0;
        for (; i > 0; i -= i & -i) s += bit[i];
        return s;
    }
    private long bitQuerySum(long[] bit, int i) {
        long s = 0;
        for (; i > 0; i -= i & -i) s += bit[i];
        return s;
    }
    private int bitKth(int[] bit, int m, int k) {
        int idx = 0;
        int bitMask = Integer.highestOneBit(m);
        for (; bitMask > 0; bitMask >>= 1) {
            int next = idx + bitMask;
            if (next <= m && bit[next] < k) {
                idx = next;
                k -= bit[next];
            }
        }
        return idx + 1;
    }
}