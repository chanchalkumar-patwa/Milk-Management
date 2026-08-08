/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utility.DBConnection;
import utility.PasswordUtil;

/*
 * @author chanchalkumar Patwa
 */
public class AdminLoginDAO {
    public static boolean validateAdmin(String userName,String password) throws SQLException{
        boolean adminFound = false;
        Connection con = DBConnection.getConnection();
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        String query = "select * from admin where username=? AND password=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, hashedPassword);
        ResultSet rs = ps.executeQuery();
        adminFound = rs.next();
        return adminFound;
    }
}
