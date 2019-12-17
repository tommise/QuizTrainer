
package quiztrainer.domain;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import quiztrainer.dao.Database;
import quiztrainer.dao.DbQuizCardDao;
import quiztrainer.dao.DbUserDao;
import quiztrainer.dao.QuizCardDao;
import quiztrainer.dao.UserDao;

public class QuizTrainerServiceTest {
    
    Deck deck;
    Database database;
    UserDao userDao;
    QuizCardDao quizCardDao;
    QuizCard quizCardOne;
    QuizCard quizCardTwo;
    String quizCardOneQuestion;
    String quizCardTwoQuestion;    
    QuizTrainerService quizTrainerService;
       
    @Before
    public void setUp() throws SQLException {
        this.quizTrainerService = new QuizTrainerService("jdbc:sqlite:quiztrainerTest.db");
        this.database = quizTrainerService.database;
        this.userDao = new DbUserDao(database);
        this.quizCardDao = new DbQuizCardDao(database);
        this.deck = new Deck("Default deck");
        
        quizCardOneQuestion = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");         
        quizCardOne = new QuizCard(quizCardOneQuestion, answer, falseAnswers, 1, 0, 0);
        
        quizCardTwoQuestion = "What is the capital city of Sweden?";
        String answer2 = "Stockholm";
        ArrayList<String> falseAnswers2 = new ArrayList<>();
        falseAnswers2.add("Helsinki");
        falseAnswers2.add("Oslo");
        falseAnswers2.add("Shanghai");    
        
        quizCardTwo = new QuizCard(quizCardTwoQuestion, answer2, falseAnswers2, 1, 0, 0);        
        
        this.deck.addACard(quizCardOne, 3); 
        this.deck.addACard(quizCardTwo, 3); 
        this.quizCardOne.setBoxNumber(3);
        this.quizCardTwo.setBoxNumber(3);        
    }
    
    @After
    public void tearDown() {
        File file = new File("quiztrainerTest.db");
        file.delete();
    }
    
    @Test
    public void addingANewUserCreatesTheUser() throws Exception {
        String username = "test";
        String name = "testname";
        this.quizTrainerService.addANewUser(username, name);
        
        User createdUser = userDao.findByUsername(username);
        createdUser.getUsername();
        
        assertEquals(username, createdUser.getUsername());
    }
    
    @Test
    public void addingANewQuizCardCreatesTheQuizCard() throws Exception {
        
        this.quizTrainerService.addANewQuizCard(quizCardOne);
        
        QuizCard createdQuizCard = quizCardDao.findByQuestion(quizCardOneQuestion);
        
        assertEquals(quizCardOneQuestion, createdQuizCard.getQuestion());
    }
    
    @Test
    public void correctAnswerUpdatesNewBoxInTheDatabase() throws Exception {
        
        this.quizTrainerService.addANewQuizCard(quizCardOne);      
        
        this.quizTrainerService.correctAnswer(quizCardOne, deck);
        
        QuizCard updatedQuizCard = quizCardDao.findByQuestion(quizCardOneQuestion);
        
        assertEquals(4, updatedQuizCard.getBoxNumber());
    }
    
    @Test
    public void wrongAnswerUpdatesNewBoxInTheDatabase() throws Exception {
        
        this.quizTrainerService.addANewQuizCard(quizCardTwo);
        
        this.quizTrainerService.wrongAnswer(quizCardTwo, deck);
        
        QuizCard updatedQuizCard = quizCardDao.findByQuestion(quizCardTwoQuestion);
        
        assertEquals(1, updatedQuizCard.getBoxNumber());
    }

    @Test
    public void initializingDeckTakesAllCardsFromTheDatabase() throws Exception {
        
        this.quizTrainerService.addANewQuizCard(quizCardOne);
        this.quizTrainerService.addANewQuizCard(quizCardTwo);        
        
        Deck initializedDeck = this.quizTrainerService.initDeck();
        
        ArrayList<Box> boxes = initializedDeck.getBoxes();
        int totalCardCount = 0;
        
        for (Box box : boxes) {
            totalCardCount += box.getQuizCards().size();
        }
        
        assertEquals(2, totalCardCount);
    } 
}