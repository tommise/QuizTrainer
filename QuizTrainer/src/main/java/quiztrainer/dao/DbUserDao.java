package quiztrainer.dao;

import java.sql.*;
import quiztrainer.domain.User;

public class DbUserDao implements UserDao {
    
    private Database db;
    
    public DbUserDao(Database db) {
        this.db = db;
    }
    
     /**
     * Creates a new user in to the database.
     * 
     * @param user  user object to be added in to the database.
     * @return created user object.
     */
    
    @Override
    public User create(User user) {
        String username = user.getUsername();
        String name = user.getName();
        
        try (Connection dbConnection = db.getConnection(); 
                PreparedStatement createNewUserStatement = dbConnection.prepareStatement(
                        "INSERT INTO User (username, name) VALUES (?, ?)")) {
            
            createNewUserStatement.setString(1, username);
            createNewUserStatement.setString(2, name);
            createNewUserStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        User newUser = findByUsername(username);
        
        return newUser;
    }   
    
     /**
     * Searches and returns an id of the user
     * based on username.
     * 
     * @param username  username as a String to be searched.
     * @return id of the user as an integer.
     */
    
    @Override
    public int getIdByUsername(String username) {
        int userId = -1;
        
        try (Connection dbConnection = db.getConnection(); 
                PreparedStatement findUserIdStatement = dbConnection.prepareStatement(
                        "SELECT id FROM User WHERE username = ?")) {
            
            findUserIdStatement.setString(1, username);

            ResultSet rs = findUserIdStatement.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            } else {
                return -1;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return userId;
    }
    
     /**
     * Searches and returns an User object
     * based on given username.
     * 
     * @param username  username as a String to be searched.
     * @return found User object if successful.
     */
    
    @Override
    public User findByUsername(String username) {
        User foundUser = null;
        
        try (Connection dbConnection = db.getConnection(); 
                PreparedStatement findUserStatement = dbConnection.prepareStatement(
                        "SELECT username, name FROM User WHERE username = ?")) {
            
            findUserStatement.setString(1, username);

            ResultSet rs = findUserStatement.executeQuery();

            if (rs.next()) {
                foundUser = new User(rs.getString("username"), rs.getString("name"));
            } else {
                return null;
            }

            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return foundUser;
    }
}