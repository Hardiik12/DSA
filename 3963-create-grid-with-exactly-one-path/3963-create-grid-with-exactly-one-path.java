class Solution {
    public String[] createGrid(int m, int n) {
        char[][] grid = new char[m][n];

        // Default: all blocked
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '#';
            }
        }

        // Free entire first row
        for (int j = 0; j < n; j++) grid[0][j] = '.';

        // Free entire last column
        for (int i = 0; i < m; i++) grid[i][n - 1] = '.';

        String[] result = new String[m];
        for (int i = 0; i < m; i++) {
            result[i] = new String(grid[i]);
        }
        return result;
    }
}