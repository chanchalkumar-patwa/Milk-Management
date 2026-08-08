/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Collection;
import utility.DBConnection;

public class CollectionDAO {

    // Fetch today's entries
    public List<Collection> getTodaysEntries() {
    List<Collection> list = new ArrayList<>();
    String sql = "SELECT c.entry_id, f.name AS farmer_name, c.shift, c.quantity_liters, c.fat_percentage, "
               + "c.snf_percentage, c.rate_per_liter, c.total_amount, c.entry_date "
               + "FROM collection c JOIN farmer f ON c.farmer_id = f.farmer_id "
               + "WHERE DATE(c.entry_date) = CURDATE()";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Collection col = new Collection();
            col.setEntryId(rs.getInt("entry_id"));
            col.setFarmerName(rs.getString("farmer_name"));
            col.setShift(rs.getString("shift"));
            col.setQuantityLiters(rs.getDouble("quantity_liters"));
            col.setFatPercentage(rs.getDouble("fat_percentage"));
            col.setSnfPercentage(rs.getDouble("snf_percentage"));
            col.setRatePerLiter(rs.getDouble("rate_per_liter"));
            col.setTotalAmount(rs.getDouble("total_amount"));
            list.add(col);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    
    
    public double getAVGFat() {
        double count = 0;
        String sql = "SELECT + SUM(fat_percentage * quantity_liters) / SUM(quantity_liters) AS average_fat FROM collection WHERE DATE(entry_date) = CURDATE()";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getDouble("average_fat");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int getTodaysCollection() {
        int count = 0;
        String sql = "SELECT SUM(quantity_liters) AS quantity FROM collection WHERE DATE(entry_date) = CURDATE()";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
