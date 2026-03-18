package com.mikrolabs.DAO;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.mikrolabs.entities.User;

@RegisterBeanMapper(User.class)
public interface UserDAO extends BaseDAO<User, String>{

    

    // private User mapRow(ResultSet rs) throws SQLException {
    //     return new User(
    //         rs.getInt("id"),
    //         rs.getString("email"),
    //         rs.getString("password"),
    //         rs.getString("name"),
    //         rs.getString("role")
    //     );
    // }

    @SqlUpdate("INSERT INTO users (name, email, password, role) VALUES(:name, :email, :password, :role)")
    Boolean registerUser(
        @Bind("name") String name,
        @Bind("email") String email,
        @Bind("password") String password,
        @Bind("role") String role
    );


   




    // public Optional<User> searchUserByEmail(String email){
    //     String sql = "SELECT * FROM users WHERE email = ?";
    //     try (Connection conn = DatabaseManager.getDataSource().getConnection();
    //         PreparedStatement ps = conn.prepareStatement(sql)) {
                
    //         ps.setString(1, email);
            
    //         try (ResultSet rs = ps.executeQuery();) {
    //             if (rs.next()) {
    //                 return Optional.of(mapRow(rs));
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return Optional.empty();
    // }
    
}
