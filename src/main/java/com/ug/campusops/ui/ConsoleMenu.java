package com.ug.campusops.ui;

import java.util.Scanner;
import com.ug.campusops.model.ServiceRequest;
import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.service.RequestDispatcher;
import java.util.ArrayList;
import java.util.List;

import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Location;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import com.ug.campusops.graph.Dijkstra;
import com.ug.campusops.graph.BFS;
import com.ug.campusops.graph.DFS;
import com.ug.campusops.graph.Prim;
import com.ug.campusops.graph.Kruskal;

/**
 * Console-based menu system for the Campus Smart Service Operations Optimizer.
 * This is what the examiner will see and interact with during the demo and oral defense.
 *
 * Feature 9 — Console/UI & Final Integration
 *
 * Menu options (connect each to the real Service Layer code):
 *   1. Report a new service request
 *   2. View pending requests
 *   3. Process next request (dispatch)
 *   4. Find fastest route between two locations
 *   5. Check reachable locations (BFS/DFS)
 *   6. View minimum spanning tree
 *   7. Search for a location or request
 *   8. Sort and display requests
 *   9. Run optimisation (Greedy / DP)
 *  10. Run performance experiments
 *  11. View queue status
 *   0. Exit
 */
public class ConsoleMenu {

    private Scanner scanner;
    private List<ServiceRequest> pendingRequests;
    private List<Location> locations;
    private Graph campusGraph;
    private DatabaseConnector db;
    private RequestDispatcher requestDispatcher;

    /** Creates the console menu. */
    public ConsoleMenu(Graph campusGraph, DatabaseConnector db) {
        this.scanner = new Scanner(System.in);
        this.pendingRequests = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.campusGraph = campusGraph;
        this.db = db;
        this.requestDispatcher = new RequestDispatcher(db, campusGraph);
        loadLocations();
    }

    /**
     * Displays the main menu and processes user choices in a loop.
     * This is the entry point — called from Main.java.
     */


    // double getWeight() {
    //     return distance * trafficFactor;
    // }

    private boolean locationExists(int id) {
    for (Location loc : locations) {
        if (loc.getLocationId() == id) {
            return true;
        }
    }
    return false;
}

private String getLocationName(int id) {
    for (Location loc : locations) {
        if (loc.getLocationId() == id) {
            return loc.getName();
        }
    }
    return null;
}

    private void findFastestRoute() {
    System.out.println("\n Find Fastest Route Between Locations");
    System.out.println("------------------------------------");

    if (campusGraph == null || campusGraph.getVertexCount() == 0) {
        System.out.println(" Graph not loaded. Please check database connection.");
        return;
    }

    System.out.print("Enter start location ID: ");
    int startId = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Enter destination location ID: ");
    int destId = scanner.nextInt();
    scanner.nextLine();

    // Check if locations exist in our loaded locations list
    if (!locationExists(startId)) {
        System.out.println(" Invalid start location ID.");
        return;
    }
    if (!locationExists(destId)) {
        System.out.println(" Invalid destination location ID.");
        return;
    }

    Dijkstra.ShortestPathResult result = Dijkstra.shortestPath(campusGraph, startId, destId);

    // Check if a path was found
    if (result.distances[destId] == Double.POSITIVE_INFINITY) {
        System.out.println(" No route found between " + startId + " and " + destId);
        return;
    }

    // Reconstruct the path from predecessors
    List<Integer> path = new ArrayList<>();
    int current = destId;
    while (current != startId) {
        path.add(current);
        current = result.predecessors[current];
        if (current == -1) {
            break;
        }
    }
    path.add(startId);
    Collections.reverse(path);

    // Display the results
    System.out.println("\n Shortest route found!");
    System.out.printf("   Total distance: %.0f meters%n", result.distances[destId]);

    System.out.println("   Path: ");
    for (int i = 0; i < path.size(); i++) {
        int id = path.get(i);
        String name = getLocationName(id);
        System.out.print("   " + (i + 1) + ". " + (name != null ? name : "Unknown (" + id + ")"));
        if (i < path.size() - 1) {
            System.out.println(" →");
        }
    }
    System.out.println();
}

private void checkReachableLocations() {
    System.out.println("\n    Check Reachable Locations (BFS/DFS)");
    System.out.println("------------------------------------");

    if (campusGraph == null || campusGraph.getVertexCount() == 0) {
        System.out.println("    Graph not loaded. Please check database connection.");
        return;
    }

    System.out.print("Enter start location ID: ");
    int startId = scanner.nextInt();
    scanner.nextLine();

    if (!locationExists(startId)) {
        System.out.println("    Invalid location ID.");
        return;
    }

    System.out.println("Choose algorithm:");
    System.out.println("1. BFS (Breadth-First Search)");
    System.out.println("2. DFS (Depth-First Search)");
    System.out.print("Enter choice (1 or 2): ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    int[] result;
    String algorithmName;
    if (choice == 1) {
        result = BFS.traverse(campusGraph, startId);
        algorithmName = "BFS";
    } else if (choice == 2) {
        result = DFS.traverse(campusGraph, startId);
        algorithmName = "DFS";
    } else {
        System.out.println(" Invalid choice.");
        return;
    }

    System.out.println("\n    " + algorithmName + " traversal from location " + startId + ":");
    System.out.println("Found " + result.length + " reachable location(s):");
    System.out.println("ID | Name");
    System.out.println("----------------");
    for (int id : result) {
        String name = getLocationName(id);
        System.out.printf("%-2d | %s%n", id, name != null ? name : "Unknown");
    }
}

private void viewMinimumSpanningTree() {
    System.out.println("\n    Minimum Spanning Tree (MST)");
    System.out.println("------------------------------------");

    if (campusGraph == null || campusGraph.getVertexCount() == 0) {
        System.out.println("    Graph not loaded. Please check database connection.");
        return;
    }

    System.out.println("Choose algorithm:");
    System.out.println("1. Prim's Algorithm");
    System.out.println("2. Kruskal's Algorithm");
    System.out.print("Enter choice (1 or 2): ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    Prim.MSTResult result;
    String algorithmName;
    if (choice == 1) {
        result = Prim.minimumSpanningTree(campusGraph);
        algorithmName = "Prim";
    } else if (choice == 2) {
        result = Kruskal.minimumSpanningTree(campusGraph);
        algorithmName = "Kruskal";
    } else {
        System.out.println("    Invalid choice.");
        return;
    }

    System.out.println("\n🌲 MST using " + algorithmName + "'s algorithm:");
    System.out.println("   Total edges: " + result.edgeCount);
    System.out.printf("   Total cost: %.2f%n", result.totalCost);

    System.out.println("\n   Edges in the MST:");
    System.out.println("   From | To   | Weight");
    System.out.println("   ----------------------");
    for (int i = 0; i < result.edgeCount; i++) {
        int fromId = result.edges[i][0];
        int toId = result.edges[i][1];
        String fromName = getLocationName(fromId);
        String toName = getLocationName(toId);
        System.out.printf("   %-4s | %-4s | %.2f%n",
            fromName != null ? fromName : String.valueOf(fromId),
            toName != null ? toName : String.valueOf(toId),
            result.weights[i]
        );
    }
}
   
    public void start() {
        int choice;

        do {
            printMenu();
            
            // Read user input
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;

                case 1:
                    reportRequest();
                    break;

                case 2:
                    viewPendingRequests();
                    break;

                case 3:
                    processNextRequest();
                    break;

                case 4:
                    findFastestRoute();
                    break;

                case 5:
                    checkReachableLocations();
                    break;

                case 6:
                    viewMinimumSpanningTree();
                    break;

                case 7:
                    search();
                    break;

                case 8:
                    sortAndDisplayRequests();
                    break;

                case 9:
                    System.out.println("[Option 9] Run optimisation (Greedy / DP) - Coming soon!");
                    // TODO: Later, call Greedy.nearestResourceAssignment() or DP...
                    break;

                case 10:
                    System.out.println("[Option 10] Run performance experiments - Coming soon!");
                    // TODO: Later, run the timing tests
                    break;

                case 11:
                   viewQueueStatus();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 11.");
            }
            System.out.println();
        } while (choice != 0);
    }
    
    private void printMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   UG Campus Smart Service Operations Optimizer       ║");
        System.out.println("║   University of Ghana, Legon                         ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  1.  Report a new service request                    ║");
        System.out.println("║  2.  View pending requests                           ║");
        System.out.println("║  3.  Process next request (dispatch)                 ║");
        System.out.println("║  4.  Find fastest route between locations            ║");
        System.out.println("║  5.  Check reachable locations (BFS/DFS)             ║");
        System.out.println("║  6.  View minimum spanning tree (Prim/Kruskal)       ║");
        System.out.println("║  7.  Search for a location or request                ║");
        System.out.println("║  8.  Sort and display requests                       ║");
        System.out.println("║  9.  Run optimisation (Greedy / DP)                  ║");
        System.out.println("║ 10.  Run performance experiments                     ║");
        System.out.println("║ 11.  View queue status                               ║");
        System.out.println("║  0.  Exit                                            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        System.out.print("Enter your choice: ");
    }

        private void reportRequest() {
        System.out.println("\nReport a new service request");
        System.out.println("------------------------------------");

        // 1. Get location ID
        System.out.print("Enter location ID (where the problem is): ");
        int locationId = scanner.nextInt();
        scanner.nextLine();

        // 2. Get category
        System.out.print("Enter category (electrical/plumbing/IT/cleaning/structural): ");
        String category = scanner.nextLine().toLowerCase();

        // Simple validation for category
        while (!category.equals("electrical") && !category.equals("plumbing") &&
               !category.equals("it") && !category.equals("cleaning") &&
               !category.equals("structural")) {
            System.out.print("   Invalid category. Please enter (electrical/plumbing/IT/cleaning/structural): ");
            category = scanner.nextLine().toLowerCase();
        }

        // 3. Get urgency level
        System.out.print("Enter urgency level (1-5, where 5 is most urgent): ");
        int urgency = scanner.nextInt();
        scanner.nextLine();

        while (urgency < 1 || urgency > 5) {
            System.out.print("   Urgency must be between 1 and 5. Please enter again: ");
            urgency = scanner.nextInt();
            scanner.nextLine();
        }

        // 4. Create and store the request
        ServiceRequest request = new ServiceRequest();
        request.setRequestId(pendingRequests.size() + 1);  // simple ID for now
        request.setSourceLocationId(locationId);
        request.setDestinationLocationId(locationId);      // same as source for now
        request.setCategory(category);
        request.setUrgencyLevel(urgency);
        request.setStatus("pending");

        pendingRequests.add(request);

        // Persist the request to the database via RequestDispatcher
        try {
            if (requestDispatcher != null) {
                requestDispatcher.submitRequest(request);
                System.out.println("   (Saved to database)");
            }
        } catch (Exception e) {
            System.out.println("   (Warning: could not save to database: " + e.getMessage() + ")");
        }
        // 5. Show confirmation
        System.out.println("\n   Request #" + request.getRequestId() + " submitted successfully!");
        System.out.println("   Location ID: " + locationId);
        System.out.println("   Category: " + category);
        System.out.println("   Urgency: " + urgency + "/5");
        System.out.println("   Status: pending");
    }

    // ─── For testing: get pending requests (will be used by Option 2) ──

    public List<ServiceRequest> getPendingRequests() {
        return pendingRequests;
    }

    private void viewPendingRequests() {
    System.out.println("\n Pending Service Requests");
    System.out.println("------------------------------------");
    
    if (pendingRequests.isEmpty()) {
        System.out.println("No pending requests found.");
        return;
    }
    
    System.out.println("ID | Location | Category    | Urgency | Status");
    System.out.println("------------------------------------------------");
    
    for (ServiceRequest req : pendingRequests) {
        System.out.printf("%-2d | %-8d | %-11s | %-7d | %s%n",
            req.getRequestId(),
            req.getSourceLocationId(),
            req.getCategory(),
            req.getUrgencyLevel(),
            req.getStatus()
        );
    }
    System.out.println("\nTotal pending: " + pendingRequests.size());
}

private void sortAndDisplayRequests() {
    System.out.println("\n   Sort and Display Requests");
    System.out.println("------------------------------------");
    
    if (pendingRequests.isEmpty()) {
        System.out.println("No pending requests to sort.");
        return;
    }
    
    System.out.println("Sort by:");
    System.out.println("1. Urgency (highest first)");
    System.out.println("2. Category (alphabetical)");
    System.out.println("3. Request ID (ascending)");
    System.out.print("Choose sort option (1-3): ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    // Create a copy of the list so we don't modify the original order
    List<ServiceRequest> sortedList = new ArrayList<>(pendingRequests);
    
    switch (choice) {
        case 1:
            sortedList.sort((a, b) -> Integer.compare(b.getUrgencyLevel(), a.getUrgencyLevel()));
            System.out.println("\n   Requests sorted by urgency (highest first):");
            break;
        case 2:
            sortedList.sort((a, b) -> a.getCategory().compareTo(b.getCategory()));
            System.out.println("\n   Requests sorted by category (alphabetical):");
            break;
        case 3:
            sortedList.sort((a, b) -> Integer.compare(a.getRequestId(), b.getRequestId()));
            System.out.println("\n   Requests sorted by request ID:");
            break;
        default:
            System.out.println("   Invalid choice.");
            return;
    }
    
    System.out.println("------------------------------------");
    System.out.println("ID | Location | Category    | Urgency | Status");
    System.out.println("------------------------------------------------");
    
    for (ServiceRequest req : sortedList) {
        System.out.printf("%-2d | %-8d | %-11s | %-7d | %s%n",
            req.getRequestId(),
            req.getSourceLocationId(),
            req.getCategory(),
            req.getUrgencyLevel(),
            req.getStatus()
        );
    }
    System.out.println("\nTotal: " + sortedList.size() + " requests");
}

private void viewQueueStatus() {
    System.out.println("\n   Queue Status");
    System.out.println("------------------------------------");
    
    if (pendingRequests.isEmpty()) {
        System.out.println("   No pending requests in the queue.");
        return;
    }
    
    // 1. Total count
    System.out.println("   Total pending requests: " + pendingRequests.size());
    System.out.println();
    
    // 2. Breakdown by urgency (1-5)
    int[] urgencyCounts = new int[6]; // indices 0-5, we'll use 1-5
    for (ServiceRequest req : pendingRequests) {
        urgencyCounts[req.getUrgencyLevel()]++;
    }
    
    System.out.println("   Urgency Breakdown:");
    for (int i = 5; i >= 1; i--) {
        if (urgencyCounts[i] > 0) {
            String label = (i == 5) ? " (Critical)" : (i == 1) ? " (Minor)" : "";
            System.out.printf("   Level %d%s: %d request(s)%n", i, label, urgencyCounts[i]);
        }
    }
    System.out.println();
    
    // 3. Breakdown by category
    java.util.Map<String, Integer> categoryCounts = new java.util.HashMap<>();
    for (ServiceRequest req : pendingRequests) {
        categoryCounts.put(req.getCategory(), 
            categoryCounts.getOrDefault(req.getCategory(), 0) + 1);
    }
    
    System.out.println("   Category Breakdown:");
    // Sort categories alphabetically
    java.util.List<String> sortedCategories = new java.util.ArrayList<>(categoryCounts.keySet());
    java.util.Collections.sort(sortedCategories);
    for (String cat : sortedCategories) {
        System.out.printf("   %-11s: %d request(s)%n", cat, categoryCounts.get(cat));
    }
    System.out.println();
    
    // 4. Average urgency
    double totalUrgency = 0;
    for (ServiceRequest req : pendingRequests) {
        totalUrgency += req.getUrgencyLevel();
    }
    double avgUrgency = totalUrgency / pendingRequests.size();
    System.out.printf("   Average urgency level: %.1f / 5.0%n", avgUrgency);
}

private void processNextRequest() {
    System.out.println("\n Process Next Request (Dispatch)");
    System.out.println("------------------------------------");
    // Use RequestDispatcher + SchedulingEngine to process next request
    try {
        int assigned = -1;
        if (requestDispatcher != null) {
            assigned = requestDispatcher.processNextRequest();
        }
        if (assigned > 0) {
            System.out.println("   Request assigned to resource ID: " + assigned);
        } else if (assigned == -1) {
            System.out.println("   No request processed or no available resource.");
        }
    } catch (Exception e) {
        System.out.println("   Error while processing next request: " + e.getMessage());
    }
}

private void loadLocations() {
    locations = new ArrayList<>();
    String filePath = "data/locations.csv";
    
    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
        String line = br.readLine(); // skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String area = parts[2];
                String type = parts[3];
                double lat = Double.parseDouble(parts[4]);
                double lng = Double.parseDouble(parts[5]);
                locations.add(new Location(id, name, area, type, lat, lng));
            }
        }
        System.out.println("[INFO] Loaded " + locations.size() + " locations from CSV.");
    } catch (Exception e) {
        System.out.println("[WARNING] Could not load locations: " + e.getMessage());
    }
}

private void search() {
    System.out.println("\n Search for a location or request");
    System.out.println("------------------------------------");
    System.out.println("1. Search by Request ID");
    System.out.println("2. Search by Location Name");
    System.out.print("Choose search type (1 or 2): ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    switch (choice) {
        case 1:
            searchRequestById();
            break;
        case 2:
            searchLocationByName();
            break;
        default:
            System.out.println(" Invalid choice.");
    }
}

private void searchRequestById() {
    System.out.print("Enter Request ID: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    
    for (ServiceRequest req : pendingRequests) {
        if (req.getRequestId() == id) {
            System.out.println("\n   Request Found:");
            System.out.println("   ID: " + req.getRequestId());
            System.out.println("   Location: " + req.getSourceLocationId());
            System.out.println("   Category: " + req.getCategory());
            System.out.println("   Urgency: " + req.getUrgencyLevel() + "/5");
            System.out.println("   Status: " + req.getStatus());
            return;
        }
    }
    System.out.println("   No pending request found with ID: " + id);
}

private void searchLocationByName() {
    System.out.print("Enter location name (or partial): ");
    String query = scanner.nextLine().toLowerCase();
    
    List<Location> results = new ArrayList<>();
    for (Location loc : locations) {
        if (loc.getName().toLowerCase().contains(query)) {
            results.add(loc);
        }
    }
    
    if (results.isEmpty()) {
        System.out.println("   No locations found matching: " + query);
        return;
    }
    
    System.out.println("\n   Found " + results.size() + " location(s):");
    System.out.println("ID | Name                           | Area");
    System.out.println("-----------------------------------------------");
    for (Location loc : results) {
        System.out.printf("%-2d | %-30s | %s%n",
            loc.getLocationId(),
            loc.getName().length() > 30 ? loc.getName().substring(0, 27) + "..." : loc.getName(),
            loc.getArea()
        );
    }
}



}
