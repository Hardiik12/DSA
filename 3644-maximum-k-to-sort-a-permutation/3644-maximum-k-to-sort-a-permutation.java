class Solution {
    public int sortPermutation(int[] nums) {
        int n = nums.length;

        // Check if already sorted
        boolean sorted = true;
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                sorted = false;
                break;
            }
        }
        if (sorted) return 0;

        // Compute bitwise AND of all misplaced values
        int M = -1; // all bits set
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                if (M == -1) M = nums[i];
                else M &= nums[i];
                if (M == 0) return 0; // early exit
            }
        }
        return M;
    }
}