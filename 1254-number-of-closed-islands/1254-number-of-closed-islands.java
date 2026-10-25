class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        for (int i = 0; i < m; i++) { bfs(grid, i, 0); bfs(grid, i, n - 1); }
        for (int j = 0; j < n; j++) { bfs(grid, 0, j); bfs(grid, m - 1, j); }

        int count = 0;
        for (int i = 1; i < m - 1; i++)
            for (int j = 1; j < n - 1; j++)
                if (grid[i][j] == 0) { count++; bfs(grid, i, j); }
        return count;
    }

    private void bfs(int[][] grid, int r, int c) {
        int m = grid.length, n = grid[0].length;
        if (grid[r][c] != 0) return;

        int[] stack = new int[m * n * 2];
        int top = 0;
        stack[top++] = r;
        stack[top++] = c;
        grid[r][c] = 1;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (top > 0) {
            int j = stack[--top];
            int i = stack[--top];
            for (int d = 0; d < 4; d++) {
                int ni = i + dr[d], nj = j + dc[d];
                if (ni >= 0 && ni < m && nj >= 0 && nj < n && grid[ni][nj] == 0) {
                    grid[ni][nj] = 1;
                    stack[top++] = ni;
                    stack[top++] = nj;
                }
            }
        }
    }
}