/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

@WebServlet("/MilkDistributionServlet")
public class MilkDistributionServlet extends HttpServlet{
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        String message = null ;
        String status = "error";
        // --- Read form parameters safely ---
        String customerIdStr = request.getParameter("customer_id");
        String cName = request.getParameter("cName");
        String cNumber = request.getParameter("cNumber");
        String entryDate = request.getParameter("date");
        String shift = request.getParameter("shift");
        String quantityStr = request.getParameter("quantity");
        String rateStr = request.getParameter("rate");
        String totalStr = request.getParameter("total_amount");
        
        
        if(isRegisteredCustomer(customerIdStr, cName, cName)){
           if(customerIdStr == null || customerIdStr.isEmpty() ||
           entryDate == null || entryDate.isEmpty() ||
           shift == null || shift.isEmpty() ||
           quantityStr == null || quantityStr.isEmpty() ||
           rateStr == null || rateStr.isEmpty() ||
           totalStr == null || totalStr.isEmpty()) {
                message = "Error: Input Field acoordinglymmmmmmmmmmmmmmmmmmmm";
                request.setAttribute("message", message);
                request.setAttribute("status", status);
                request.getRequestDispatcher("soldMilkEntry.jsp").forward(request, response);
                return;
            }
        }else{
            if(cName==null || cName.isEmpty()  || 
            cNumber == null || cNumber.isEmpty() ||
            entryDate == null || entryDate.isEmpty() ||
            shift == null || shift.isEmpty() ||
            quantityStr == null || quantityStr.isEmpty() ||
            rateStr == null || rateStr.isEmpty() ||
            totalStr == null || totalStr.isEmpty()) {
                message = "Error: Input Field acoordingly";
                request.setAttribute("message", message);
                request.setAttribute("status", status);
                request.getRequestDispatcher("soldMilkEntry.jsp").forward(request, response);
                return;
            }
        }
        
        int i = 0;
        double quantity = Double.parseDouble(quantityStr);
        double rate = Double.parseDouble(rateStr);
        double total = Double.parseDouble(totalStr);
        
        if(isRegisteredCustomer(customerIdStr, cName, cNumber)){
            int customerId = Integer.parseInt(customerIdStr);
            i = entryForRegisteredCustomer(customerId,entryDate,shift,quantity,rate,total);
        }else{
            i= entryForUnregisteredCustomer(cName,cNumber,entryDate,shift,quantity,rate,total);
        }
        
        if (i > 0) {
            message = "Milk collection entry added successfully!";
            status = "success";
        } else {
            message = "<h3>Failed to add entry.</h3>";
        }
        
        request.setAttribute("message", message);
        request.setAttribute("status", status);
        response.sendRedirect("soldMilkEntry.jsp");
        
    }

    
    private boolean isRegisteredCustomer(String id,String name,String num){
        if(id==null || id.isEmpty()){
            return false;
        }
        return true;
    }

    private int entryForRegisteredCustomer(int customerId, String entryDate, String shift, double quantity, double rate, double total) {
        String sql = "INSERT INTO distribution (customer_id,distribution_date,shift,quantity_liters,rate_per_liter,total_amount) VALUES (?,?,?,?,?,?)";
        int result = 0;
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, entryDate);
            ps.setString(3, shift);
            ps.setDouble(4, quantity);
            ps.setDouble(5, rate);
            ps.setDouble(6, total);
            result = ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private int entryForUnregisteredCustomer(String cName, String cNumber, String entryDate, String shift, double quantity, double rate, double total) {
        int result = 0;
        String sql = "INSERT INTO distribution (customer_name,contact_number,distribution_date,shift,quantity_liters,rate_per_liter,total_amount) VALUES (?,?,?,?,?,?,?);";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cName);
            ps.setString(2, cNumber);
            ps.setString(3, entryDate);
            ps.setString(4, shift);
            ps.setDouble(5, quantity);
            ps.setDouble(6, rate);
            ps.setDouble(7, total);
            result = ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
     
}
