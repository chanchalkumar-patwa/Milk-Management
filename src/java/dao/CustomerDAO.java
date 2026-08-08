/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import utility.DBConnection;
/**
 *
 * @author chanc
 */
public class CustomerDAO {
    public boolean insertCustomer(Customer customer){
        String sql = "INSERT INTO customer (name, contact_number, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getContactNumber());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setContactNumber(rs.getString("contact_number"));
                customer.setAddress(rs.getString("address"));
                customer.setEmail(rs.getString("email"));
                customers.add(customer);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return customers;    
    }
    
    
    public List<Customer> searchCustomers(String keyword) {
    List<Customer> customers = new ArrayList<>();
    String sql = "SELECT * FROM customer WHERE name LIKE ? OR contact_number LIKE ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String searchValue = "%" + keyword + "%";
        ps.setString(1, searchValue);
        ps.setString(2, searchValue);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setName(rs.getString("name"));
                c.setContactNumber(rs.getString("contact_number"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                customers.add(c);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customers;
}

    
     public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        Customer c = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Customer();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setName(rs.getString("name"));
                    c.setContactNumber(rs.getString("contact_number"));
                    c.setEmail(rs.getString("email"));
                    c.setAddress(rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
     
     public int getCustomerCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total_customers FROM customer";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total_customers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
     
     public boolean updateCustomer(Customer c) {
        String sql = "UPDATE customer SET name=?, contact_number=?, address=?, email=? WHERE customer_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getContactNumber());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getEmail());
            ps.setInt(5, c.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
