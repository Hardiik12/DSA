class Solution {
    public int minOperations(String s) {
        int n = s.length();
        if (n <= 1) return 0;

        // Check if the string is already sorted
        boolean sorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }
        if (sorted) return 0;

        // For length 2, the only proper substrings have length 1, so no change is possible
        if (n == 2) return -1;

        // Build the sorted version using counting sort (only lowercase letters)
        int[] freq = new int[26];
        for (int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
        }
        char[] sortedArr = new char[n];
        int idx = 0;
        for (int i = 0; i < 26; i++) {
            while (freq[i]-- > 0) {
                sortedArr[idx++] = (char) ('a' + i);
            }
        }

        // Find the first and last positions where s differs from the sorted string
        int L = 0;
        while (L < n && s.charAt(L) == sortedArr[L]) L++;
        int R = n - 1;
        while (R >= 0 && s.charAt(R) == sortedArr[R]) R--;

        // If the mismatching interval is proper, one sort is enough
        if (L > 0 || R < n - 1) {
            return 1;
        }

        // The whole string mismatches. Check the special case requiring 3 operations:
        // the maximum character appears only at index 0, and the minimum character
        // appears only at index n-1.
        char globalMax = sortedArr[n - 1];
        char globalMin = sortedArr[0];

        boolean maxOnlyAtZero = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == globalMax) {
                maxOnlyAtZero = false;
                break;
            }
        }

        boolean minOnlyAtLast = true;
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == globalMin) {
                minOnlyAtLast = false;
                break;
            }
        }

        if (maxOnlyAtZero && minOnlyAtLast) {
            return 3;
        } else {
            return 2;
        }
    }
}