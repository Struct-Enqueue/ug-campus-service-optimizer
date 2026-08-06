package com.ug.campusops.model;

/**
 * Represents an audit event for tracking system operations.
 * Used for the stack-based undo/audit log (supports Feature 2's Stack demo).
 *
 * Maps to the 'audit_events' database table.
 */
public class AuditEvent {

    private int eventId;
    private String eventType;    // e.g. "REQUEST_CREATED", "RESOURCE_ASSIGNED", "STATUS_CHANGED", "UNDO"
    private String entityType;   // e.g. "ServiceRequest", "Resource", "Location"
    private int entityId;        // ID of the affected entity
    private String description;  // human-readable description of what happened
    private String timestamp;    // ISO datetime string

    /** Default constructor */
    public AuditEvent() {}

    /** Full constructor */
    public AuditEvent(int eventId, String eventType, String entityType,
                      int entityId, String description, String timestamp) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.description = description;
        this.timestamp = timestamp;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getEventId() { return eventId; }
    public String getEventType() { return eventType; }
    public String getEntityType() { return entityType; }
    public int getEntityId() { return entityId; }
    public String getDescription() { return description; }
    public String getTimestamp() { return timestamp; }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public void setEntityId(int entityId) { this.entityId = entityId; }
    public void setDescription(String description) { this.description = description; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return String.format("AuditEvent[id=%d, type='%s', entity='%s#%d', time='%s']",
                eventId, eventType, entityType, entityId, timestamp);
    }
}
