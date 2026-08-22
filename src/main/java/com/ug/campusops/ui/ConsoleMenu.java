package com.ug.campusops.ui;

import java.util.Scanner;

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

    /** Creates the console menu. */
    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu and processes user choices in a loop.
     * This is the entry point — called from Main.java.
     */
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
                    System.out.println("[Option 1] Report a new service request - Coming soon!");
                    // TODO: Later, call dispatcher.submitRequest()
                    break;

                case 2:
                    System.out.println("[Option 2] View pending requests - Coming soon!");
                    // TODO: Later, call scheduler.getQueueStatus() or fetch from DB
                    break;

                case 3:
                    System.out.println("[Option 3] Process next request (dispatch) - Coming soon!");
                    // TODO: Later, call dispatcher.assignResource()
                    break;

                case 4:
                    System.out.println("[Option 4] Find fastest route between locations - Coming soon!");
                    // TODO: Later, call Dijkstra.shortestPath()
                    break;

                case 5:
                    System.out.println("[Option 5] Check reachable locations (BFS/DFS) - Coming soon!");
                    // TODO: Later, call BFS.traverse() or DFS.traverse()
                    break;

                case 6:
                    System.out.println("[Option 6] View minimum spanning tree (Prim/Kruskal) - Coming soon!");
                    // TODO: Later, call Prim.minimumSpanningTree() or Kruskal...
                    break;

                case 7:
                    System.out.println("[Option 7] Search for a location or request - Coming soon!");
                    // TODO: Later, call indexingEngine.searchByName()
                    break;

                case 8:
                    System.out.println("[Option 8] Sort and display requests - Coming soon!");
                    // TODO: Later, call SortAlgorithms.mergeSort()
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
                    System.out.println("[Option 11] View queue status - Coming soon!");
                    // TODO: Later, call schedulingEngine.getQueueStatus()
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
}
