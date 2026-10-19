import java.util.*;

class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        int less = 0;
        int equal = 0;

        for (int num : nums) {
            if (num < target) {
                less++;
            } else if (num == target) {
                equal++;
            }
        }

        List<Integer> result = new ArrayList<>(equal);
        for (int i = less; i < less + equal; i++) {
            result.add(i);
        }

        return result;
    }
}