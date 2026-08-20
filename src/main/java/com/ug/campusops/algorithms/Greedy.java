package com.ug.campusops.algorithms;

import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.ServiceRequest;

/**
 * Greedy algorithm implementations for resource assignment and routing.
 * A greedy algorithm makes the best local choice at each step.
 *
 * Feature 7 — Optimisation Engine
 *
 * Required evidence:
 *   - Working greedy implementation
 *   - At least ONE counterexample where greedy fails (produces suboptimal result)
 */
public class Greedy {

    /**
     * Greedy nearest-resource assignment: for each request, assign the closest
     * available resource. Fast, but may not be globally optimal.
     *
     * Processes requests in the order given (array order). For each request it
     * scans every resource that is still "available" (both by its original
     * availabilityStatus AND not yet claimed earlier in this same pass), reads
     * the edge weight from the request's source location to the resource's
     * home location via graph.getWeight(), and greedily picks the smallest one.
     * Once a resource is picked it is marked busy so it cannot be re-used for a
     * later request in the same batch — this is exactly why greedy can be
     * suboptimal (see {@link #greedyCounterExample()}).
     *
     * @param requests  array of pending service requests
     * @param resources array of available resources
     * @param graph     the campus graph (for distance calculation)
     * @return array of resource IDs assigned to each request (parallel array);
     *         -1 at position i if no resource could be assigned to requests[i]
     */
    public static int[] nearestResourceAssignment(ServiceRequest[] requests,
                                                   Resource[] resources,
                                                   Graph graph) {
        if (requests == null || resources == null || graph == null) {
            throw new IllegalArgumentException("requests, resources, and graph must not be null");
        }

        int[] assignments = new int[requests.length];
        // Local "claimed" tracker so we don't mutate caller's Resource objects
        // unless we actually commit to the assignment.
        boolean[] claimed = new boolean[resources.length];
        for (int i = 0; i < resources.length; i++) {
            claimed[i] = !"available".equalsIgnoreCase(resources[i].getAvailabilityStatus());
        }

        for (int r = 0; r < requests.length; r++) {
            ServiceRequest request = requests[r];
            int bestIndex = -1;
            double bestDistance = Double.POSITIVE_INFINITY;

            for (int i = 0; i < resources.length; i++) {
                if (claimed[i]) {
                    continue; // already busy or already assigned earlier in this pass
                }
                double distance = graph.getWeight(request.getSourceLocationId(),
                                                   resources[i].getHomeLocationId());
                if (distance < 0) {
                    continue; // no edge / unreachable
                }
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestIndex = i;
                }
            }

            if (bestIndex >= 0) {
                assignments[r] = resources[bestIndex].getResourceId();
                claimed[bestIndex] = true;
                resources[bestIndex].setAvailabilityStatus("busy");
            } else {
                assignments[r] = -1; // no resource could be assigned
            }
        }

        return assignments;
    }

    /**
     * Demonstrates a case where the greedy approach gives a WRONG or SUBOPTIMAL answer.
     * Required by the brief as evidence of understanding greedy limitations.
     *
     * Scenario (all distances in metres, consistent with campus scale):
     *
     *   Two requests: R1 at location A, R2 at location B.
     *   Two available resources: X and Y.
     *
     *   Distances:
     *     A -> X = 100 m,   A -> Y = 110 m
     *     B -> X = 120 m,   B -> Y = 500 m
     *
     *   GREEDY (processes requests in order R1, R2, picks nearest available each time):
     *     R1 (at A) compares X (100 m) vs Y (110 m) -> picks X (smaller). X becomes busy.
     *     R2 (at B) has only Y left -> forced to take Y (500 m).
     *     Greedy total cost = 100 + 500 = 600 m.
     *
     *   OPTIMAL (considers both requests together before assigning):
     *     Assign Y to R1 (110 m) and X to R2 (120 m).
     *     Optimal total cost = 110 + 120 = 230 m.
     *
     *   Greedy (600 m) is 370 m worse than optimal (230 m) — greedy's locally
     *   best choice for R1 (grabbing X, which was only 10 m closer than Y) left
     *   R2 stuck with a resource 380 m farther away than it needed to be.
     *
     * @return a String describing the counterexample with numbers
     */
    public static String greedyCounterExample() {
        int distAX = 100;
        int distAY = 110;
        int distBX = 120;
        int distBY = 500;

        int greedyTotal = distAX + distBY;                 // R1->X, R2->Y (forced)
        int optimalTotal = distAY + distBX;                 // R1->Y, R2->X

        StringBuilder sb = new StringBuilder();
        sb.append("Greedy Counterexample: Nearest-Resource Assignment\n");
        sb.append("----------------------------------------------------\n");
        sb.append("Requests: R1 at Location A, R2 at Location B\n");
        sb.append("Resources: X and Y (both available at the start)\n");
        sb.append("Distances (m): A->X=").append(distAX)
          .append(", A->Y=").append(distAY)
          .append(", B->X=").append(distBX)
          .append(", B->Y=").append(distBY).append("\n\n");

        sb.append("Greedy (processes R1 then R2, always picks nearest available):\n");
        sb.append("  R1 at A compares X(").append(distAX).append(") vs Y(").append(distAY)
          .append(") -> picks X (smaller). X becomes busy.\n");
        sb.append("  R2 at B has only Y left -> forced to take Y(").append(distBY).append(").\n");
        sb.append("  Greedy total cost = ").append(distAX).append(" + ").append(distBY)
          .append(" = ").append(greedyTotal).append(" m\n\n");

        sb.append("Optimal (considers both requests together):\n");
        sb.append("  Assign Y to R1(").append(distAY).append(") and X to R2(").append(distBX).append(").\n");
        sb.append("  Optimal total cost = ").append(distAY).append(" + ").append(distBX)
          .append(" = ").append(optimalTotal).append(" m\n\n");

        sb.append("Conclusion: greedy total (").append(greedyTotal)
          .append(" m) is ").append(greedyTotal - optimalTotal)
          .append(" m worse than optimal (").append(optimalTotal)
          .append(" m). Greedy's locally-best pick for R1 starved R2 of the only\n")
          .append("other nearby resource, proving greedy is NOT globally optimal here.");

        return sb.toString();
    }
}