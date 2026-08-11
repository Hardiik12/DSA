# High Effort Vs Low Effort Tasks

Given two integer arrays `h[]` and `l[]`, where `h[i]` and `l[i]` denote the number of tasks that can be completed on the **i-th** day by performing a high-effort task and a low-effort task, respectively.

For each day, you may choose exactly one of the following options:
- Perform no task (0 tasks completed).
- Perform a low-effort task (`l[i]` tasks completed).
- Perform a high-effort task (`h[i]` tasks completed), which can **only** be performed on the first day (day 0) or if **no task was performed on the previous day** (day `i-1`).

Return the maximum total number of tasks that can be completed over all days.

---

## Examples

### Example 1:
```text
Input: h[] = [2, 8, 1], l[] = [1, 2, 1]
Output: 9
Explanation: Pick the high-effort task on day 1 (8 tasks) and the low-effort task on day 2 (1 task). 
Total = 8 + 1 = 9.
```

### Example 2:
```text
Input: h[] = [3, 6, 8, 7, 6], l[] = [1, 5, 4, 5, 3]
Output: 20
Explanation: Pick the high-effort task on day 0 (3 tasks) and low-effort tasks on all remaining days (5 + 4 + 5 + 3 = 17 tasks). 
Total = 3 + 17 = 20.
```

---

## Constraints

- $1 \le h.size() \le 10^5$
- $0 \le h[i] \le 10^3$
- $1 \le l.size() \le 10^5$
- $0 \le l[i] \le 10^3$
- $l.size() = h.size()$

---

## Solution & Intuition

We can solve this problem using **Dynamic Programming**.

### Recurrence Relation

For day `i`:
- **Option 1: Perform Low-Effort Task**
  $$\text{tasks}_{\text{low}}(i) = \text{dp}[i-1] + l[i]$$
- **Option 2: Perform High-Effort Task**
  (Requires day $i-1$ to be a rest/no-task day)
  $$\text{tasks}_{\text{high}}(i) = \text{dp}[i-2] + h[i] \quad (\text{or } h[0] \text{ if } i = 0)$$
- **Option 3: Perform No Task**
  $$\text{tasks}_{\text{none}}(i) = \text{dp}[i-1]$$

$$\text{dp}[i] = \max\left(\text{dp}[i-1], \; \text{dp}[i-1] + l[i], \; \text{dp}[i-2] + h[i]\right)$$

### Space Optimization

Since $\text{dp}[i]$ only depends on $\text{dp}[i-1]$ (`prev1`) and $\text{dp}[i-2]$ (`prev2`), we optimize auxiliary space from $\mathcal{O}(N)$ to $\mathcal{O}(1)$.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | Single pass from day `0` to `n-1`. |
| **Auxiliary Space** | $\mathcal{O}(1)$ | Uses only two tracking variables (`prev1`, `prev2`). |

---

## Java Implementation

```java
public class Solution {
    public static int maxTasks(int[] h, int[] l) {
        if (h == null || l == null) return 0;
        int n = h.length;
        if (n == 0 || l.length != n) return 0;

        int prev2 = 0; // dp[i-2]
        int prev1 = Math.max(0, Math.max(h[0], l[0])); // dp[i-1] for i=1

        for (int i = 1; i < n; i++) {
            int lowEffort = prev1 + l[i];
            int highEffort = prev2 + h[i];
            int noTask = prev1;

            int curr = Math.max(noTask, Math.max(lowEffort, highEffort));
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }
}
```
