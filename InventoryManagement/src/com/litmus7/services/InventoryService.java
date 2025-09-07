package com.litmus7.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.dao.InventoryDAO;
import com.litmus7.dto.Inventory;
import com.litmus7.util.DatabaseConnectionUtil;
import com.litmus7.util.Response;
import com.litmus7.constants.FilePath;

public class InventoryService {

	
	
    private final InventoryDAO dao = new InventoryDAO();

    
    public Response<File[],?,?> getCsvFiles() {
        File inputDir = new File(FilePath.INPUT_FOLDER);
        if (!inputDir.exists() || !inputDir.isDirectory()) {
        	Response <File[],Boolean,?> response= new Response<>(null,null,null);
            return response;
        }
        Response <File[],Boolean,?> response= new Response<>(inputDir.listFiles((dir, name) -> name.endsWith(".csv")),null,null);
        return response;
    }
    

    
    public Response<Boolean,?,?> processSingleFile(File csvFile) {
        Connection conn = null;
        boolean success = false;

        try {

            List<Inventory> items = parseFileToInventoryList(csvFile).getData();
            if (items.isEmpty()) {

                moveFile(csvFile, FilePath.ERROR_FOLDER);
                Response<Boolean,?,?> response=new Response<>(false,null,null);
                return response;
            }


            conn = DatabaseConnectionUtil.getConnection();
            conn.setAutoCommit(false);

            
            dao.insertBatch(items, conn);

            
            conn.commit();
            success = true;
            moveFile(csvFile, FilePath.PROCESSED_FOLDER);

        } catch (Exception e) {
        	
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            moveFile(csvFile, FilePath.ERROR_FOLDER);
            success = false;

        } finally 
        {
            
        }
        
        Response<Boolean,?,?> response=new Response<>(success,null,null);
        return response;
    }



    private Response<List<Inventory>,?,?> parseFileToInventoryList(File csvFile) throws IOException {
        List<Inventory> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 4) {
                    throw new IOException("Invalid format in file: " + csvFile.getName());
                }

                int sku = Integer.parseInt(parts[0].trim());
                String productName = parts[1].trim();
                int quantity = Integer.parseInt(parts[2].trim());
                float price=Float.parseFloat(parts[3].trim());

                items.add(new Inventory(sku, productName, quantity, price));
            }
        }
        Response<List<Inventory>,?,?> response=new Response<>(items,null,null);
        return response;
    }

    
    private void moveFile(File file, String targetFolder) {
        try {
            Path targetPath = Paths.get(targetFolder, file.getName());
            Files.createDirectories(targetPath.getParent());
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
