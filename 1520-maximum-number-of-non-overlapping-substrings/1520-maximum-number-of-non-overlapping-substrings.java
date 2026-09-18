class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, n);
        Arrays.fill(last, -1);
        
        // Record first and last occurrence of each character
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == n) first[c] = i;
            last[c] = i;
        }
        
        List<int[]> intervals = new ArrayList<>();
        
        // Build all valid intervals
        for (int c = 0; c < 26; c++) {
            if (first[c] == n) continue;
            int start = first[c];
            int end = last[c];
            
            // Expand end to include all characters found in the current range
            for (int j = start; j <= end; j++) {
                int d = s.charAt(j) - 'a';
                if (last[d] > end) end = last[d];
            }
            
            // Check if this interval is valid (contains all occurrences of its characters)
            boolean valid = true;
            for (int k = start; k <= end; k++) {
                if (first[s.charAt(k) - 'a'] < start) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                intervals.add(new int[]{start, end});
            }
        }
        
        // Sort intervals by their end index
        intervals.sort((a, b) -> a[1] - b[1]);
        
        int m = intervals.size();
        int[] dpCount = new int[m + 1];
        int[] dpLen = new int[m + 1];
        int[] parent = new int[m + 1];
        boolean[] took = new boolean[m + 1];
        
        // DP to find maximum number of non-overlapping intervals with minimum total length
        for (int i = 1; i <= m; i++) {
            int[] interval = intervals.get(i - 1);
            int start = interval[0];
            int end = interval[1];
            int len = end - start + 1;
            
            // Option 1: Skip current interval
            dpCount[i] = dpCount[i - 1];
            dpLen[i] = dpLen[i - 1];
            parent[i] = i - 1;
            took[i] = false;
            
            // Option 2: Take current interval
            int prev = i - 1;
            while (prev > 0 && intervals.get(prev - 1)[1] >= start) {
                prev--;
            }
            int takeCount = dpCount[prev] + 1;
            int takeLen = dpLen[prev] + len;
            
            if (takeCount > dpCount[i] || (takeCount == dpCount[i] && takeLen < dpLen[i])) {
                dpCount[i] = takeCount;
                dpLen[i] = takeLen;
                parent[i] = prev;
                took[i] = true;
            }
        }
        
        // Reconstruct the selected substrings
        List<String> result = new ArrayList<>();
        int curr = m;
        while (curr > 0) {
            if (took[curr]) {
                int[] interval = intervals.get(curr - 1);
                result.add(s.substring(interval[0], interval[1] + 1));
                curr = parent[curr];
            } else {
                curr--;
            }
        }
        
        return result;
    }
}