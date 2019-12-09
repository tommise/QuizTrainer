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
    
     /**
     * Creates a new user in to the database.
     * 
     * @param user  user object to be added in to the database.
     * @return created user object.
     */
    
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
    
     /**
     * Searches and returns an id of the user
     * based on username.
     * 
     * @param username  username as a String to be searched.
     * @return id of the user as an integer.
     */
    
    @Override
    public int getIdByUsername(String username) {
        int userId;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findUserIdStatement = dbConnection.prepareStatement("SELECT id FROM User WHERE username = ?");
            findUserIdStatement.setString(1, username);

            ResultSet rs = findUserIdStatement.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            } else {
                return -1;
            }

            findUserIdStatement.close();
            rs.close();
            dbConnection.close();  
            
            return userId;
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return -1;
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
    
     /**
     * Searches and returns all User objects
     * in the database.
     * 
     * @return all found users in an ArrayList
     */
    
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