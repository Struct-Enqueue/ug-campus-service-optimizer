package com.ug.campusops.service;

import com.ug.campusops.datastructures.CircularQueue;
import com.ug.campusops.datastructures.MyDeque;
import com.ug.campusops.datastructures.MyPriorityQueue;
import com.ug.campusops.datastructures.MyQueue;
import com.ug.campusops.model.ServiceRequest;

/**
 * Decides the order in which service requests get handled, using different
 * scheduling strategies built on custom data structures.
 *
 * Feature 8 — Service Layer
 * Depends on: Feature 2 (Queue, CircularQueue, Deque) and Feature 4 (PriorityQueue)
 *
 * Scheduling modes:
 *   - FIFO: first-come-first-served (uses MyQueue)
 *   - Circular: round-robin with wrap-around (uses CircularQueue)
 *   - Priority: most urgent first (uses MyPriorityQueue)
 *   - Deque: allows urgent insertion at front (uses MyDeque)
 */
public class SchedulingEngine {

    // The different queue types — Feature 8 team picks which to use based on scheduling mode
    private MyQueue<ServiceRequest> fifoQueue;
    private CircularQueue<ServiceRequest> circularQueue;
    private MyPriorityQueue<ServiceRequest> priorityQueue;
    private MyDeque<ServiceRequest> deque;

    /** Creates a SchedulingEngine with all queue types initialized. */
    public SchedulingEngine() {
        // TODO: Feature 8 team — initialize the queues
    }

    /**
     * Returns the next request to handle based on the current scheduling mode.
     *
     * @return the next ServiceRequest, or null if all queues are empty
     */
    public ServiceRequest scheduleNext() {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("SchedulingEngine.scheduleNext() not yet implemented");
    }

    /**
     * Adds a request to the appropriate queue based on its urgency.
     * Urgent requests (level 4-5) go to the front of the deque.
     * Normal requests go through the FIFO or priority queue.
     *
     * @param request the request to enqueue
     */
    public void addToQueue(ServiceRequest request) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("SchedulingEngine.addToQueue() not yet implemented");
    }

    /**
     * Returns a summary of how many requests are in each queue.
     *
     * @return a human-readable status string
     */
    public String getQueueStatus() {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("SchedulingEngine.getQueueStatus() not yet implemented");
    }
}
