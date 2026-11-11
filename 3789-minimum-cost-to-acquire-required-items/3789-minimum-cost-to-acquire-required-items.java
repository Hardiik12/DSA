class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long[] candidates = {
            0,
            Math.min(need1, need2),
            need1,
            need2,
            Math.max(need1, need2)
        };
        long best = Long.MAX_VALUE;
        for (long b : candidates) {
            long rem1 = Math.max(0L, (long) need1 - b);
            long rem2 = Math.max(0L, (long) need2 - b);
            long cost = b * costBoth + rem1 * cost1 + rem2 * cost2;
            best = Math.min(best, cost);
        }
        return best;
    }
}