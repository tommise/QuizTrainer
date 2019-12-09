
package quiztrainer.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import quiztrainer.dao.Database;
import quiztrainer.dao.FileQuizCardDao;
import quiztrainer.dao.FileUserDao;
import quiztrainer.dao.QuizCardDao;
import quiztrainer.dao.UserDao;

public class QuizTrainerServiceTest {
    
    Database database;
    UserDao userDao;
    QuizCardDao quizCardDao;
    QuizCard quizCardOne;
    QuizCard quizCardTwo;
    String quizCardOneQuestion;
       
    @Before
    public void setUp() {
        try {     
            this.database = new Database("jdbc:sqlite:quiztrainerTest.db");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuizTrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.userDao = new FileUserDao(database);
        this.quizCardDao = new FileQuizCardDao(database);
        
        quizCardOneQuestion = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");         
        
        quizCardOne = new QuizCard(quizCardOneQuestion, answer, falseAnswers, 1);
        
        String question2 = "What is the capital city of Sweden?";
        String answer2 = "Stockholm";
        ArrayList<String> falseAnswers2 = new ArrayList<>();
        falseAnswers2.add("Helsinki");
        falseAnswers2.add("Oslo");
        falseAnswers2.add("Shanghai");    
        
        quizCardTwo = new QuizCard(question2, answer2, falseAnswers2, 1);
    }
    
    @After
    public void tearDown() {
        File file = new File("quiztrainerTest.db");
        file.delete();
    }
    
    @Test
    public void addingANewUserCreatesTheUser() throws Exception {
        String username = "test";
        User user = new User(username, "testname");
        this.userDao.create(user);
        
        User createdUser = userDao.findByUsername(username);
        createdUser.getUsername();
        
        assertEquals(createdUser.getUsername(), username);
    }
    
    @Test
    public void addingANewQuizCardCreatesTheQuizCard() throws Exception {
        
        this.quizCardDao.create(quizCardOne, 1);
        
        QuizCard createdQuizCard = quizCardDao.findByQuestion(quizCardOneQuestion);
        
        assertEquals(createdQuizCard.getQuestion(), quizCardOneQuestion);
    }
}
