package com.litmus7.controller;

import java.io.File;
import java.util.concurrent.*;


import com.litmus7.services.InventoryService;

public class InventoryController {

    private final InventoryService service = new InventoryService();

    
    public int triggerPhase3Processing() {
        File[] files = service.getCsvFiles().getData();
        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in input folder.");
            return 0;
        }

        int processedCount = 0;;

        
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (File file : files) {
            executor.submit(() -> {
                boolean result = service.processSingleFile(file).getData();
            });
            processedCount++;
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        

        return processedCount;
    }
}
