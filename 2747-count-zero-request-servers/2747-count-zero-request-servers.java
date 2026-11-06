class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> a[1] - b[1]);
        int m = logs.length;

        int q = queries.length;
        int[][] qs = new int[q][2];
        for (int i = 0; i < q; i++) { qs[i][0] = queries[i]; qs[i][1] = i; }
        Arrays.sort(qs, (a, b) -> a[0] - b[0]);

        int[] ans = new int[q];
        int[] freq = new int[n + 1];
        int distinct = 0;
        int left = 0, right = 0;

        for (int[] qq : qs) {
            int qt = qq[0], qi = qq[1];
            int low = qt - x;

            while (right < m && logs[right][1] <= qt) {
                int sid = logs[right][0];
                if (freq[sid] == 0) distinct++;
                freq[sid]++;
                right++;
            }
            while (left < right && logs[left][1] < low) {
                int sid = logs[left][0];
                freq[sid]--;
                if (freq[sid] == 0) distinct--;
                left++;
            }

            ans[qi] = n - distinct;
        }

        return ans;
    }
}