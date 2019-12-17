
package quiztrainer.domain;

import java.util.List;
import quiztrainer.dao.Database;
import quiztrainer.dao.UserDao;
import quiztrainer.dao.QuizCardDao;

import quiztrainer.dao.DbUserDao;
import quiztrainer.dao.DbQuizCardDao;

import quiztrainer.logic.Leitner;

import java.util.logging.Level;
import java.util.logging.Logger;


public class QuizTrainerService {
    
    Database database;
    
    private UserDao userDao;
    private QuizCardDao quizCardDao;
    
    private Deck deck;
    private Leitner leitner;
    private User currentUser;
    private int currentUserId;
    
    public QuizTrainerService(String databaseUrl) {
        
        try {     
            this.database = new Database(databaseUrl);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuizTrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.userDao = new DbUserDao(database);
        this.quizCardDao = new DbQuizCardDao(database);
        
        this.deck = new Deck("Default deck");
        this.leitner = new Leitner();
        this.currentUser = null;
    }
    
    /**
     * Adding a new user to the database.
     *
     * @param   username   Username which was chosen by the user.
     * @param   name   Name which was chosen by the user.
     * 
     * @return true if user was successfully added.
     */
    
    public boolean addANewUser(String username, String name) {
        
        if (userDao.findByUsername(username) != null) {
            return false;
        }

        User newUser = new User(username, name);
        
        try {
            userDao.create(newUser);
            this.currentUser = newUser;
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
    /**
     * Logging user in. 
     * The user will be logged in if given username is
     * found with userDao.findByUsername() method.
     *
     * @param   username   Username to be logged in.
     * 
     * @return true if user was successfully found.
     */    
    
    public boolean login(String username) {       
        User user = userDao.findByUsername(username);          
        
        if (user == null) {
            return false;
        }
        
        currentUserId = userDao.getIdByUsername(username);
        currentUser = user; 
        return true;
    }
    
    /**
     * Logging user out. 
     * currentUser variable will be assigned a null value.
     */   
    
    public void logout() {
        this.currentUser = null;
    }    
    
    public User getCurrentUser() {
        return currentUser;
    }
    
     /**
     * Adding a new QuizCard.
     * If given quizCard was not found within the database,
     * a quizCard will be added to the database with
     * quizCardDao.create() method.
     *
     * @param   quizCard   QuizCard to be added to the database.
     * 
     * @return true if QuizCard was successfully added.
     */  
    
    public boolean addANewQuizCard(QuizCard quizCard) {
        
        if (quizCardDao.findByQuestion(quizCard.getQuestion()) != null) {
            return false;
        }
        
        try {
            quizCardDao.create(quizCard, currentUserId);
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
     /**
     * User has answered a QuizCard right.
     * Moves a QuizCard to a higher box within the database
     * with method quizCardDao.setBox()
     * 
     * @param   quizCard   QuizCard to be moved to a different box in the database.
     * @param   currentDeck   Current deck to be passed to the Leitner object.
     */ 
    
    public void correctAnswer(QuizCard quizCard, Deck currentDeck) {
        int moveCardToBox = quizCard.getBoxNumber() + 1;
        
        if (moveCardToBox > 5) {
            return;
        }
        
        this.leitner.moveCardUp(quizCard, currentDeck);
        
        int quizCardId = this.quizCardDao.getIdByQuestion(quizCard.getQuestion());

        try {
            this.quizCardDao.setBox(quizCardId, moveCardToBox);
        } catch (Exception e) {
            return;
        } 
    }
    
     /**
     * User has answered a QuizCard wrong.
     * Moves a QuizCard to box number one within the database
     * with method quizCardDao.setBox()
     * 
     * @param   quizCard   QuizCard to be moved to a different box in the database.
     * @param   currentDeck   Current deck to be passed to the Leitner object.
     */  
    
    public void wrongAnswer(QuizCard quizCard, Deck currentDeck) {
        this.leitner.moveCardToBoxOne(quizCard, currentDeck);        
        
        int quizCardId = this.quizCardDao.getIdByQuestion(quizCard.getQuestion());
        
        try {
            this.quizCardDao.setBox(quizCardId, 1);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
     /**
     * Initializing the deck.
     * Initializes the default deck by
     * calling quizCardDao.getAllQuizCards(currentUserId)
     * and adding them locally to the Deck object.
     * 
     * @return default Deck.
     */
    
    public Deck initDeck() {
        List<QuizCard> allQuizCards = this.quizCardDao.getAllQuizCards(currentUserId);

        for (QuizCard quizCard : allQuizCards) {
            deck.addACard(quizCard, quizCard.getBoxNumber());
        }
        
        return this.deck;
    }
    
    public List<QuizCard> getAllQuizCards() {
        List<QuizCard> allQuizCards = this.quizCardDao.getAllQuizCards(currentUserId);
        
        return allQuizCards;
    }    
}
