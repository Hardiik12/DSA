class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int[] count = new int[k];
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) available.add(i);

        // busy: (freeTime, serverId)
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        int n = arrival.length;
        for (int i = 0; i < n; i++) {
            // Release servers that finished by arrival[i]
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                available.add(busy.poll()[1]);
            }

            if (available.isEmpty()) continue; // all busy → drop

            int target = i % k;
            Integer chosen = available.ceiling(target); // first free >= target
            if (chosen == null) chosen = available.first(); // wrap around

            available.remove(chosen);
            count[chosen]++;
            busy.offer(new int[]{arrival[i] + load[i], chosen});
        }

        int max = 0;
        for (int c : count) max = Math.max(max, c);

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) if (count[i] == max) ans.add(i);
        return ans;
    }
}