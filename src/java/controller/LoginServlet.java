/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author chanc
 */
import dao.AdminLoginDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        String userName = request.getParameter("uName");
        String password = request.getParameter("pass");
        try {
            boolean isValid = AdminLoginDAO.validateAdmin(userName, password);
            if(isValid){
                HttpSession session = request.getSession();
                session.setAttribute("user",userName);
                response.sendRedirect("dash.jsp");
            }else {
                request.setAttribute("error", "Invalid credentials!");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
