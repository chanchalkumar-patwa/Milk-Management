/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.reportGenerators;


import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import utility.DBConnection;  // Adjust this import if your DBConnection is in a different package

@WebServlet("/CollectionReportServlet")
public class CollectionReportServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String shift = request.getParameter("shift");
        String mobile = request.getParameter("mobile");

        List<Map<String, Object>> reportData = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            StringBuilder query = new StringBuilder(
                "SELECT f.name AS farmer_name, f.contact_number, c.entry_date, c.shift, " +
                "c.quantity_liters, c.fat_percentage, c.snf_percentage, c.rate_per_liter, c.total_amount " +
                "FROM collection c " +
                "JOIN farmer f ON c.farmer_id = f.farmer_id " +
                "WHERE c.entry_date BETWEEN ? AND ?"
            );

            // Add optional filters
            if (shift != null && !shift.isEmpty()) {
                query.append(" AND c.shift = ?");
            }
            if (mobile != null && !mobile.isEmpty()) {
                query.append(" AND f.contact_number = ?");
            }

            PreparedStatement ps = conn.prepareStatement(query.toString());
            ps.setString(1, startDate);
            ps.setString(2, endDate);

            int paramIndex = 3;
            if (shift != null && !shift.isEmpty()) {
                ps.setString(paramIndex++, shift);
            }
            if (mobile != null && !mobile.isEmpty()) {
                ps.setString(paramIndex++, mobile);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("farmer_name", rs.getString("farmer_name"));
                row.put("contact_number", rs.getString("contact_number"));
                row.put("entry_date", rs.getDate("entry_date"));
                row.put("shift", rs.getString("shift"));
                row.put("quantity_liters", rs.getDouble("quantity_liters"));
                row.put("fat_percentage", rs.getDouble("fat_percentage"));
                row.put("snf_percentage", rs.getDouble("snf_percentage"));
                row.put("rate_per_liter", rs.getDouble("rate_per_liter"));
                row.put("total_amount", rs.getDouble("total_amount"));
                reportData.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching report: " + e.getMessage());
        }

        request.setAttribute("reportData", reportData);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("shift", shift);
        request.setAttribute("mobile", mobile);

        // Forward back to JSP for display
        RequestDispatcher rd = request.getRequestDispatcher("reports.jsp");
        rd.forward(request, response);
    }
}
