class Solution {
    public String reverseParentheses(String s) {
      int n = s.length();
        int[] pair = new int[n];
        int[] stack = new int[n];
        int top = -1;

        // Match each '(' with its corresponding ')'
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack[++top] = i;
            } else if (c == ')') {
                int j = stack[top--];
                pair[i] = j;
                pair[j] = i;
            }
        }

        StringBuilder sb = new StringBuilder(n);
        int i = 0;
        int dir = 1; // 1 = forward, -1 = backward

        while (i >= 0 && i < n) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                i = pair[i];   // jump to matching bracket
                dir = -dir;    // reverse direction
            } else {
                sb.append(c);
            }
            i += dir;
        }

        return sb.toString();
    }
}
