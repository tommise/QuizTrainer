
package quiztrainer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quiztrainer.domain.Deck;

public class DbDeckDao implements DeckDao {
    private Database db;
    
    public DbDeckDao(Database db) {
        this.db = db;
    }
    
     /**
     * Stores a new Deck to the database.
     * 
     * @param deckName  The name of the new deck.
     * @param userId    Id of the user which made the deck.
     */
    
    @Override
    public void create(String deckName, int userId) throws Exception {
        
        try (Connection dbConnection = db.getConnection()) {
            PreparedStatement createNewUserStatement = dbConnection.prepareStatement("INSERT INTO Deck (deckName, user_id) VALUES (?, ?)");
            createNewUserStatement.setString(1, deckName);
            createNewUserStatement.setInt(2, userId);
            createNewUserStatement.executeUpdate();
            createNewUserStatement.close();
        }
    }
    
     /**
     * Fetches all deck from the database from the user with user id.
     * 
     * @param userId    Id of the user whose decks are to be searched.
     */
    
    @Override
    public List<Deck> getAllDecksByUserId(int userId) {

        ArrayList allDecks = new ArrayList();
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement getAllDecksByIdStatement = dbConnection.prepareStatement("SELECT * FROM Deck WHERE user_id = ?"); 
            getAllDecksByIdStatement.setInt(1, userId);
            ResultSet rs = getAllDecksByIdStatement.executeQuery();
        
            while (rs.next()) {
                int deckId = rs.getInt("id");
                Deck deck = new Deck(rs.getString("deckName"));
                deck.setDeckId(deckId);
                allDecks.add(deck);
            }       
        
            getAllDecksByIdStatement.close();
            rs.close();
            dbConnection.close();

            return allDecks;  
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
     /**
     * Searches and returns deck id based on deck name and user id.
     * As the database may hold decks with the same name, the query is specified with user id.
     * 
     * @param deckName  Name of the deck to be searched.    
     * @param userId    Id of the user whose deck is searched.
     */    

    @Override
    public int getDeckIdByNameAndUserId(String deckName, int userId) {
        int deckId;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findDeckStatement = dbConnection.prepareStatement("SELECT Deck.id FROM Deck"
                    + " JOIN User ON User.id = Deck.user_id WHERE deckName = ? AND user_id = ?");
            findDeckStatement.setString(1, deckName);
            findDeckStatement.setInt(2, userId);

            ResultSet rs = findDeckStatement.executeQuery();

            if (rs.next()) {
                deckId = rs.getInt("id");
            } else {
                return -1;
            }

            findDeckStatement.close();
            rs.close();
            dbConnection.close();  
            
            return deckId;
        } catch (Exception e) {
            return -1;
        }
    
    }
    
     /**
     * Searches and returns particular name based on deckName and user id.
     * 
     * @param deckName  Name of the deck to be searched.  
     * @param userId    Id of the user whose deck is searched.
     */  
    
    @Override
    public Deck findDeckByDeckName(String deckName, int userId) {
        Deck foundDeck;
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findDeckStatement = dbConnection.prepareStatement("SELECT * FROM Deck"
                    + " JOIN User ON User.id = Deck.user_id WHERE deckName = ? AND user_id = ?");
            findDeckStatement.setString(1, deckName);
            findDeckStatement.setInt(2, userId);

            ResultSet rs = findDeckStatement.executeQuery();

            if (rs.next()) {
                foundDeck = new Deck(rs.getString("deckName"));
            } else {
                return null;
            }

            findDeckStatement.close();
            rs.close();
            dbConnection.close();  
            
            return foundDeck;
            
        } catch (Exception e) {
            return null;
        }
    } 
}
