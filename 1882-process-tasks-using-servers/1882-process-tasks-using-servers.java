class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length, m = tasks.length;
        int[] ans = new int[m];

        // Free servers: min-heap by (weight, index)
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        for (int i = 0; i < n; i++) free.offer(new int[]{servers[i], i});

        // Busy servers: min-heap by (freeTime, weight, index)
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? a[0] - b[0]
                    : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);

        long time = 0; // current time

        for (int j = 0; j < m; j++) {
            // Task j becomes available at second j; ensure time >= j
            if (time < j) time = j;

            // If no free server, jump time forward to the earliest busy server's free time
            while (free.isEmpty() && !busy.isEmpty() && busy.peek()[0] > time) {
                time = busy.peek()[0];
            }
            // Release all servers whose freeTime <= time
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] s = busy.poll();
                free.offer(new int[]{s[1], s[2]});
            }

            // Assign the task
            int[] srv = free.poll();
            ans[j] = srv[1];
            busy.offer(new int[]{(int)(time + tasks[j]), srv[0], srv[1]});
        }

        return ans;
    }
}
