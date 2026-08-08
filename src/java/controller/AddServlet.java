/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import dao.CustomerDAO;
import dao.FarmerDAO;
import model.Farmer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.regex.Pattern;
import model.Customer;

@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get form parameters
        String name = request.getParameter("name").trim();
        String contactNumber = request.getParameter("contact_number").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String type = request.getParameter("type").trim();

        StringBuilder errorMsg = new StringBuilder();

        // 2. Validate Name
        if (name.isEmpty() || !Pattern.matches("[a-zA-Z\\s]+", name)) {
            errorMsg.append("Name must contain only letters and spaces.<br>");
        }

        // 3. Validate Contact Number (digits only, 10 digits)
        if (contactNumber.isEmpty() || !Pattern.matches("\\d{10}", contactNumber)) {
            errorMsg.append("Contact number must be 10 digits.<br>");
        }

        // 4. Validate Email (basic pattern)
        if (email.isEmpty() || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
            errorMsg.append("Invalid email format.<br>");
        }

        // 5. Validate Address
        if (address.isEmpty()) {
            errorMsg.append("Address cannot be empty.<br>");
        }

        // 6. Validate Type
        if (!(type.equalsIgnoreCase("supplier") || type.equalsIgnoreCase("customer"))) {
            errorMsg.append("Invalid type selected.<br>");
        }

        // 7. If errors exist, forward back with error message
        if (errorMsg.length() > 0) {
            request.setAttribute("message", errorMsg.toString());
            if ("supplier".equalsIgnoreCase(type)) {
                request.getRequestDispatcher("customers.jsp").forward(request, response);
            }else if("customer".equalsIgnoreCase(type)){
                request.getRequestDispatcher("customer.jsp").forward(request, response);
            }
            return;
        }

        // 8. Insert based on type
        boolean success = false;

        if ("supplier".equalsIgnoreCase(type)) {
            Farmer farmer = new Farmer();
            farmer.setName(name);
            farmer.setContactNumber(contactNumber);
            farmer.setAddress(address);
            farmer.setEmail(email);
            FarmerDAO dao = new FarmerDAO();
            success = dao.insertFarmer(farmer);
            
        }else if("customer".equalsIgnoreCase(type)){
            Customer customer = new Customer();
            customer.setName(name);
            customer.setContactNumber(contactNumber);
            customer.setEmail(email);
            customer.setAddress(address);
            CustomerDAO dao = new CustomerDAO();
            success = dao.insertCustomer(customer);
        }
        
        // 9. Set success or failure message
        if (success) {
            request.setAttribute("message", type + " added successfully!");
        } else {
            request.setAttribute("message", "Error adding " + type + ". Try again.");
        }
        if ("supplier".equalsIgnoreCase(type)) {
            response.sendRedirect("/DairyManagement/FarmerServlet");
        }else if("customer".equalsIgnoreCase(type)) {
            response.sendRedirect("/DairyManagement/CustomerServlet");
        }
    }
}
