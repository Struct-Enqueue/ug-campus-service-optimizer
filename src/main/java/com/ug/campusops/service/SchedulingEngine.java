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
 * Depends on: Feature 2 (Queue, CircularQueue, Deque) and Feature 4
 * (PriorityQueue)
 *
 * Scheduling modes:
 * - FIFO: first-come-first-served (uses MyQueue)
 * - Circular: round-robin with wrap-around (uses CircularQueue)
 * - Priority: most urgent first (uses MyPriorityQueue)
 * - Deque: allows urgent insertion at front (uses MyDeque)
 */
public class SchedulingEngine {

    public enum SchedulingMode {
        FIFO, CIRCULAR, PRIORITY, DEQUE
    }

    // The different queue types — Feature 8 team picks which to use based on
    // scheduling mode
    private MyQueue<ServiceRequest> fifoQueue;
    private CircularQueue<ServiceRequest> circularQueue;
    private MyPriorityQueue<ServiceRequest> priorityQueue;
    private MyDeque<ServiceRequest> deque;
    private SchedulingMode mode;

    /** Creates a SchedulingEngine with all queue types initialized. */
    public SchedulingEngine() {
        fifoQueue = new MyQueue<>();
        circularQueue = new CircularQueue<>(320);
        priorityQueue = new MyPriorityQueue<>();
        deque = new MyDeque<>();
        mode = SchedulingMode.PRIORITY;
    }

    /**
     * Returns the next request to handle based on the current scheduling mode.
     *
     * @return the next ServiceRequest, or null if all queues are empty
     */
    public ServiceRequest scheduleNext() {
        switch (mode) {
            case FIFO:
                return fifoQueue.isEmpty() ? null : fifoQueue.dequeue();
            case CIRCULAR:
                return circularQueue.isEmpty() ? null : circularQueue.dequeue();
            case DEQUE:
                return deque.isEmpty() ? null : deque.removeFront();
            case PRIORITY:
            default:
                return priorityQueue.isEmpty() ? null : priorityQueue.extractMin();
        }
    }

    /**
     * Adds a request to the appropriate queue based on its urgency.
     * Urgent requests (level 4-5) go to the front of the deque.
     * Normal requests go through the FIFO or priority queue.
     *
     * @param request the request to enqueue
     */
    public void addToQueue(ServiceRequest request) {
        if (request == null)
            throw new IllegalArgumentException("request must not be null");
        switch (mode) {
            case FIFO:
                fifoQueue.enqueue(request);
                break;
            case CIRCULAR:
                circularQueue.enqueue(request);
                break;
            case DEQUE:
                if (request.getUrgencyLevel() >= 4)
                    deque.addFront(request);
                else
                    deque.addRear(request);
                break;
            case PRIORITY:
            default:
                priorityQueue.insert(request);
                break;
        }
    }

    /**
     * Returns a summary of how many requests are in each queue.
     *
     * @return a human-readable status string
     */
    public String getQueueStatus() {
        return String.format("mode=%s, fifo=%d, circular=%d, priority=%d, deque=%d",
                mode, fifoQueue.size(), circularQueue.size(), priorityQueue.size(), deque.size());
    }

    public void setSchedulingMode(SchedulingMode mode) {
        if (mode == null)
            throw new IllegalArgumentException("mode must not be null");
        this.mode = mode;
    }

    public SchedulingMode getSchedulingMode() {
        return mode;
    }
}
