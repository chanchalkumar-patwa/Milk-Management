/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Distribution;
import utility.DBConnection;

/**
 *
 * @author chanc
 */
public class DistributionDAO {
    
    public List<Distribution> getTodaysEntries() {
    List<Distribution> list = new ArrayList<>();
    String sql = "SELECT COALESCE(c.name, d.customer_name) AS customer_name,COALESCE(c.contact_number, d.contact_number) AS contact_number, d.shift,d.quantity_liters,d.rate_per_liter,d.total_amount FROM distribution d LEFT JOIN customer c ON d.customer_id = c.customer_id WHERE DATE(d.distribution_date) = CURDATE() ORDER BY d.distribution_date DESC";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
                Distribution dist = new Distribution();
                dist.setCustomerName(rs.getString("customer_name"));
                dist.setContactNumber(rs.getString("contact_number"));
                dist.setShift(rs.getString("shift"));
                dist.setQuantity(rs.getDouble("quantity_liters"));
                dist.setRate(rs.getDouble("rate_per_liter"));
                dist.setTotal(rs.getDouble("total_amount"));
                list.add(dist);
            }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    
}
