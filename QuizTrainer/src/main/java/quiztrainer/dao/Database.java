package quiztrainer.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private String url;

    public Database(String url) throws SQLException, ClassNotFoundException {
        this.url = url;
        this.initDatabase();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * Initializes the database and creates User, QuizCard and Deck tables if they
     * don't exist yet.
     */
    
    public void initDatabase() throws SQLException {

        try (Connection connection = getConnection()) {
            PreparedStatement createUserTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS User (id INTEGER PRIMARY KEY, username VARCHAR(25), name VARCHAR(25));"
            );
            createUserTable.execute();
            createUserTable.close();

            PreparedStatement createQuizCardTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS QuizCard (id INTEGER PRIMARY KEY, user_id INTEGER, deck_id INTEGER, boxNumber INTEGER, question VARCHAR(40),"
                    + " rightAnswer VARCHAR(25), falseanswer1 VARCHAR(25), falseanswer2 VARCHAR(25), falseanswer3 VARCHAR(25), answeredRight INTEGER,"
                    + " totalAnswers INTEGER, FOREIGN KEY (user_id) REFERENCES User(id), FOREIGN KEY (deck_id) REFERENCES Deck(id));"
            );
            createQuizCardTable.execute();
            createQuizCardTable.close();
            
            PreparedStatement createDeckTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Deck (id INTEGER PRIMARY KEY, deckName VARCHAR(25), user_id INTEGER, FOREIGN KEY (user_id) REFERENCES User(id));"
            );
            createDeckTable.execute();
            createUserTable.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
