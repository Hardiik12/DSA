// Brute force, O(m*n) — also passes given small constraints
class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int count = 0;
        for (int a : arr1) {
            boolean ok = true;
            for (int b : arr2) {
                if (Math.abs(a - b) <= d) { ok = false; break; }
            }
            if (ok) count++;
        }
        return count;
    }
}