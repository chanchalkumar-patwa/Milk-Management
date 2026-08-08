/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.reportGenerators;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utility.DBConnection;

@WebServlet("/DownloadCollectionExcelServlet")
public class DownloadCollectionExcelServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String shift = request.getParameter("shift");
        String mobile = request.getParameter("mobile");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=MilkCollectionReport.xlsx");

        try (Connection con = DBConnection.getConnection();
             XSSFWorkbook workbook = new XSSFWorkbook();
             OutputStream out = response.getOutputStream()) {

            // Create Sheet
            Sheet sheet = workbook.createSheet("Milk Collection Report");
            Row header = sheet.createRow(0);
            String[] columns = {"Farmer Name", "Mobile", "Entry Date", "Shift", "Quantity (L)", "Fat (%)", "SNF (%)", "Rate/Liter", "Total Amount"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Build SQL Query
            StringBuilder sql = new StringBuilder(
                "SELECT f.name AS farmer_name, f.contact_number, c.entry_date, c.shift, c.quantity_liters, " +
                "c.fat_percentage, c.snf_percentage, c.rate_per_liter, c.total_amount " +
                "FROM collection c JOIN farmer f ON c.farmer_id = f.farmer_id WHERE c.entry_date BETWEEN ? AND ?"
            );

            if (shift != null && !shift.isEmpty()) {
                sql.append(" AND c.shift = ?");
            }
            if (mobile != null && !mobile.isEmpty()) {
                sql.append(" AND f.contact_number = ?");
            }

            try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                ps.setString(1, startDate);
                ps.setString(2, endDate);

                int paramIndex = 3;
                if (shift != null && !shift.isEmpty()) {
                    ps.setString(paramIndex++, shift);
                }
                if (mobile != null && !mobile.isEmpty()) {
                    ps.setString(paramIndex, mobile);
                }

                ResultSet rs = ps.executeQuery();

                int rowIndex = 1;
                while (rs.next()) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(rs.getString("farmer_name"));
                    row.createCell(1).setCellValue(rs.getString("contact_number"));
                    row.createCell(2).setCellValue(rs.getString("entry_date"));
                    row.createCell(3).setCellValue(rs.getString("shift"));
                    row.createCell(4).setCellValue(rs.getDouble("quantity_liters"));
                    row.createCell(5).setCellValue(rs.getDouble("fat_percentage"));
                    row.createCell(6).setCellValue(rs.getDouble("snf_percentage"));
                    row.createCell(7).setCellValue(rs.getDouble("rate_per_liter"));
                    row.createCell(8).setCellValue(rs.getDouble("total_amount"));
                }
                   

                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h3 style='color:red;'>Error generating Excel file: " + e.getMessage() + "</h3>");
        }
    }
}
