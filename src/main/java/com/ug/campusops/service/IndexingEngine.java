package com.ug.campusops.service;

import com.ug.campusops.datastructures.BST;
import com.ug.campusops.datastructures.HashTable;
import com.ug.campusops.model.Location;
import com.ug.campusops.model.ServiceRequest;

/**
 * Uses trees and hash tables from Feature 3 to build search indexes over
 * locations, requests, and resources for fast lookup.
 *
 * Feature 8 — Service Layer
 * Depends on: Feature 3 (BST, HashTable)
 */
public class IndexingEngine {

    // Internal indexes — backed by custom BST and HashTable
    private BST<String, Location> locationNameIndex; // search locations by name
    private HashTable<Integer, ServiceRequest> requestIdIndex; // look up requests by ID
    private HashTable<String, Object> categoryIndex; // look up by category

    /** Creates an IndexingEngine with empty indexes. */
    public IndexingEngine() {
        this.locationNameIndex = new BST<>();
        this.requestIdIndex = new HashTable<>();
        this.categoryIndex = new HashTable<>();
    }

    /**
     * Builds the location name index from an array of locations.
     * Inserts each location into the BST keyed by name.
     *
     * @param locations array of Location objects to index
     */
    public void indexLocations(Location[] locations) {
        if (locations == null)
            return;
        for (Location location : locations) {
            if (location != null && location.getName() != null) {
                locationNameIndex.insert(location.getName(), location);
                String firstWord = location.getName().trim().split("\\s+")[0];
                if (locationNameIndex.search(firstWord.toLowerCase()) == null) {
                    locationNameIndex.insert(firstWord.toLowerCase(), location);
                }
            }
        }
    }

    /**
     * Builds the request index from an array of service requests.
     *
     * @param requests array of ServiceRequest objects to index
     */
    public void indexRequests(ServiceRequest[] requests) {
        if (requests == null)
            return;
        for (ServiceRequest request : requests) {
            if (request == null)
                continue;
            requestIdIndex.put(request.getRequestId(), request);
            String category = normalize(request.getCategory());
            ServiceRequest[] existing = (ServiceRequest[]) categoryIndex.get(category);
            ServiceRequest[] updated = new ServiceRequest[existing == null ? 1 : existing.length + 1];
            if (existing != null)
                System.arraycopy(existing, 0, updated, 0, existing.length);
            updated[updated.length - 1] = request;
            categoryIndex.put(category, updated);
        }
    }

    /**
     * Searches for a location by name using the BST index.
     *
     * @param name the location name to search for
     * @return the matching Location, or null if not found
     */
    public Location searchByName(String name) {
        if (name == null)
            return null;
        Location exact = locationNameIndex.search(name.trim());
        return exact != null ? exact : locationNameIndex.search(name.trim().toLowerCase());
    }

    /**
     * Searches for requests by category using the hash table index.
     *
     * @param category the category to search for (e.g. "electrical", "plumbing")
     * @return matching requests (implementation can return array or custom list)
     */
    public ServiceRequest[] searchByCategory(String category) {
        ServiceRequest[] requests = category == null ? null : (ServiceRequest[]) categoryIndex.get(normalize(category));
        return requests == null ? new ServiceRequest[0] : requests.clone();
    }

    public ServiceRequest searchRequestById(int requestId) {
        return requestIdIndex.get(requestId);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
