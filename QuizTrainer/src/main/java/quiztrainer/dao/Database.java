
package quiztrainer.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private String url;

    public Database(String url) throws ClassNotFoundException {
        this.url = url;
        initDatabase();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
    
    public void initDatabase() {
        try {
            Connection connection = getConnection();
            PreparedStatement createUserTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS User (id INTEGER PRIMARY KEY, username VARCHAR(25), name VARCHAR(25));"
            );
            createUserTable.execute();
            createUserTable.close();

            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
