package com.ug.campusops.model;

/**
 * Represents a service request (maintenance complaint) submitted on the UG Legon campus.
 * Examples: "AC broken at Commonwealth Hall", "Pipe burst in Volta Hall", "Projector dead in JQB".
 *
 * Maps to the 'service_requests' database table.
 */
public class ServiceRequest implements Comparable<ServiceRequest> {

    private int requestId;
    private int sourceLocationId;      // where the problem is
    private int destinationLocationId; // where the resource needs to go (usually same as source)
    private String category;           // "electrical", "plumbing", "IT", "cleaning", "structural"
    private int urgencyLevel;          // 1 (minor) to 5 (critical)
    private String timeSubmitted;      // ISO datetime string, e.g. "2025-09-15T08:30:00"
    private String deadline;           // ISO datetime string for when it must be resolved
    private String status;             // "pending", "assigned", "in-progress", "resolved"
    private int assignedResourceId;    // 0 or -1 if not yet assigned

    /** Default constructor */
    public ServiceRequest() {}

    /** Full constructor */
    public ServiceRequest(int requestId, int sourceLocationId, int destinationLocationId,
                          String category, int urgencyLevel, String timeSubmitted,
                          String deadline, String status, int assignedResourceId) {
        this.requestId = requestId;
        this.sourceLocationId = sourceLocationId;
        this.destinationLocationId = destinationLocationId;
        this.category = category;
        this.urgencyLevel = urgencyLevel;
        this.timeSubmitted = timeSubmitted;
        this.deadline = deadline;
        this.status = status;
        this.assignedResourceId = assignedResourceId;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getRequestId() { return requestId; }
    public int getSourceLocationId() { return sourceLocationId; }
    public int getDestinationLocationId() { return destinationLocationId; }
    public String getCategory() { return category; }
    public int getUrgencyLevel() { return urgencyLevel; }
    public String getTimeSubmitted() { return timeSubmitted; }
    public String getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public int getAssignedResourceId() { return assignedResourceId; }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setRequestId(int requestId) { this.requestId = requestId; }
    public void setSourceLocationId(int sourceLocationId) { this.sourceLocationId = sourceLocationId; }
    public void setDestinationLocationId(int destinationLocationId) { this.destinationLocationId = destinationLocationId; }
    public void setCategory(String category) { this.category = category; }
    public void setUrgencyLevel(int urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public void setTimeSubmitted(String timeSubmitted) { this.timeSubmitted = timeSubmitted; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public void setStatus(String status) { this.status = status; }
    public void setAssignedResourceId(int assignedResourceId) { this.assignedResourceId = assignedResourceId; }

    @Override
    public String toString() {
        return String.format("ServiceRequest[id=%d, loc=%d, cat='%s', urgency=%d, status='%s']",
                requestId, sourceLocationId, category, urgencyLevel, status);
    }

    /**
     * Compares by urgency level (higher urgency = "smaller" for min-heap so it gets extracted first).
     * If urgency is equal, earlier submission time comes first (FIFO tiebreaker).
     */
    @Override
    public int compareTo(ServiceRequest other) {
        // Higher urgency should come first → negate the comparison
        int cmp = Integer.compare(other.urgencyLevel, this.urgencyLevel);
        if (cmp != 0) return cmp;
        // Same urgency → earlier submission first
        return this.timeSubmitted.compareTo(other.timeSubmitted);
    }
}
