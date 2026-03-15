package com.mikrolabs.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.mikrolabs.DatabaseManager;
import com.mikrolabs.entities.User;

public class UserDAO implements BaseDAO<User, String>{

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("name"),
            rs.getString("role")
        );
    }


    public int register(User user){
        return 0;
    }


    public Optional<User> searchUserByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseManager.getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    
}
