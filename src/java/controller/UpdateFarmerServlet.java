package controller;

import dao.FarmerDAO;
import model.Farmer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateFarmerServlet
 */
@WebServlet("/UpdateFarmerServlet")  // ✅ Web annotation mapping
public class UpdateFarmerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FarmerDAO farmerDAO;

    @Override
    public void init() {
        farmerDAO = new FarmerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("farmerId"));
            String name = request.getParameter("name");
            String contact = request.getParameter("contactNumber");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            Farmer farmer = new Farmer(id, name, contact, email, address);
            boolean updated = farmerDAO.updateFarmer(farmer);

            if (updated) {
                response.sendRedirect("FarmerServlet?message=Farmer+updated+successfully");
            } else {
                response.sendRedirect("FarmerServlet?message=Failed+to+update+farmer");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("FarmerServlet?message=Error+while+updating+farmer");
        }
    }
}
