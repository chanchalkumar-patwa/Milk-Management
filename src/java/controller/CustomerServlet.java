/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;

/**
 *
 * @author chanc
 */

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO dao;
    
    public void init(){
        dao = new CustomerDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session1 = request.getSession(false);
        if (session1 == null || session1.getAttribute("user") == null) {
            response.sendRedirect("index.jsp"); 
            return;
        }
        
        String action = request.getParameter("action");
        String search = request.getParameter("search");
        
        try{
            if(action == null){
                action = "list" ;
            }
            switch(action){
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listCustomers(request, response, search);
                    break;
            }    
        } catch(Exception e){
            throw new ServletException(e);
        }
    }
    
    private void listCustomers(HttpServletRequest request, HttpServletResponse response, String search)
            throws ServletException, IOException {
        List<Customer> customers;

        if (search != null && !search.trim().isEmpty()) {
            customers = dao.searchCustomers(search.trim());
        } else {
            customers = dao.getAllCustomers();
        }

        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");
        dispatcher.forward(request, response);
    }
    
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer existingCustomer  = dao.getCustomerById(id);
        request.setAttribute("customer", existingCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("editCustomer.jsp");
        dispatcher.forward(request, response);
    }
    
    
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            updateCustomer(request, response);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("customerId"));
            String name = request.getParameter("name");
            String contact = request.getParameter("contactNumber");
            String address = request.getParameter("address");
            String email = request.getParameter("email");

            Customer c = new Customer(id, name, contact, address, email);
            boolean updated = dao.updateCustomer(c);

            if (updated) {
                response.sendRedirect("CustomerServlet?message=Customer+updated+successfully");
            } else {
                response.sendRedirect("CustomerServlet?message=Failed+to+update+customer");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerServlet?message=Error+while+updating+customer");
        }
    }
}
