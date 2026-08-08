/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.FarmerDAO;
import model.Farmer;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FarmerServlet")
public class FarmerServlet extends HttpServlet {
    private FarmerDAO farmerDAO;
    
   
    @Override
    public void init() {
        farmerDAO = new FarmerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "delete":
                    deleteFarmer(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listFarmers(request, response, search);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listFarmers(HttpServletRequest request, HttpServletResponse response, String search)
            throws ServletException, IOException {
        List<Farmer> farmers;

        if (search != null && !search.trim().isEmpty()) {
            farmers = farmerDAO.searchFarmers(search.trim());
        } else {
            farmers = farmerDAO.getAllFarmers();
        }

        request.setAttribute("farmers", farmers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customers.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteFarmer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        farmerDAO.deleteFarmer(id);
        response.sendRedirect("FarmerServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Farmer existingFarmer = farmerDAO.getFarmerById(id);
        request.setAttribute("farmer", existingFarmer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("editFarmer.jsp");
        dispatcher.forward(request, response);
    }
}

