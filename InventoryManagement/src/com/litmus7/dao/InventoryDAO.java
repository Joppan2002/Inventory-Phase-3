package com.litmus7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.litmus7.dto.Inventory;
import com.litmus7.exceptions.InventoryDAOException;
import com.litmus7.constants.errorCode;


public class InventoryDAO {

    private static final String INSERT_SQL =
        "INSERT INTO inventory (SKU, ProductName, Quantity, Price) VALUES (?, ?, ?, ?)";



    public static void insert(Inventory item, Connection conn) throws InventoryDAOException
    {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, item.getSKU());
            ps.setString(2, item.getProductName());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());
            ps.executeUpdate();
        }
        catch(Exception e)
        {
        	throw new InventoryDAOException("ErrorCode.DB",errorCode.DB,e);
        }
    }

    
    public void insertBatch(List<Inventory> items, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            for (Inventory item : items) {
                ps.setInt(1, item.getSKU());
                ps.setString(2, item.getProductName());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
