package com.ug.campusops.model;

/**
 * Represents a resource (maintenance staff, technician, shuttle, or vehicle)
 * that can be dispatched to handle service requests on the UG Legon campus.
 *
 * Maps to the 'resources' database table.
 */
public class Resource {

    private int resourceId;
    private String type;              // e.g. "Electrician", "Plumber", "IT Technician", "Shuttle"
    private int homeLocationId;       // where this resource is normally based
    private int capacity;             // workload capacity (e.g. max concurrent jobs)
    private String availabilityStatus; // "available", "busy", "off-duty"

    /** Default constructor */
    public Resource() {}

    /** Full constructor */
    public Resource(int resourceId, String type, int homeLocationId,
                    int capacity, String availabilityStatus) {
        this.resourceId = resourceId;
        this.type = type;
        this.homeLocationId = homeLocationId;
        this.capacity = capacity;
        this.availabilityStatus = availabilityStatus;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getResourceId() { return resourceId; }
    public String getType() { return type; }
    public int getHomeLocationId() { return homeLocationId; }
    public int getCapacity() { return capacity; }
    public String getAvailabilityStatus() { return availabilityStatus; }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setResourceId(int resourceId) { this.resourceId = resourceId; }
    public void setType(String type) { this.type = type; }
    public void setHomeLocationId(int homeLocationId) { this.homeLocationId = homeLocationId; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    @Override
    public String toString() {
        return String.format("Resource[id=%d, type='%s', home=%d, status='%s']",
                resourceId, type, homeLocationId, availabilityStatus);
    }
}
