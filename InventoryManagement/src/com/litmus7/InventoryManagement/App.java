package com.litmus7.InventoryManagement;

import com.litmus7.controller.InventoryController;

public class App {
    public static void main(String[] args) {
        InventoryController controller = new InventoryController();

        System.out.println("Starting Phase 3 processing (thread pool)...");

        int processedCount = controller.triggerPhase3Processing();

        System.out.println("Phase 3 processing finished.");
        System.out.println("Total files submitted: " + processedCount);
    }
}
