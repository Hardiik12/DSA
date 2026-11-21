import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }
        
        int[] newParent = new int[n];
        newParent[0] = -1; // root has no parent
        
        // Stacks for each character 'a' to 'z'
        List<Integer>[] charStacks = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            charStacks[i] = new ArrayList<>();
        }
        
        // Iterative DFS to avoid StackOverflow
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0}); // {node, state} state 0 = enter, 1 = exit
        
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            int u = top[0];
            int state = top[1];
            
            if (state == 0) {
                // Entering node u
                int c = s.charAt(u) - 'a';
                if (!charStacks[c].isEmpty()) {
                    newParent[u] = charStacks[c].get(charStacks[c].size() - 1);
                } else {
                    newParent[u] = parent[u];
                }
                
                charStacks[c].add(u);
                stack.push(new int[]{u, 1}); // Exit state
                
                // Push children in reverse order to process left-to-right (optional)
                for (int i = children[u].size() - 1; i >= 0; i--) {
                    stack.push(new int[]{children[u].get(i), 0});
                }
            } else {
                // Exiting node u
                int c = s.charAt(u) - 'a';
                charStacks[c].remove(charStacks[c].size() - 1);
            }
        }
        
        // Build the new tree
        List<Integer>[] newChildren = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newChildren[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            newChildren[newParent[i]].add(i);
        }
        
        // Calculate subtree sizes using post-order DFS iteratively
        int[] subtreeSize = new int[n];
        Deque<int[]> stack2 = new ArrayDeque<>();
        stack2.push(new int[]{0, 0}); // {node, state}
        
        while (!stack2.isEmpty()) {
            int[] top = stack2.pop();
            int u = top[0];
            int state = top[1];
            
            if (state == 0) {
                stack2.push(new int[]{u, 1});
                for (int v : newChildren[u]) {
                    stack2.push(new int[]{v, 0});
                }
            } else {
                int size = 1;
                for (int v : newChildren[u]) {
                    size += subtreeSize[v];
                }
                subtreeSize[u] = size;
            }
        }
        
        return subtreeSize;
    }
}