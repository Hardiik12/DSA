class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0; // minimum possible open parentheses count
        int maxOpen = 0; // maximum possible open parentheses count

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen--;
                maxOpen--;
            } else { // '*'
                minOpen--; // treat as ')'
                maxOpen++; // treat as '('
            }

            // Cannot have negative open parentheses
            if (maxOpen < 0) return false;

            if (minOpen < 0) minOpen = 0;
        }

        return minOpen == 0;
    }
}