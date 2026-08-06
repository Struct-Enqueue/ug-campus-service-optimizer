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
        // TODO: Feature 9 team — implement this
        //   1. Print the menu options
        //   2. Read user choice
        //   3. Call the appropriate Service Layer method
        //   4. Display results
        //   5. Loop until user chooses Exit (0)
        throw new UnsupportedOperationException("ConsoleMenu.start() not yet implemented");
    }

    /**
     * Prints the menu banner and options.
     */
    private void printMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   UG Campus Smart Service Operations Optimizer      ║");
        System.out.println("║   University of Ghana, Legon                        ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  1.  Report a new service request                   ║");
        System.out.println("║  2.  View pending requests                          ║");
        System.out.println("║  3.  Process next request (dispatch)                ║");
        System.out.println("║  4.  Find fastest route between locations           ║");
        System.out.println("║  5.  Check reachable locations (BFS/DFS)            ║");
        System.out.println("║  6.  View minimum spanning tree (Prim/Kruskal)      ║");
        System.out.println("║  7.  Search for a location or request               ║");
        System.out.println("║  8.  Sort and display requests                      ║");
        System.out.println("║  9.  Run optimisation (Greedy / DP)                 ║");
        System.out.println("║ 10.  Run performance experiments                    ║");
        System.out.println("║ 11.  View queue status                              ║");
        System.out.println("║  0.  Exit                                           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
}
