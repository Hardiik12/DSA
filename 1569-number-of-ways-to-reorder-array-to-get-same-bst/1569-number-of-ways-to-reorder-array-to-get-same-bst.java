class Solution {
    private static final int MOD = 1_000_000_007;
    private long[] fact;
    private long[] invFact;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // Precompute factorials and inverse factorials for combinations
        fact = new long[n + 1];
        invFact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }
        invFact[n] = modPow(fact[n], MOD - 2);
        for (int i = n - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }

        long ways = countWays(nums);

        // Subtract the original ordering
        return (int) ((ways - 1 + MOD) % MOD);
    }

    private long countWays(int[] arr) {
        if (arr.length <= 1) return 1;

        int root = arr[0];
        int leftCount = 0;

        // Count elements going to the left subtree
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < root) leftCount++;
        }

        int rightCount = arr.length - 1 - leftCount;
        int[] left = new int[leftCount];
        int[] right = new int[rightCount];

        int li = 0, ri = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < root) {
                left[li++] = arr[i];
            } else {
                right[ri++] = arr[i];
            }
        }

        long ways = comb(arr.length - 1, leftCount);
        ways = ways * countWays(left) % MOD;
        ways = ways * countWays(right) % MOD;

        return ways;
    }

    private long comb(int n, int k) {
        if (k < 0 || k > n) return 0;
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    private long modPow(long base, long exp) {
        long result = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return result;
    }
}