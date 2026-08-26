package com.ug.campusops.algorithms;

import com.ug.campusops.model.ServiceRequest;

/**
 * Dynamic Programming algorithm implementations for optimal request selection
 * and route/budget optimisation.
 *
 * Feature 7 — Optimisation Engine
 *
 * Required evidence:
 *   - Memoisation or tabulation table
 *   - Solution reconstruction (which items were selected)
 */
public class DynamicProgramming {

    /**
     * Holds the result of a knapsack-style optimisation.
     */
    public static class KnapsackResult {
        public int maxValue;               // maximum total priority/value achieved
        public boolean[] selectedItems;     // which requests/items were selected
        public int[][] dpTable;            // the full DP table (for evidence/trace)

        public KnapsackResult(int numItems) {
            this.selectedItems = new boolean[numItems];
        }

        /**
         * Returns the indices (into the original requests/items array) of the
         * items that were selected, e.g. "selected requests: #5, #12, #27".
         */
        public String selectedIndicesAsString() {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < selectedItems.length; i++) {
                if (selectedItems[i]) {
                    if (!first) sb.append(", ");
                    sb.append("#").append(i);
                    first = false;
                }
            }
            return sb.length() == 0 ? "(none)" : sb.toString();
        }

        /** Pretty-prints the DP tabulation table (rows = items, columns = budget). */
        public String dpTableAsString() {
            if (dpTable == null) return "(no table)";
            StringBuilder sb = new StringBuilder();
            for (int[] row : dpTable) {
                for (int val : row) {
                    sb.append(String.format("%4d", val));
                }
                sb.append('\n');
            }
            return sb.toString();
        }
    }

    

    /**
     * 0/1 Knapsack: select the best subset of service requests to handle within
     * a limited time/budget constraint, maximising total urgency/priority.
     *
     * Each request has a "cost" (estimated time to complete) and a "value" (urgencyLevel, 1-5).
     * The constraint is the total available time/budget (in the same minute
     * units as the estimated cost).
     *
     * @param requests array of candidate service requests
     * @param budget   maximum total time/cost allowed
     * @return a KnapsackResult with the optimal selection and DP table
     */
    public static KnapsackResult requestKnapsack(ServiceRequest[] requests, int budget) {
        if (requests == null) {
            throw new IllegalArgumentException("requests must not be null");
        }
        if (budget < 0) {
            throw new IllegalArgumentException("budget must not be negative");
        }

        int n = requests.length;
        int[] cost = new int[n];
        int[] value = new int[n];
        for (int i = 0; i < n; i++) {
            cost[i] = requests[i].getEstimatedTime();
            value[i] = requests[i].getUrgencyLevel();
        }

        return knapsack(cost, value, budget);
    }

    /**
     * Route/budget optimisation using DP.
     * Given a set of routes with costs and benefits, find the optimal set within budget.
     *
     * @param costs    cost (time/distance) of each route option
     * @param benefits benefit (coverage/value) of each route option
     * @param budget   total budget constraint
     * @return a KnapsackResult with optimal route selection
     */
    public static KnapsackResult optimalRouteBudget(int[] costs, int[] benefits, int budget) {
        if (costs == null || benefits == null) {
            throw new IllegalArgumentException("costs and benefits must not be null");
        }
        if (costs.length != benefits.length) {
            throw new IllegalArgumentException("costs and benefits must be the same length");
        }
        if (budget < 0) {
            throw new IllegalArgumentException("budget must not be negative");
        }

        return knapsack(costs, benefits, budget);
    }

    /**
     * Shared 0/1 knapsack tabulation + backtracking core.
     *
     *   dp[i][w] = max value achievable using items 0..i-1 with capacity w
     *
     * Base case: dp[0][w] = 0 for all w (no items -> no value).
     * Transition for item i-1 (0-indexed item i-1, 1-indexed row i):
     *   - If item doesn't fit (cost[i-1] > w): dp[i][w] = dp[i-1][w]
     *   - Else: dp[i][w] = max( dp[i-1][w],                       // skip item
     *                           value[i-1] + dp[i-1][w - cost[i-1]] ) // take item
     *
     * Reconstruction: walk the table from dp[n][budget] back to dp[0][0]; if
     * dp[i][w] != dp[i-1][w] the item at row i (index i-1) was taken, so move
     * to dp[i-1][w - cost[i-1]]; otherwise move straight to dp[i-1][w].
     */
    private static KnapsackResult knapsack(int[] cost, int[] value, int budget) {
        int n = cost.length;
        int[][] dp = new int[n + 1][budget + 1];

        for (int i = 1; i <= n; i++) {
            int itemCost = cost[i - 1];
            int itemValue = value[i - 1];
            for (int w = 0; w <= budget; w++) {
                if (itemCost > w) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    int skip = dp[i - 1][w];
                    int take = itemValue + dp[i - 1][w - itemCost];
                    dp[i][w] = Math.max(skip, take);
                }
            }
        }

        KnapsackResult result = new KnapsackResult(n);
        result.dpTable = dp;
        result.maxValue = dp[n][budget];

        int w = budget;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                result.selectedItems[i - 1] = true;
                w -= cost[i - 1];
            }
        }

        return result;
    }
}