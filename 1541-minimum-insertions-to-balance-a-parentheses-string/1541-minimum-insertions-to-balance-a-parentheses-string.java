class Solution {
    public int minInsertions(String s) {
        int n = s.length();
        int cost = 0;
        int open = 0;
        int i = 0;

        while (i < n) {
            if (s.charAt(i) == '(') {
                open++;
                i++;
            } else { // s.charAt(i) == ')'
                if (i + 1 < n && s.charAt(i + 1) == ')') {
                    // It's a '))' pair
                    if (open > 0) {
                        open--;
                    } else {
                        cost++; // insert '(' before this '))'
                    }
                    i += 2;
                } else {
                    // Single ')'
                    cost++; // insert ')' to make it '))'
                    if (open > 0) {
                        open--;
                    } else {
                        cost++; // insert '(' before this '))'
                    }
                    i++;
                }
            }
        }

        cost += 2 * open;
        return cost;
    }
}