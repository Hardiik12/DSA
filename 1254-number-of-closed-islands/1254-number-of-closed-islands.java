class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // Eliminate border-connected land
        for (int i = 0; i < m; i++) { fill(grid, i, 0); fill(grid, i, n - 1); }
        for (int j = 1; j < n - 1; j++) { fill(grid, 0, j); fill(grid, m - 1, j); }

        int count = 0;
        for (int i = 1; i < m - 1; i++)
            for (int j = 1; j < n - 1; j++)
                if (grid[i][j] == 0) { count++; fill(grid, i, j); }
        return count;
    }

    private void fill(int[][] g, int r, int c) {
        if (g[r][c] != 0) return;
        int m = g.length, n = g[0].length;
        int[] st = new int[m * n];
        int top = 0;
        st[top++] = r * n + c;
        g[r][c] = 1;

        while (top > 0) {
            int p = st[--top];
            int i = p / n, j = p - i * n;

            if (i > 0     && g[i - 1][j] == 0) { g[i - 1][j] = 1; st[top++] = (i - 1) * n + j; }
            if (i < m - 1 && g[i + 1][j] == 0) { g[i + 1][j] = 1; st[top++] = (i + 1) * n + j; }
            if (j > 0     && g[i][j - 1] == 0) { g[i][j - 1] = 1; st[top++] = i * n + (j - 1); }
            if (j < n - 1 && g[i][j + 1] == 0) { g[i][j + 1] = 1; st[top++] = i * n + (j + 1); }
        }
    }
}