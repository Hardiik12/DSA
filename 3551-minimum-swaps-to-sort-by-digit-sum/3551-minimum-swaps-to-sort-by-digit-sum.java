import java.util.*;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;

        // Build the target sorted order
        Integer[] sorted = new Integer[n];
        for (int i = 0; i < n; i++) sorted[i] = nums[i];

        Arrays.sort(sorted, (a, b) -> {
            int sa = digitSum(a);
            int sb = digitSum(b);
            if (sa != sb) return Integer.compare(sa, sb);
            return Integer.compare(a, b);
        });

        // Map each value to its target position
        Map<Integer, Integer> pos = new HashMap<>(n * 2);
        for (int i = 0; i < n; i++) {
            pos.put(sorted[i], i);
        }

        // Count cycles in the permutation
        boolean[] visited = new boolean[n];
        int cycles = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                cycles++;
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    j = pos.get(nums[j]);
                }
            }
        }

        return n - cycles;
    }

    private int digitSum(int x) {
        int sum = 0;
        while (x > 0) {
            sum += x % 10;
            x /= 10;
        }
        return sum;
    }
}