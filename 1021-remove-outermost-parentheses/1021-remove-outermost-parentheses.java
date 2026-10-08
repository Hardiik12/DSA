class Solution {
    public String removeOuterParentheses(String s) {
        char[] src = s.toCharArray();
        char[] dst = new char[src.length];
        int depth = 0;
        int idx = 0;

        for (int i = 0; i < src.length; i++) {
            char c = src[i];
            if (c == '(') {
                if (depth > 0) dst[idx++] = c;
                depth++;
            } else {
                depth--;
                if (depth > 0) dst[idx++] = c;
            }
        }

        return new String(dst, 0, idx);
    }
}