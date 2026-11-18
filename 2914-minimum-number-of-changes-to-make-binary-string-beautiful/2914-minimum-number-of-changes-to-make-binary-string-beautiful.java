class Solution {
    public int minChanges(String s) {
        char[] chars = s.toCharArray();
        int changes = 0;
        for (int i = 0; i < chars.length; i += 2) {
            if (chars[i] != chars[i + 1]) {
                changes++;
            }
        }
        return changes;
    }
}