package quiztrainer.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import quiztrainer.domain.User;

public class FileUserDao implements UserDao {
    
    private Database db;
    
    public FileUserDao(Database db) {
        this.db = db;
    }
    
    @Override
    public User create(User user) throws Exception, SQLException {
        String username = user.getUsername();
        String name = user.getName();
        
        Connection dbConnection = db.getConnection();
        PreparedStatement createNewUserStatement = dbConnection.prepareStatement("INSERT INTO User (username, name) VALUES (?, ?)");
        createNewUserStatement.setString(1, username); 
        createNewUserStatement.setString(2, name);
        createNewUserStatement.executeUpdate();        
        createNewUserStatement.close();
        
        dbConnection.close();
        
        User newUser = findByUsername(username);
        
        return newUser;
    }   
    
    @Override
    public User findByUsername(String username) {
        User foundUser;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findUserStatement = dbConnection.prepareStatement("SELECT username, name FROM User WHERE username = ?");
            findUserStatement.setString(1, username);

            ResultSet rs = findUserStatement.executeQuery();

            if (rs.next()) {
                foundUser = new User(rs.getString("username"), rs.getString("name"));
            } else {
                return null;
            }

            findUserStatement.close();
            rs.close();
            dbConnection.close();  
            
            return foundUser;
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
      
    @Override
    public List<User> getAll() {
        ArrayList allUsers = new ArrayList();
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement getAllUsersStatement = dbConnection.prepareStatement("SELECT * FROM User");       
            ResultSet rs = getAllUsersStatement.executeQuery();
        
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("name"));
                allUsers.add(user);
            }       
        
            getAllUsersStatement.close();
            rs.close();
            dbConnection.close();

            return allUsers;  
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }

}