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
    }

    /**
     * 0/1 Knapsack: select the best subset of service requests to handle within
     * a limited time/budget constraint, maximising total urgency/priority.
     *
     * Each request has a "cost" (time to complete) and a "value" (urgency level).
     * The constraint is the total available time/budget.
     *
     * @param requests array of candidate service requests
     * @param budget   maximum total time/cost allowed
     * @return a KnapsackResult with the optimal selection and DP table
     */
    public static KnapsackResult requestKnapsack(ServiceRequest[] requests, int budget) {
        // TODO: Feature 7 team — implement this
        //   1. Build DP table: dp[i][w] = max value using items 0..i-1 with capacity w
        //   2. Fill table bottom-up
        //   3. Backtrack to find which items were selected
        throw new UnsupportedOperationException("DynamicProgramming.requestKnapsack() not yet implemented");
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
        // TODO: Feature 7 team — implement this
        throw new UnsupportedOperationException("DynamicProgramming.optimalRouteBudget() not yet implemented");
    }
}
