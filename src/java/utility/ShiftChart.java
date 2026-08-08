package utility;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.jfree.data.json.impl.JSONObject;
//import org.json.JSONObject;

@WebServlet("/api/shiftdata")
public class ShiftChart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        JSONObject json = new JSONObject();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "SELECT shift, SUM(quantity_liters) AS total_quantity FROM collection WHERE entry_date >= CURDATE() - INTERVAL 7 DAY GROUP BY shift");
             ResultSet rs = ps.executeQuery()) {

            double morning = 0, evening = 0;

            while (rs.next()) {
                String shift = rs.getString("shift");
                double total = rs.getDouble("total_quantity");
                if ("Morning".equalsIgnoreCase(shift)) morning = total;
                else if ("Evening".equalsIgnoreCase(shift)) evening = total;
            }

            json.put("morning", morning);
            json.put("evening", evening);

        } catch (SQLException e) {
            e.printStackTrace();
            json.put("error", e.getMessage());
        }

        response.getWriter().write(json.toString());
    }
}
