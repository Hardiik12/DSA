class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        char[] str = s.toCharArray();
        
        // 1. Build CSR for original tree
        int[] head = new int[n];
        int[] next = new int[n];
        java.util.Arrays.fill(head, -1);
        for (int i = 1; i < n; i++) {
            int p = parent[i];
            next[i] = head[p];
            head[p] = i;
        }
        
        int[] newParent = new int[n];
        newParent[0] = -1;
        
        // 2. Iterative DFS to find newParent and record DFS order
        int[] order = new int[n];
        // Allocate stack size 2n to safely hold nodes and exit markers
        int[] stack = new int[2 * n]; 
        int top = 0;
        
        // Stacks for each character 'a' - 'z'
        int[][] charStacks = new int[26][n];
        int[] charStackSize = new int[26];
        
        int idx = 0;
        stack[top++] = 0; // push root
        
        while (top > 0) {
            int u = stack[--top];
            
            if (u >= 0) {
                // Entering node u
                order[idx++] = u;
                int c = str[u] - 'a';
                
                if (charStackSize[c] > 0) {
                    newParent[u] = charStacks[c][charStackSize[c] - 1];
                } else {
                    newParent[u] = parent[u];
                }
                
                charStacks[c][charStackSize[c]++] = u;
                
                // Push exit marker (using bitwise NOT to make it negative)
                stack[top++] = ~u; 
                
                // Push children
                for (int v = head[u]; v != -1; v = next[v]) {
                    stack[top++] = v;
                }
            } else {
                // Exiting node u
                int node = ~u;
                int c = str[node] - 'a';
                charStackSize[c]--;
            }
        }
        
        // 3. Compute subtree sizes
        int[] ans = new int[n];
        java.util.Arrays.fill(ans, 1); // Each node initially counts itself
        
        // Process nodes in reverse DFS order
        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            ans[newParent[u]] += ans[u];
        }
        
        return ans;
    }
}