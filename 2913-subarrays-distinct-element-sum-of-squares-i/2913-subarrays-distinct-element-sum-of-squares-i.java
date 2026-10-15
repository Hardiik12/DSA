import java.util.*;

class Solution {
    public int sumCounts(List<Integer> nums) {
        int n = nums.size();
        int ans = 0;

        for (int i = 0; i < n; i++) {
            boolean[] seen = new boolean[101]; // nums[i] <= 100
            int distinct = 0;

            for (int j = i; j < n; j++) {
                int v = nums.get(j);
                if (!seen[v]) {
                    seen[v] = true;
                    distinct++;
                }
                ans += distinct * distinct;
            }
        }

        return ans;
    }
}
