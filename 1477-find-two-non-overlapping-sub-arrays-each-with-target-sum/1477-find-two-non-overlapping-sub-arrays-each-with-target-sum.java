class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int INF = Integer.MAX_VALUE;
        
        // 1. Build prefix array
        int left = 0, sum = 0, minLen = INF;
        for (int right = 0; right < n; right++) {
            sum += arr[right];
            while (sum > target && left <= right) {
                sum -= arr[left++];
            }
            if (sum == target) {
                minLen = Math.min(minLen, right - left + 1);
            }
            prefix[right] = minLen;
        }
        
        // 2. Build suffix array
        int right = n - 1;
        sum = 0;
        minLen = INF;
        for (int l = n - 1; l >= 0; l--) {
            sum += arr[l];
            while (sum > target && right >= l) {
                sum -= arr[right--];
            }
            if (sum == target) {
                minLen = Math.min(minLen, right - l + 1);
            }
            suffix[l] = minLen;
        }
        
        // 3. Find the minimum sum of lengths
        int ans = INF;
        for (int i = 0; i < n - 1; i++) {
            if (prefix[i] != INF && suffix[i + 1] != INF) {
                ans = Math.min(ans, prefix[i] + suffix[i + 1]);
            }
        }
        
        return ans == INF ? -1 : ans;
    }
}