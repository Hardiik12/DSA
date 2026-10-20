import java.util.Arrays;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // If already sorted, all divisors work
        boolean alreadySorted = true;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) { alreadySorted = false; break; }
        }
        if (alreadySorted) {
            int sum = 0;
            for (int k = 1; k * k <= n; k++) {
                if (n % k == 0) {
                    sum += k;
                    if (k != n / k) sum += n / k;
                }
            }
            return sum;
        }

        int sum = 0;
        for (int k = 2; k <= n; k++) {
            if (n % k != 0) continue;
            if (canSort(nums, sorted, k)) sum += k;
        }
        return sum;
    }

    private boolean canSort(int[] nums, int[] sorted, int k) {
        int n = nums.length;
        for (int start = 0; start < n; start += k) {
            if (!blockMatches(nums, start, sorted, start, k)) return false;
        }
        return true;
    }

    private boolean blockMatches(int[] nums, int a, int[] sorted, int b, int k) {
        int descents = 0;
        int pivot = a;

        for (int i = a; i < a + k - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                if (++descents > 1) return false;
                pivot = i + 1;
            }
        }

        if (descents == 0) {
            for (int i = 0; i < k; i++) {
                if (nums[a + i] != sorted[b + i]) return false;
            }
            return true;
        }

        if (nums[a + k - 1] > nums[a]) return false;

        int pos = pivot;
        for (int i = 0; i < k; i++) {
            if (nums[pos] != sorted[b + i]) return false;
            if (++pos == a + k) pos = a;
        }
        return true;
    }
}