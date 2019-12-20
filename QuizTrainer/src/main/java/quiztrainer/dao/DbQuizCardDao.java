
package quiztrainer.dao;

import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import quiztrainer.domain.QuizCard;

public class DbQuizCardDao implements QuizCardDao {
    private Database db;
    
    public DbQuizCardDao(Database db) {
        this.db = db;
    }
    
     /**
     * Stores a new QuizCard to the database.
     * 
     * @param quizCard  A QuizCard object which was made by the user.
     * @param userId    Current user id who made the QuizCard.
     * @param deckId    Id of the deck where the QuizCard object is to be added.
     * @return created QuizCard object
     */
    
    @Override
    public QuizCard create(QuizCard quizCard, int userId, int deckId) throws Exception, SQLException {
        ArrayList<String> falseAnswers = quizCard.getFalseAnswers();  
        
        try (Connection dbConnection = db.getConnection()) {
            PreparedStatement createNewQuizCardStatement = dbConnection.prepareStatement("INSERT INTO QuizCard"
                    + " (user_id, deck_id, boxNumber, question, rightAnswer, falseAnswer1, falseAnswer2, falseAnswer3, answeredRight, totalAnswers)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            createNewQuizCardStatement.setInt(1, userId);
            createNewQuizCardStatement.setInt(2, deckId);
            createNewQuizCardStatement.setInt(3, 1);
            createNewQuizCardStatement.setString(4, quizCard.getQuestion());
            createNewQuizCardStatement.setString(5, quizCard.getCorrectAnswer());
            createNewQuizCardStatement.setString(6, falseAnswers.get(0));
            createNewQuizCardStatement.setString(7, falseAnswers.get(1));
            createNewQuizCardStatement.setString(8, falseAnswers.get(2));
            createNewQuizCardStatement.setInt(9, 0);
            createNewQuizCardStatement.setInt(10, 0);
            
            createNewQuizCardStatement.executeUpdate();
            createNewQuizCardStatement.close();
        }
        
        QuizCard newQuizCard = findByQuestion(quizCard.getQuestion(), userId);
        
        return newQuizCard;
    }   
    
     /**
     * Removes a quizCard from the database.
     * 
     * @param quizCardId Id of the QuizCard to be removed from database.
     */
    
    @Override
    public void delete(int quizCardId) {
        
        try (Connection dbConnection = db.getConnection()) {
            PreparedStatement createNewQuizCardStatement = dbConnection.prepareStatement("DELETE FROM QuizCard WHERE QuizCard.id = ?");
            createNewQuizCardStatement.setInt(1, quizCardId);
            createNewQuizCardStatement.executeUpdate();
            createNewQuizCardStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DbQuizCardDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     /**
     * Searches and returns an QuizCard object based on given question.
     * The database may hold multiple QuizCards with same question,
     * hence the query will be pointed to only current user.
     * 
     * @param findWithQuestion  Question as a String to be searched.
     * @param userId    Id of the current user.
     * @return found QuizCard object if successful.
     */
    
    @Override
    public QuizCard findByQuestion(String findWithQuestion, int userId) {
        QuizCard foundQuizCard = null;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardStatement = dbConnection.prepareStatement("SELECT * FROM QuizCard"
                    + " WHERE question = ? AND user_id = ?");
            findQuizCardStatement.setString(1, findWithQuestion);
            findQuizCardStatement.setInt(2, userId);       

            ResultSet rs = findQuizCardStatement.executeQuery();

            if (rs.next()) {
                foundQuizCard = getQuizCardFromResulSet(rs);
            }

            findQuizCardStatement.close();
            rs.close();
            dbConnection.close();  
            
            return foundQuizCard;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    
     /**
     * Searches and returns all QuizCard objects in the database.
     * 
     * @param currentUserId Current user id to be used in the SQL query.
     * @return all found QuizCards in an ArrayList which were made by the current user.
     */
    
    @Override
    public List<QuizCard> getAllQuizCards(int currentUserId) {
        ArrayList<QuizCard> allQuizCards = new ArrayList<>();
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement getAllQuizCardsStatement = dbConnection.prepareStatement("SELECT * FROM QuizCard WHERE user_id = ?");
            getAllQuizCardsStatement.setInt(1, currentUserId);
            
            ResultSet rs = getAllQuizCardsStatement.executeQuery();
        
            while (rs.next()) {
                QuizCard quizCard = getQuizCardFromResulSet(rs);
                allQuizCards.add(quizCard);
            }       
        
            getAllQuizCardsStatement.close();
            rs.close();
            dbConnection.close();

            return allQuizCards;  
            
        } catch (Exception e) {
            return null;
        }
    }    
    
     /**
     * Return all quizCards from the Deck based on deck id.
     * 
     * @param deckId    Id of the deck to be searched from.
     * @return QuizCards in ArrayList if successful.
     */
    
    @Override
    public List<QuizCard> getAllQuizCardsByDeckId(int deckId) {
        ArrayList<QuizCard> allQuizCards = new ArrayList<>();
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement getAllQuizCardsStatement = dbConnection.prepareStatement(
                    "SELECT * FROM QuizCard WHERE Deck_id = ?");
            getAllQuizCardsStatement.setInt(1, deckId);           
            
            ResultSet rs = getAllQuizCardsStatement.executeQuery();
        
            while (rs.next()) {
                QuizCard quizCard = getQuizCardFromResulSet(rs);
                allQuizCards.add(quizCard);
            }       
            
            getAllQuizCardsStatement.close();
            rs.close();
            dbConnection.close();

            return allQuizCards;  
            
        } catch (Exception e) {
            return null;
        }
    }    
    
     /**
     * Updates the box number of given QuizCard in the database.
     * 
     * @param quizCardId  An id of the QuizCard used in the SQL query.
     * @param boxNum    A box number where the QuizCard will be moved to.
     */
    
    @Override
    public void setBox(int quizCardId, int boxNum) {

        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardStatement = dbConnection.prepareStatement("UPDATE QuizCard SET boxNumber = ? WHERE id = ?");
            findQuizCardStatement.setInt(1, boxNum);
            findQuizCardStatement.setInt(2, quizCardId);

            findQuizCardStatement.executeUpdate();

            findQuizCardStatement.close();
            dbConnection.close();  

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     /**
     * Searches and returns an id of the QuizCard object
     * based on a given question.The database may hold multiple QuizCards with same question, 
     * hence the query will be pointed to only current user.
     * 
     * @param question  Question as a String to be searched.
     * @param userId    Current user id to be searched.
     * @return found QuizCard id as an int if successful.
     */
    
    @Override
    public int getIdByQuestion(String question, int userId) {
        int quizCardId;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardIdStatement = dbConnection.prepareStatement("SELECT id FROM QuizCard "
                    + "WHERE question = ? AND user_id = ?");
            findQuizCardIdStatement.setString(1, question);
            findQuizCardIdStatement.setInt(2, userId);

            ResultSet rs = findQuizCardIdStatement.executeQuery();

            if (rs.next()) {
                quizCardId = rs.getInt("id");
            } else {
                return -1;
            }

            findQuizCardIdStatement.close();
            rs.close();
            dbConnection.close();  
            
            return quizCardId;
            
        } catch (Exception e) {
            return -1;
        }
    }
    
     /**
     * Updates the amount of rehearsed for particular QuizCard with QuizCard id.
     * 
     * @param quizCardId    Id of the QuizCard to be updated.
     * @param amount    Amount to be updated as a new value for how many times the card has been rehearsed.
     */
    
    @Override
    public void setAmountRehearsed(int quizCardId, int amount) {
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardStatement = dbConnection.prepareStatement("UPDATE QuizCard SET totalAnswers = ? WHERE id = ?");
            findQuizCardStatement.setInt(1, amount);
            findQuizCardStatement.setInt(2, quizCardId);

            findQuizCardStatement.executeUpdate();

            findQuizCardStatement.close();
            dbConnection.close();  

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     /**
     * Updates the amount of right answers for particular QuizCard with QuizCard id.
     * 
     * @param quizCardId    Id of the QuizCard to be updated.
     * @param amount    Amount to be updated as a new value for how many times the card has been answered right.
     */
    
    @Override
    public void setAmountAnsweredRight(int quizCardId, int amount) {
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardStatement = dbConnection.prepareStatement("UPDATE QuizCard SET answeredRight = ? WHERE id = ?");
            findQuizCardStatement.setInt(1, amount);
            findQuizCardStatement.setInt(2, quizCardId);

            findQuizCardStatement.executeUpdate();

            findQuizCardStatement.close();
            dbConnection.close();  

        } catch (Exception e) {
            System.out.println(e);
        }
    }
     
    public QuizCard getQuizCardFromResulSet(ResultSet rs) throws SQLException {
                
        int boxNumber = rs.getInt("boxNumber");                
        String question = rs.getString("question");
        String rightAnswer = rs.getString("rightAnswer");
        String falseAnswer1 = rs.getString("falseAnswer1");
        String falseAnswer2 = rs.getString("falseAnswer2");
        String falseAnswer3 = rs.getString("falseAnswer3");
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add(falseAnswer1);
        falseAnswers.add(falseAnswer2);
        falseAnswers.add(falseAnswer3);
        int answeredRight = rs.getInt("answeredRight");
        int totalAnswers = rs.getInt("totalAnswers");
                
        QuizCard quizCard = new QuizCard(question, rightAnswer, falseAnswers, boxNumber, answeredRight, totalAnswers);
        
        return quizCard;
    }
}
