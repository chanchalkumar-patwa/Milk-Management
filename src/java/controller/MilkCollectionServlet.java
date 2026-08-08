/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.DBConnection;

/**
 *
 * @author chanc
 */
@WebServlet(name = "MilkCollectionServlet", urlPatterns = {"/MilkCollectionServlet"})
public class MilkCollectionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String message = null ;
        String status = "error";
        // --- Read form parameters safely ---
        String farmerIdStr = request.getParameter("farmer_id");
        String entryDate = request.getParameter("date");
        String shift = request.getParameter("shift");
        String quantityStr = request.getParameter("quantity");
        String fatStr = request.getParameter("fat");
        String snfStr = request.getParameter("snf");
        String rateStr = request.getParameter("rate");
        String totalStr = request.getParameter("total_amount");

        // --- Null or empty checks ---
        if(farmerIdStr == null || farmerIdStr.isEmpty() ||
           entryDate == null || entryDate.isEmpty() ||
           shift == null || shift.isEmpty() ||
           quantityStr == null || quantityStr.isEmpty() ||
           fatStr == null || fatStr.isEmpty() ||
           snfStr == null || snfStr.isEmpty() ||
           rateStr == null || rateStr.isEmpty() ||
           totalStr == null || totalStr.isEmpty()) {

            message = "Error: All fields are required!";
            request.setAttribute("message", message);
            request.setAttribute("status", status);
            request.getRequestDispatcher("milkEntry.jsp").forward(request, response);
            return;
        }

        try {
            int farmerId = Integer.parseInt(farmerIdStr);
            double quantity = Double.parseDouble(quantityStr);
            double fat = Double.parseDouble(fatStr);
            double snf = Double.parseDouble(snfStr);
            double rate = Double.parseDouble(rateStr);
            double total = Double.parseDouble(totalStr);

            String sql = "INSERT INTO collection (farmer_id, entry_date, shift, quantity_liters, fat_percentage, snf_percentage, rate_per_liter, total_amount) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, farmerId);
                ps.setString(2, entryDate);
                ps.setString(3, shift);
                ps.setDouble(4, quantity);
                ps.setDouble(5, fat);
                ps.setDouble(6, snf);
                ps.setDouble(7, rate);
                ps.setDouble(8, total);

                int i = ps.executeUpdate();
                if (i > 0) {
                    message = "Milk collection entry added successfully!";
                    status = "success";
                } else {
                    message = "<h3>Failed to add entry.</h3>";
                }
            }

        } catch (NumberFormatException nfe) {
            message = "Error: Invalid numeric value provided!";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        }
        
        request.setAttribute("message", message);
        request.setAttribute("status", status);
        response.sendRedirect("milkEntry.jsp");
    }
}