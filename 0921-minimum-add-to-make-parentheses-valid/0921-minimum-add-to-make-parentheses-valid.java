class Solution {
    public int minAddToMakeValid(String s) {
        int openNeeded = 0;   // unmatched ')' that need a '(' before them
        int balance = 0;      // unmatched '('

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                balance++;
            } else {
                if (balance > 0) {
                    balance--;
                } else {
                    openNeeded++;
                }
            }
        }

        return openNeeded + balance;
    }
}