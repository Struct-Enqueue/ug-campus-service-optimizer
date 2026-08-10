package com.ug.campusops.algorithms;

import com.ug.campusops.model.ServiceRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DynamicProgramming. No dependency on Graph/Dijkstra/DB —
 * ServiceRequest is a plain finished model class, so these run standalone.
 */
class DynamicProgrammingTest {

    @Test
    void testOptimalRouteBudgetMatchesBruteForce() {
        int[] cost = {30, 60, 20, 90, 120};
        int[] value = {5, 4, 2, 3, 5};
        int budget = 100;

        DynamicProgramming.KnapsackResult result =
                DynamicProgramming.optimalRouteBudget(cost, value, budget);

        int expected = bruteForceKnapsack(cost, value, budget);
        assertEquals(expected, result.maxValue);

        // selected items must actually respect the budget
        int totalCost = 0, totalValue = 0;
        for (int i = 0; i < cost.length; i++) {
            if (result.selectedItems[i]) {
                totalCost += cost[i];
                totalValue += value[i];
            }
        }
        assertTrue(totalCost <= budget, "selection must not exceed budget");
        assertEquals(result.maxValue, totalValue, "reported maxValue must match reconstructed selection");
    }

    @Test
    void testZeroBudgetSelectsNothing() {
        int[] cost = {10, 20};
        int[] value = {5, 5};
        DynamicProgramming.KnapsackResult result = DynamicProgramming.optimalRouteBudget(cost, value, 0);
        assertEquals(0, result.maxValue);
        for (boolean selected : result.selectedItems) {
            assertFalse(selected);
        }
    }

    @Test
    void testSingleItemFitsExactly() {
        int[] cost = {50};
        int[] value = {9};
        DynamicProgramming.KnapsackResult result = DynamicProgramming.optimalRouteBudget(cost, value, 50);
        assertEquals(9, result.maxValue);
        assertTrue(result.selectedItems[0]);
    }

    @Test
    void testRequestKnapsackWithServiceRequests() {
        ServiceRequest[] reqs = new ServiceRequest[3];
        reqs[0] = new ServiceRequest(1, 1, 1, "IT", 5, "t", "d", "pending", 0);        // cost 30, value 5
        reqs[1] = new ServiceRequest(2, 1, 1, "cleaning", 2, "t", "d", "pending", 0);   // cost 20, value 2
        reqs[2] = new ServiceRequest(3, 1, 1, "structural", 5, "t", "d", "pending", 0); // cost 120, value 5

        DynamicProgramming.KnapsackResult result = DynamicProgramming.requestKnapsack(reqs, 50);

        // Budget 50: request 0 (30) + request 1 (20) = 50 cost, 7 value. Request 2 (120) can't fit.
        assertEquals(7, result.maxValue);
        assertTrue(result.selectedItems[0]);
        assertTrue(result.selectedItems[1]);
        assertFalse(result.selectedItems[2]);
    }

    @Test
    void testNegativeBudgetThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> DynamicProgramming.optimalRouteBudget(new int[]{1}, new int[]{1}, -5));
    }

    @Test
    void testMismatchedArrayLengthsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> DynamicProgramming.optimalRouteBudget(new int[]{1, 2}, new int[]{1}, 10));
    }

    /** Simple 2^n brute force used only to validate the DP result in tests. */
    private static int bruteForceKnapsack(int[] cost, int[] value, int budget) {
        int n = cost.length;
        int best = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            int c = 0, v = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    c += cost[i];
                    v += value[i];
                }
            }
            if (c <= budget && v > best) best = v;
        }
        return best;
    }
}
