
package quiztrainer.dao;

import java.util.*;
import java.sql.*;
import quiztrainer.domain.QuizCard;

public class FileQuizCardDao implements QuizCardDao {
    private Database db;
    
    public FileQuizCardDao(Database db) {
        this.db = db;
    }
    
     /**
     * Stores a new QuizCard to the database.
     * 
     * @param quizCard  A QuizCard object which was made by the user.
     * @param userId   Current user id
     * @return created QuizCard object
     */
    
    @Override
    public QuizCard create(QuizCard quizCard, int userId) throws Exception, SQLException {
        int boxNumber = 1;
        String question = quizCard.getQuestion();
        String rightAnswer = quizCard.getCorrectAnswer();
        ArrayList<String> falseAnswers = quizCard.getFalseAnswers();
        String falseAnswer1 = falseAnswers.get(0);
        String falseAnswer2 = falseAnswers.get(1);
        String falseAnswer3 = falseAnswers.get(2);   
        
        Connection dbConnection = db.getConnection();
        PreparedStatement createNewQuizCardStatement = dbConnection.prepareStatement("INSERT INTO QuizCard"
                + " (user_id, boxNumber, question, rightAnswer, falseAnswer1, falseAnswer2, falseAnswer3)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)");
        createNewQuizCardStatement.setInt(1, userId); 
        createNewQuizCardStatement.setInt(2, boxNumber); 
        createNewQuizCardStatement.setString(3, question); 
        createNewQuizCardStatement.setString(4, rightAnswer);
        createNewQuizCardStatement.setString(5, falseAnswer1); 
        createNewQuizCardStatement.setString(6, falseAnswer2);
        createNewQuizCardStatement.setString(7, falseAnswer3);         
        createNewQuizCardStatement.executeUpdate();        
        createNewQuizCardStatement.close();
        
        dbConnection.close();
        
        QuizCard newQuizCard = findByQuestion(question);
        
        return newQuizCard;
    }   
    
     /**
     * Searches and returns an QuizCard object based on given question.
     * 
     * @param findWithQuestion  question as a String to be searched.
     * @return found QuizCard object if successful.
     */
    
    @Override
    public QuizCard findByQuestion(String findWithQuestion) {
        QuizCard foundQuizCard;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardStatement = dbConnection.prepareStatement("SELECT "
                    + "boxNumber, question, rightAnswer, falseAnswer1, falseAnswer2, falseAnswer3 "
                    + "FROM QuizCard WHERE question = ?");
            findQuizCardStatement.setString(1, findWithQuestion);

            ResultSet rs = findQuizCardStatement.executeQuery();

            if (rs.next()) {
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
                
                foundQuizCard = new QuizCard(question, rightAnswer, falseAnswers, boxNumber);
            } else {
                return null;
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
        ArrayList allQuizCards = new ArrayList();
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement getAllQuizCardsStatement = dbConnection.prepareStatement("SELECT * FROM QuizCard WHERE user_id = ?");
            getAllQuizCardsStatement.setInt(1, currentUserId);
            
            ResultSet rs = getAllQuizCardsStatement.executeQuery();
        
            while (rs.next()) {
                
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
                
                QuizCard quizCard = new QuizCard(question, rightAnswer, falseAnswers, boxNumber);
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
     * based on a given question.
     * 
     * @param question  question as a String to be searched.
     * @return found QuizCard id as an int if successful.
     */
    
    @Override
    public int getIdByQuestion(String question) {
        int quizCardId;
        
        try {
            Connection dbConnection = db.getConnection();
            PreparedStatement findQuizCardIdStatement = dbConnection.prepareStatement("SELECT id FROM QuizCard WHERE question = ?");
            findQuizCardIdStatement.setString(1, question);

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
}
