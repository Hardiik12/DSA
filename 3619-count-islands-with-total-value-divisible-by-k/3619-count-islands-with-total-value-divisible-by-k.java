class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[] stack = new int[m * n];
        int count = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) continue;

                // BFS/DFS flood fill using flat int stack
                int top = 0;
                long sum = 0;
                stack[top++] = r * n + c;
                sum += grid[r][c];
                grid[r][c] = 0;

                while (top > 0) {
                    int p = stack[--top];
                    int i = p / n, j = p - i * n;

                    if (i > 0     && grid[i - 1][j] != 0) { sum += grid[i - 1][j]; grid[i - 1][j] = 0; stack[top++] = (i - 1) * n + j; }
                    if (i < m - 1 && grid[i + 1][j] != 0) { sum += grid[i + 1][j]; grid[i + 1][j] = 0; stack[top++] = (i + 1) * n + j; }
                    if (j > 0     && grid[i][j - 1] != 0) { sum += grid[i][j - 1]; grid[i][j - 1] = 0; stack[top++] = i * n + (j - 1); }
                    if (j < n - 1 && grid[i][j + 1] != 0) { sum += grid[i][j + 1]; grid[i][j + 1] = 0; stack[top++] = i * n + (j + 1); }
                }

                if (sum % k == 0) count++;
            }
        }

        return count;
    }
}