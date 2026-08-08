/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;


import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/dailycollection")
public class DailyCollectionBarChart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        JSONArray dateLabels = new JSONArray();
        JSONArray totalCollection = new JSONArray();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT entry_date, SUM(quantity_liters) AS total_quantity " +
                 "FROM collection " +
                 "GROUP BY entry_date " +
                 "ORDER BY entry_date DESC LIMIT 7"); // last 7 days
             ResultSet rs = ps.executeQuery()) {

            // Reverse order to show oldest → newest
            Stack<String> dates = new Stack<>();
            Stack<Double> totals = new Stack<>();

            while (rs.next()) {
                dates.push(rs.getString("entry_date"));
                totals.push(rs.getDouble("total_quantity"));
            }

            while (!dates.isEmpty()) {
                dateLabels.put(dates.pop());
                totalCollection.put(totals.pop());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
               
        JSONObject json = new JSONObject();
        json.put("dates", dateLabels);
        json.put("quantities", totalCollection);

        response.getWriter().write(json.toString());
    }
}
