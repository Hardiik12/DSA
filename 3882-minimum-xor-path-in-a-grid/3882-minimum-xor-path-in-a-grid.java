class Solution {
    private int ans = Integer.MAX_VALUE;
    private int n;
    private boolean[][] visited;
    private int[][] grid;

    public int minCost(int[][] grid) {
        int m = grid.length;
        this.n = grid[0].length;
        this.grid = grid;
        
        // visited[i][j][xor] flattened or as a 3D boolean array. 
        // Using boolean[][] of size [m*n][1024] for fast allocation and cache locality.
        this.visited = new boolean[m * n][1024];
        
        dfs(0, 0, 0, m);
        return ans;
    }

    private void dfs(int r, int c, int xor, int m) {
        xor ^= grid[r][c];
        
        // Absolute minimum found, exit immediately
        if (ans == 0) return; 
        
        // Prune if this state has already been explored
        int id = r * n + c;
        if (visited[id][xor]) return;
        visited[id][xor] = true;
        
        // Update answer if we reached the bottom-right
        if (r == m - 1 && c == n - 1) {
            if (xor < ans) ans = xor;
            return;
        }
        
        // Optional heuristic: explore the path with smaller current value first
        if (r + 1 < m && c + 1 < n) {
            if (grid[r + 1][c] < grid[r][c + 1]) {
                dfs(r + 1, c, xor, m);
                dfs(r, c + 1, xor, m);
            } else {
                dfs(r, c + 1, xor, m);
                dfs(r + 1, c, xor, m);
            }
        } else if (r + 1 < m) {
            dfs(r + 1, c, xor, m);
        } else if (c + 1 < n) {
            dfs(r, c + 1, xor, m);
        }
    }
}