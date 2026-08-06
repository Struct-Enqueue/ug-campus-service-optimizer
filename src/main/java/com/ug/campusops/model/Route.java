package com.ug.campusops.model;

/**
 * Represents a route (road/path) between two locations on the UG Legon campus.
 * Each route has a distance, average travel time, and a traffic factor that
 * reflects how congested the route gets during peak hours.
 *
 * Maps to the 'routes' database table.
 */
public class Route {

    private int routeId;
    private int fromLocationId;
    private int toLocationId;
    private int distanceM;        // distance in meters
    private int avgTimeMin;       // average travel time in minutes
    private double trafficFactor; // 1.0 = normal, 1.5 = 50% slower at peak

    /** Default constructor */
    public Route() {}

    /** Full constructor */
    public Route(int routeId, int fromLocationId, int toLocationId,
                 int distanceM, int avgTimeMin, double trafficFactor) {
        this.routeId = routeId;
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.distanceM = distanceM;
        this.avgTimeMin = avgTimeMin;
        this.trafficFactor = trafficFactor;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getRouteId() { return routeId; }
    public int getFromLocationId() { return fromLocationId; }
    public int getToLocationId() { return toLocationId; }
    public int getDistanceM() { return distanceM; }
    public int getAvgTimeMin() { return avgTimeMin; }
    public double getTrafficFactor() { return trafficFactor; }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setRouteId(int routeId) { this.routeId = routeId; }
    public void setFromLocationId(int fromLocationId) { this.fromLocationId = fromLocationId; }
    public void setToLocationId(int toLocationId) { this.toLocationId = toLocationId; }
    public void setDistanceM(int distanceM) { this.distanceM = distanceM; }
    public void setAvgTimeMin(int avgTimeMin) { this.avgTimeMin = avgTimeMin; }
    public void setTrafficFactor(double trafficFactor) { this.trafficFactor = trafficFactor; }

    @Override
    public String toString() {
        return String.format("Route[id=%d, from=%d, to=%d, dist=%dm, time=%dmin, traffic=%.1f]",
                routeId, fromLocationId, toLocationId, distanceM, avgTimeMin, trafficFactor);
    }
}
