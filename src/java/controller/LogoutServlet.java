/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


/**
 *
 * @author chanc
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet{
     protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session = request.getSession(false); // don't create a new session
        if (session != null) {
            session.invalidate(); // destroy session
        }
        response.sendRedirect("index.jsp"); // redirect to login page
     }
}
