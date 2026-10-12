import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        final int MOD = 1_000_000_007;
        int[] bitCount = new int[31];

        // Count how many numbers have each bit set
        for (int num : nums) {
            for (int b = 0; b < 31; b++) {
                if ((num & (1 << b)) != 0) {
                    bitCount[b]++;
                }
            }
        }

        // Build the k largest values by concentrating bits
        long[] vals = new long[k];
        for (int b = 30; b >= 0; b--) {
            int c = bitCount[b];
            if (c == 0) continue;
            long add = 1L << b;
            int limit = Math.min(c, k);
            for (int i = 0; i < limit; i++) {
                vals[i] += add;
            }
        }

        // Sum of squares modulo 1e9+7
        long ans = 0;
        for (long v : vals) {
            long modV = v % MOD;
            ans = (ans + modV * modV) % MOD;
        }
        return (int) ans;
    }
}