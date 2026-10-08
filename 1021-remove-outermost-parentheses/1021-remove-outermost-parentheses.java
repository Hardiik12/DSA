class Solution {
    public String removeOuterParentheses(String s) {
        int n = s.length();
        char[] dst = new char[n];
        int idx = 0;
        int depth = 0;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                if (depth != 0) dst[idx++] = c;
                depth++;
            } else {
                if (--depth != 0) dst[idx++] = c;
            }
        }

        return new String(dst, 0, idx);
    }
}