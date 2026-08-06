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
    private BST<String, Location> locationNameIndex;      // search locations by name
    private HashTable<Integer, ServiceRequest> requestIdIndex; // look up requests by ID
    private HashTable<String, Object> categoryIndex;      // look up by category

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
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("IndexingEngine.indexLocations() not yet implemented");
    }

    /**
     * Builds the request index from an array of service requests.
     *
     * @param requests array of ServiceRequest objects to index
     */
    public void indexRequests(ServiceRequest[] requests) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("IndexingEngine.indexRequests() not yet implemented");
    }

    /**
     * Searches for a location by name using the BST index.
     *
     * @param name the location name to search for
     * @return the matching Location, or null if not found
     */
    public Location searchByName(String name) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("IndexingEngine.searchByName() not yet implemented");
    }

    /**
     * Searches for requests by category using the hash table index.
     *
     * @param category the category to search for (e.g. "electrical", "plumbing")
     * @return matching requests (implementation can return array or custom list)
     */
    public ServiceRequest[] searchByCategory(String category) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("IndexingEngine.searchByCategory() not yet implemented");
    }
}
