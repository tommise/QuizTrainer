
package quiztrainer.domain;

import quiztrainer.dao.Database;
import quiztrainer.dao.FileUserDao;
import quiztrainer.dao.UserDao;
import quiztrainer.logic.Leitner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizTrainerService {
    
    private UserDao userDao;
    private Database database;
    
    private Leitner leitner;
    private User currentUser;
    
    public QuizTrainerService() {
        
        try {     
            this.database = new Database("jdbc:sqlite:quiztrainer.db");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuizTrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.userDao = new FileUserDao(database);
        this.leitner = new Leitner();
        this.currentUser = null;
        
        addANewUser("testi", "testinimi");
    }
    
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
    
    public boolean login(String username) {       
        User user = userDao.findByUsername(username);          
        
        if (user == null) {
            return false;
        }
        
        currentUser = user; 
        return true;
    }
    
    public void logout() {
        this.currentUser = null;
    }    
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public String correctAnswer(QuizCard quizCard, Deck deck) {
        this.leitner.moveCardUp(quizCard, deck);
        return quizCard.getCorrectAnswerString();
    }
    
    public String wrongAnswer(QuizCard quizCard, Deck deck) {
        this.leitner.moveCardToBoxOne(quizCard, deck);
        return quizCard.getWrongAnswerString();
    }      
}
