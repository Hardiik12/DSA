class Solution {
    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;

        // For k == 1, every product has remainder 0.
        if (k == 1) {
            int[] res = new int[queries.length];
            for (int i = 0; i < queries.length; i++) {
                int start = queries[i][2];
                res[i] = n - start;
            }
            return res;
        }

        int size = 1;
        while (size < n) size <<= 1;

        int[][] cnt = new int[2 * size][k];
        int[] prod = new int[2 * size];
        Arrays.fill(prod, 1); // identity for empty segments

        for (int i = 0; i < n; i++) {
            int v = nums[i] % k;
            cnt[size + i][v] = 1;
            prod[size + i] = v;
        }

        for (int i = size - 1; i >= 1; i--) {
            pull(i, k, cnt, prod);
        }

        int[] res = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int idx = queries[qi][0];
            int val = queries[qi][1];
            int start = queries[qi][2];
            int x = queries[qi][3];

            // Point update
            int pos = size + idx;
            Arrays.fill(cnt[pos], 0);
            int v = val % k;
            cnt[pos][v] = 1;
            prod[pos] = v;

            pos >>= 1;
            while (pos >= 1) {
                pull(pos, k, cnt, prod);
                pos >>= 1;
            }

            // Query range [start, n - 1]
            int l = start + size;
            int r = n - 1 + size;

            int[] leftCnt = new int[k];
            int[] rightCnt = new int[k];
            int leftProd = 1;

            while (l <= r) {
                if ((l & 1) == 1) {
                    int oldProd = leftProd;
                    for (int p = 0; p < k; p++) {
                        int c = cnt[l][p];
                        if (c != 0) {
                            leftCnt[(oldProd * p) % k] += c;
                        }
                    }
                    leftProd = (oldProd * prod[l]) % k;
                    l++;
                }

                if ((r & 1) == 0) {
                    int pr = prod[r];
                    int[] tmp = new int[k];

                    for (int p = 0; p < k; p++) {
                        int c = rightCnt[p];
                        if (c != 0) {
                            tmp[(pr * p) % k] += c;
                        }
                    }

                    for (int p = 0; p < k; p++) {
                        rightCnt[p] = cnt[r][p] + tmp[p];
                    }

                    r--;
                }

                l >>= 1;
                r >>= 1;
            }

            int ans = leftCnt[x];
            int lp = leftProd;

            for (int p = 0; p < k; p++) {
                if (rightCnt[p] != 0 && (lp * p) % k == x) {
                    ans += rightCnt[p];
                }
            }

            res[qi] = ans;
        }

        return res;
    }

    private void pull(int node, int k, int[][] cnt, int[] prod) {
        int left = node << 1;
        int right = left | 1;

        int[] cNode = cnt[node];
        int[] cL = cnt[left];
        int[] cR = cnt[right];

        Arrays.fill(cNode, 0);

        int prodL = prod[left];

        for (int p = 0; p < k; p++) {
            cNode[p] = cL[p];
        }

        for (int p = 0; p < k; p++) {
            int c = cR[p];
            if (c != 0) {
                cNode[(prodL * p) % k] += c;
            }
        }

        prod[node] = (prodL * prod[right]) % k;
    }
}