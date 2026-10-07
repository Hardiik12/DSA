import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;

        // Count minimum removals needed
        int leftRem = 0, rightRem = 0;
        int open = 0;
        for (char c : arr) {
            if (c == '(') open++;
            else if (c == ')') {
                if (open > 0) open--;
                else rightRem++;
            }
        }
        int close = 0;
        for (int i = n - 1; i >= 0; i--) {
            char c = arr[i];
            if (c == ')') close++;
            else if (c == '(') {
                if (close > 0) close--;
                else leftRem++;
            }
        }

        List<String> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        char[] buf = new char[n];

        dfs(arr, 0, 0, leftRem, rightRem, buf, 0, result, seen);
        return result;
    }

    private void dfs(char[] arr, int idx, int open, int leftRem, int rightRem,
                     char[] buf, int len, List<String> result, Set<String> seen) {
        // Pruning: not enough remaining characters to close all open parentheses
        int remaining = arr.length - idx;
        if (open > remaining) return;
        // Pruning: cannot remove more characters than remaining
        if (leftRem + rightRem > remaining) return;

        if (idx == arr.length) {
            if (open == 0 && leftRem == 0 && rightRem == 0) {
                String str = new String(buf, 0, len);
                if (seen.add(str)) result.add(str);
            }
            return;
        }

        char c = arr[idx];

        if (c == '(') {
            // Remove this '('
            if (leftRem > 0) {
                dfs(arr, idx + 1, open, leftRem - 1, rightRem, buf, len, result, seen);
            }
            // Keep this '('
            buf[len] = c;
            dfs(arr, idx + 1, open + 1, leftRem, rightRem, buf, len + 1, result, seen);
        } else if (c == ')') {
            // Remove this ')'
            if (rightRem > 0) {
                dfs(arr, idx + 1, open, leftRem, rightRem - 1, buf, len, result, seen);
            }
            // Keep this ')' only if it can be matched
            if (open > 0) {
                buf[len] = c;
                dfs(arr, idx + 1, open - 1, leftRem, rightRem, buf, len + 1, result, seen);
            }
        } else {
            // Always keep letters
            buf[len] = c;
            dfs(arr, idx + 1, open, leftRem, rightRem, buf, len + 1, result, seen);
        }
    }
}