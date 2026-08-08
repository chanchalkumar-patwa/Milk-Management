/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Farmer;
import utility.DBConnection;

/**
 *
 * @author chanchalKumar Patwa
 */
public class FarmerDAO {
    // ✅ INSERT new farmer
    public boolean insertFarmer(Farmer farmer) {
        String sql = "INSERT INTO farmer (name, contact_number, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, farmer.getName());
            ps.setString(2, farmer.getContactNumber());
            ps.setString(3, farmer.getEmail());
            ps.setString(4, farmer.getAddress());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ SELECT all farmers
    public List<Farmer> getAllFarmers() {
        List<Farmer> farmers = new ArrayList<>();
        String sql = "SELECT * FROM farmer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Farmer farmer = new Farmer();
                farmer.setFarmerId(rs.getInt("farmer_id"));
                farmer.setName(rs.getString("name"));
                farmer.setContactNumber(rs.getString("contact_number"));
                farmer.setEmail(rs.getString("Email"));
                farmer.setAddress(rs.getString("address"));
                farmers.add(farmer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmers;
    }

    // ✅ SELECT farmer by ID
    public Farmer getFarmerById(int id) {
        String sql = "SELECT * FROM farmer WHERE farmer_id = ?";
        Farmer farmer = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    farmer = new Farmer();
                    farmer.setFarmerId(rs.getInt("farmer_id"));
                    farmer.setName(rs.getString("name"));
                    farmer.setContactNumber(rs.getString("contact_number"));
                    farmer.setEmail(rs.getString("email"));
                    farmer.setAddress(rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmer;
    }

    // ✅ UPDATE farmer
    public boolean updateFarmer(Farmer farmer) {
        String sql = "UPDATE farmer SET name = ?, contact_number = ?, email = ?, address = ? WHERE farmer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, farmer.getName());
            ps.setString(2, farmer.getContactNumber());
            ps.setString(3, farmer.getEmail());
            ps.setString(4, farmer.getAddress());
            ps.setInt(5, farmer.getFarmerId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getFarmerCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total_farmers FROM farmer";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total_farmers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // ✅ SEARCH farmers by name or contact
    public List<Farmer> searchFarmers(String keyword) {
    List<Farmer> farmers = new ArrayList<>();
    String sql = "SELECT * FROM farmer WHERE name LIKE ? OR contact_number LIKE ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String searchValue = "%" + keyword + "%";
        ps.setString(1, searchValue);
        ps.setString(2, searchValue);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Farmer farmer = new Farmer();
                farmer.setFarmerId(rs.getInt("farmer_id"));
                farmer.setName(rs.getString("name"));
                farmer.setContactNumber(rs.getString("contact_number"));
                farmer.setEmail(rs.getString("email"));
                farmer.setAddress(rs.getString("address"));
                farmers.add(farmer);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return farmers;
}


    // ✅ DELETE farmer
    public boolean deleteFarmer(int id) {
        String sql = "DELETE FROM farmer WHERE farmer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
