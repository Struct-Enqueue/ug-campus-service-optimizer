package com.ug.campusops.model;

/**
 * Represents a physical location on the University of Ghana, Legon campus.
 * Examples: Commonwealth Hall, Balme Library, JQB Lecture Complex, Night Market.
 *
 * Maps to the 'locations' database table.
 */
public class Location {

    private int locationId;
    private String name;
    private String area;       // e.g. "Main Campus Hill", "Diaspora Area", "Science Quad"
    private String type;       // e.g. "Traditional Hall", "Lecture Complex", "Shuttle Stop"
    private double latitude;
    private double longitude;

    /** Default constructor */
    public Location() {}

    /** Full constructor */
    public Location(int locationId, String name, String area, String type,
                    double latitude, double longitude) {
        this.locationId = locationId;
        this.name = name;
        this.area = area;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getLocationId() { return locationId; }
    public String getName() { return name; }
    public String getArea() { return area; }
    public String getType() { return type; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setLocationId(int locationId) { this.locationId = locationId; }
    public void setName(String name) { this.name = name; }
    public void setArea(String area) { this.area = area; }
    public void setType(String type) { this.type = type; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    @Override
    public String toString() {
        return String.format("Location[id=%d, name='%s', area='%s', type='%s']",
                locationId, name, area, type);
    }
}
