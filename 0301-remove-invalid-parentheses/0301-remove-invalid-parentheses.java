class Solution {
    public List<String> removeInvalidParentheses(String s) {
        // Count misplaced parentheses
        int leftRem = 0, rightRem = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftRem++;
            } else if (c == ')') {
                if (leftRem > 0) leftRem--;
                else rightRem++;
            }
        }
        // Now leftRem is number of unmatched '(' that need removal? Wait, let's re-evaluate.
        // Standard counting:
        // Scan left to right: count open. If ')', if open>0 open--, else invalidRight++.
        // Scan right to left: count close. If '(', if close>0 close--, else invalidLeft++.
        // Let's do that properly.
        int invalidLeft = 0, invalidRight = 0;
        int open = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                open++;
            } else if (c == ')') {
                if (open > 0) open--;
                else invalidRight++;
            }
        }
        int close = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                close++;
            } else if (c == '(') {
                if (close > 0) close--;
                else invalidLeft++;
            }
        }
        // invalidLeft = number of '(' to remove
        // invalidRight = number of ')' to remove

        Set<String> result = new HashSet<>();
        dfs(s, 0, 0, invalidLeft, invalidRight, new StringBuilder(), result);
        return new ArrayList<>(result);
    }

    private void dfs(String s, int index, int open, int leftRem, int rightRem, StringBuilder sb, Set<String> result) {
        if (index == s.length()) {
            if (open == 0 && leftRem == 0 && rightRem == 0) {
                result.add(sb.toString());
            }
            return;
        }

        char c = s.charAt(index);

        if (c == '(') {
            // Option 1: remove it if we still need to remove left parentheses
            if (leftRem > 0) {
                dfs(s, index + 1, open, leftRem - 1, rightRem, sb, result);
            }
            // Option 2: keep it
            sb.append(c);
            dfs(s, index + 1, open + 1, leftRem, rightRem, sb, result);
            sb.setLength(sb.length() - 1);
        } else if (c == ')') {
            // Option 1: remove it if we still need to remove right parentheses
            if (rightRem > 0) {
                dfs(s, index + 1, open, leftRem, rightRem - 1, sb, result);
            }
            // Option 2: keep it, but only if it doesn't make balance negative
            if (open > 0) {
                sb.append(c);
                dfs(s, index + 1, open - 1, leftRem, rightRem, sb, result);
                sb.setLength(sb.length() - 1);
            }
        } else {
            // Letter: always keep
            sb.append(c);
            dfs(s, index + 1, open, leftRem, rightRem, sb, result);
            sb.setLength(sb.length() - 1);
        }
    }
}