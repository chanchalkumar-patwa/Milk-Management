/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.reportGenerators;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "SupplierReportServlet", urlPatterns = {"/SupplierReportServlet"})
public class SupplierReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String mobile = request.getParameter("mobile");

        List<Map<String, Object>> reportData = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            StringBuilder query = new StringBuilder("select ");

            // Add optional filters
//            if (mobile != null && !mobile.isEmpty()) {
//                query.append(" AND f.contact_number = ?");
//            }

            PreparedStatement ps = conn.prepareStatement(query.toString());
            ps.setString(1, startDate);
            ps.setString(2, endDate);

//            int paramIndex = 3;
//            if (shift != null && !shift.isEmpty()) {
//                ps.setString(paramIndex++, shift);
//            }
//            if (mobile != null && !mobile.isEmpty()) {
//                ps.setString(paramIndex++, mobile);
//            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("name", rs.getString("customer_name"));
                row.put("contact_number", rs.getString("contact_number"));
                row.put("date", rs.getDate("distribution_date"));
                row.put("shift", rs.getString("shift"));
                row.put("quantity_liters", rs.getDouble("quantity_liters"));
                row.put("rate_per_liter", rs.getDouble("rate_per_liter"));
                row.put("total_amount", rs.getDouble("total_amount"));
                reportData.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching report: " + e.getMessage());
        }

        request.setAttribute("reportData2", reportData);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("mobile", mobile);

        // Forward back to JSP for display
        RequestDispatcher rd = request.getRequestDispatcher("reports.jsp");
        rd.forward(request, response);
    }
}
