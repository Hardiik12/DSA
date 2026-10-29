class Solution {
    private int[] head2;   // adjacency via CSR
    private int[] to, nxt;
    private int[] parent, depth, size, heavy, headHld, pos, nodeAtPos;
    private int[] segTree;
    private char[] chars;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        // Build CSR adjacency
        int m = 2 * (n - 1);
        to = new int[m];
        nxt = new int[m];
        head2 = new int[n];
        Arrays.fill(head2, -1);
        int idx = 0;
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            to[idx] = v; nxt[idx] = head2[u]; head2[u] = idx++;
            to[idx] = u; nxt[idx] = head2[v]; head2[v] = idx++;
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        headHld = new int[n];
        pos = new int[n];
        nodeAtPos = new int[n];
        chars = s.toCharArray();
        Arrays.fill(heavy, -1);

        // Iterative DFS for parent/depth/order (avoid recursion depth issues)
        int[] order = new int[n];
        int ordLen = 0;
        int[] stack = new int[n];
        int sp = 0;
        parent[0] = -1;
        depth[0] = 0;
        stack[sp++] = 0;
        while (sp > 0) {
            int u = stack[--sp];
            order[ordLen++] = u;
            for (int e = head2[u]; e != -1; e = nxt[e]) {
                int v = to[e];
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[sp++] = v;
            }
        }

        // Compute sizes and heavy child in reverse order
        Arrays.fill(size, 1);
        for (int i = ordLen - 1; i >= 0; i--) {
            int u = order[i];
            int bestSize = 0;
            for (int e = head2[u]; e != -1; e = nxt[e]) {
                int v = to[e];
                if (v == parent[u]) continue;
                size[u] += size[v];
                if (size[v] > bestSize) {
                    bestSize = size[v];
                    heavy[u] = v;
                }
            }
        }

        // Iterative HLD decompose: assign pos, head
        // We'll process chains: push (node, chainHead) onto stack
        int cur = 0;
        int[] stackN = new int[n];
        int[] stackH = new int[n];
        sp = 0;
        stackN[sp] = 0; stackH[sp] = 0; sp++;
        while (sp > 0) {
            sp--;
            int u = stackN[sp];
            int h = stackH[sp];
            // Walk down the heavy path
            while (u != -1) {
                headHld[u] = h;
                pos[u] = cur;
                nodeAtPos[cur] = u;
                cur++;
                // Push light children as new chain heads
                for (int e = head2[u]; e != -1; e = nxt[e]) {
                    int v = to[e];
                    if (v == parent[u] || v == heavy[u]) continue;
                    stackN[sp] = v; stackH[sp] = v; sp++;
                }
                u = heavy[u];
            }
        }

        // Build segment tree
        segTree = new int[4 * n];
        buildSegTree(0, 0, n - 1);

        List<Boolean> answer = new ArrayList<>();
        for (String query : queries) {
            // fast parse
            if (query.charAt(0) == 'u') {
                // "update u c"
                int i = 7;
                int u = 0;
                while (i < query.length() && query.charAt(i) != ' ') {
                    u = u * 10 + (query.charAt(i) - '0');
                    i++;
                }
                char c = query.charAt(i + 1);
                updateNode(u, c);
            } else {
                // "query u v"
                int i = 6;
                int u = 0;
                while (query.charAt(i) != ' ') {
                    u = u * 10 + (query.charAt(i) - '0');
                    i++;
                }
                i++;
                int v = 0;
                while (i < query.length()) {
                    v = v * 10 + (query.charAt(i) - '0');
                    i++;
                }
                int mask = queryPath(u, v);
                answer.add((mask & (mask - 1)) == 0);
            }
        }
        return answer;
    }

    private void buildSegTree(int treeIdx, int left, int right) {
        if (left == right) {
            segTree[treeIdx] = 1 << (chars[nodeAtPos[left]] - 'a');
            return;
        }
        int mid = (left + right) >>> 1;
        buildSegTree(2 * treeIdx + 1, left, mid);
        buildSegTree(2 * treeIdx + 2, mid + 1, right);
        segTree[treeIdx] = segTree[2 * treeIdx + 1] ^ segTree[2 * treeIdx + 2];
    }

    private void updateNode(int node, char c) {
        chars[node] = c;
        updateSegTree(0, 0, pos.length - 1, pos[node], 1 << (c - 'a'));
    }

    private void updateSegTree(int treeIdx, int left, int right, int idx, int val) {
        if (left == right) {
            segTree[treeIdx] = val;
            return;
        }
        int mid = (left + right) >>> 1;
        if (idx <= mid) updateSegTree(2 * treeIdx + 1, left, mid, idx, val);
        else updateSegTree(2 * treeIdx + 2, mid + 1, right, idx, val);
        segTree[treeIdx] = segTree[2 * treeIdx + 1] ^ segTree[2 * treeIdx + 2];
    }

    private int queryPath(int u, int v) {
        int result = 0;
        while (headHld[u] != headHld[v]) {
            if (depth[headHld[u]] < depth[headHld[v]]) {
                int t = u; u = v; v = t;
            }
            result ^= querySegTree(0, 0, pos.length - 1, pos[headHld[u]], pos[u]);
            u = parent[headHld[u]];
        }
        if (depth[u] > depth[v]) { int t = u; u = v; v = t; }
        result ^= querySegTree(0, 0, pos.length - 1, pos[u], pos[v]);
        return result;
    }

    private int querySegTree(int treeIdx, int left, int right, int qL, int qR) {
        if (qL > right || qR < left) return 0;
        if (qL <= left && right <= qR) return segTree[treeIdx];
        int mid = (left + right) >>> 1;
        return querySegTree(2 * treeIdx + 1, left, mid, qL, qR)
             ^ querySegTree(2 * treeIdx + 2, mid + 1, right, qL, qR);
    }
}