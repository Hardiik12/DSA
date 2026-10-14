class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num || sum < 1) return "";

        int nines = sum / 9;
        int remainder = sum % 9;

        StringBuilder sb = new StringBuilder(num);
        for (int i = 0; i < nines; i++) sb.append('9');
        if (remainder > 0) sb.append((char) ('0' + remainder));
        int zeros = num - nines - (remainder > 0 ? 1 : 0);
        for (int i = 0; i < zeros; i++) sb.append('0');

        return sb.toString();
    }
}