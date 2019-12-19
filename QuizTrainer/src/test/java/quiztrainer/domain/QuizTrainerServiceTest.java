
package quiztrainer.domain;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import quiztrainer.dao.*;

public class QuizTrainerServiceTest {
    
    Deck deck;
    Database database;
    QuizCard quizCardOne;
    QuizCard quizCardTwo;  
    
    UserDao userDao;
    DeckDao deckDao;
    QuizCardDao quizCardDao;
    QuizTrainerService trainer;
       
    @Before
    public void setUp() throws SQLException {
        this.trainer = new QuizTrainerService("jdbc:sqlite:quiztrainerTest.db");
        this.database = trainer.database;
        this.userDao = new DbUserDao(database);
        this.deckDao = new DbDeckDao(database);
        this.quizCardDao = new DbQuizCardDao(database);
        
        String quizCardOneQuestion = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");         
        quizCardOne = new QuizCard(quizCardOneQuestion, answer, falseAnswers, 1, 0, 0);
        
        String quizCardTwoQuestion = "What is the capital city of Sweden?";
        String answer2 = "Stockholm";
        ArrayList<String> falseAnswers2 = new ArrayList<>();
        falseAnswers2.add("Helsinki");
        falseAnswers2.add("Oslo");
        falseAnswers2.add("Shanghai");    
        quizCardTwo = new QuizCard(quizCardTwoQuestion, answer2, falseAnswers2, 1, 0, 0);       
        
        this.deck = new Deck("Default deck");
        this.deck.addACard(quizCardOne, 3);  
        this.quizCardOne.setBoxNumber(3);
        this.trainer.addANewUser("test", "test");
        this.trainer.login("test");
    }
    
    @After
    public void tearDown() {
        File file = new File("quiztrainerTest.db");
        file.delete();
    }
    
    @Test
    public void addingANewUserCreatesTheUser() throws Exception {
        String username = "test username";
        String name = "test name";
        this.trainer.addANewUser(username, name);
        
        User createdUser = userDao.findByUsername(username);
        createdUser.getUsername();
        
        assertEquals(username, createdUser.getUsername());
    }
    
    @Test
    public void loggingInUpdatesCurrentUser() throws Exception {
        this.trainer.addANewUser("test username", "test name");
        this.trainer.login("test username");
        
        assertEquals("test username", this.trainer.getCurrentUser().getUsername());
    }
    
    @Test
    public void loggingOutUpdatesCurrentUser() throws Exception {
        this.trainer.addANewUser("test username", "test name");
        this.trainer.login("test username");
        this.trainer.logout();
        
        assertNull(this.trainer.getCurrentUser());
    }
    
    @Test
    public void addingANewQuizCardCreatesTheQuizCard() throws Exception {
        this.trainer.addANewQuizCard(quizCardOne, "Cities");
        int currentUserId = userDao.getIdByUsername("test");
        
        QuizCard createdQuizCard = quizCardDao.findByQuestion(quizCardOne.getQuestion(), currentUserId);
        
        assertEquals(quizCardOne.getQuestion(), createdQuizCard.getQuestion());
    }
    
    @Test
    public void removingACardRemovesTheRightCard() {
        this.trainer.addANewDeck("Cities");        
        this.trainer.addANewQuizCard(quizCardOne, "Cities");
        this.trainer.addANewQuizCard(quizCardTwo, "Cities");
        
        this.trainer.removeAQuizCard(quizCardOne);
        int currentUserId = userDao.getIdByUsername("test");
        QuizCard quizCard = this.quizCardDao.findByQuestion(quizCardOne.getQuestion(), currentUserId);
        
        assertNull(quizCard);
    } 
    
    @Test
    public void removingACardReducesTheSizeOfDeck() {
        this.trainer.addANewDeck("Cities");
        this.trainer.addANewQuizCard(quizCardOne, "Cities");
        this.trainer.addANewQuizCard(quizCardTwo, "Cities");
        
        this.trainer.removeAQuizCard(quizCardOne);
     
        List<QuizCard> cities = this.trainer.getQuizCardsInDeck("Cities");
        
        assertEquals(1, cities.size());
    }     
    
    @Test
    public void creatingANewUserInitializesDefaultDeckForUser() {
        Deck defaultDeck = this.trainer.getDeckByName("Default Deck");
        
        assertEquals("Default Deck", defaultDeck.getDeckName());
    }
    
    @Test
    public void addingANewDeckCreatesTheDeck() throws Exception {
        int userId = userDao.getIdByUsername("test");
        this.trainer.addANewDeck("Anatomy");
        Deck createdDeck = deckDao.findDeckByDeckName("Anatomy", userId);
        
        assertEquals("Anatomy", createdDeck.getDeckName());
    }
    
    @Test
    public void correctAnswerUpdatesNewBoxInTheDatabase() throws Exception {
        
        this.trainer.addANewQuizCard(quizCardOne, "Default Deck");
        int currentUserId = userDao.getIdByUsername("test");    
        this.trainer.correctAnswer(quizCardOne, deck);
        
        QuizCard updatedQuizCard = quizCardDao.findByQuestion(quizCardOne.getQuestion(), currentUserId);
        
        assertEquals(4, updatedQuizCard.getBoxNumber());
    }
    
    @Test
    public void wrongAnswerUpdatesNewBoxInTheDatabase() throws Exception {
        
        this.trainer.addANewQuizCard(quizCardOne, "Default Deck");
        int currentUserId = userDao.getIdByUsername("test");
        this.trainer.wrongAnswer(quizCardOne, deck);
        
        QuizCard updatedQuizCard = quizCardDao.findByQuestion(quizCardOne.getQuestion(), currentUserId);
        
        assertEquals(1, updatedQuizCard.getBoxNumber());
    }
    
    @Test
    public void nextQuestionDrawReturnsQuizCard() {
        QuizCard nextQuizCard = this.trainer.drawNextQuestion(deck);
        
        assertNotNull(nextQuizCard);
    }     
    
    @Test
    public void nextQuestionDrawReturnsNullIfBoxesAreEmpty() {
        Deck newDeck = new Deck("Deck name");
        QuizCard nextQuizCard = this.trainer.drawNextQuestion(newDeck);
        
        assertNull(nextQuizCard);
    }
    
    @Test
    public void updatingADeckReturnsFalseIfNoCardsInDeck() {
        Deck defaultDeck = this.trainer.getDeckByName("Default Deck");
        
        assertEquals(false, this.trainer.updateDeck(defaultDeck));
    }
    
    @Test
    public void updatingADeckReturnsTrueIfCardsInDeck() {
        Deck defaultDeck = this.trainer.getDeckByName("Default Deck");
        
        this.trainer.addANewQuizCard(quizCardOne, defaultDeck.getDeckName());
        this.trainer.addANewQuizCard(quizCardTwo, defaultDeck.getDeckName());
        
        assertEquals(true, this.trainer.updateDeck(defaultDeck));
    }
    
    @Test
    public void creatingMultipleCardsAndSearchingThemReturnsAll() {
        this.trainer.addANewDeck("Second Deck");
        
        this.trainer.addANewQuizCard(quizCardOne, "Default Deck");
        this.trainer.addANewQuizCard(quizCardTwo, "Second Deck");
        
        List<QuizCard> allQuizCards = this.trainer.getAllQuizCards();
        
        assertEquals(2, allQuizCards.size());
    }
    
    @Test
    public void mostRehearsedReturnsRightCard() {
        this.trainer.addANewQuizCard(quizCardOne, "Default Deck");
        this.trainer.addANewQuizCard(quizCardTwo, "Default Deck");
        
        for (int i = 0; i < 10; i++) {
            this.trainer.updateAnswerAmount(quizCardOne);
        }
        
        for (int i = 0; i < 3; i++) {
            this.trainer.updateAnswerAmount(quizCardTwo);
        }
        
        QuizCard mostRehearsedQuizCard = this.trainer.getTheMostRehearsedQuizCard();
        
        assertEquals(quizCardOne.getQuestion(), mostRehearsedQuizCard.getQuestion());
        
    }
    
    @Test
    public void mostAnsweredRightReturnsRightCard() {
        this.trainer.addANewQuizCard(quizCardOne, "Default Deck");
        this.trainer.addANewQuizCard(quizCardTwo, "Default Deck");
        
        for (int i = 0; i < 10; i++) {
            this.trainer.updateRightAnswerAmount(quizCardOne);
        }
        
        for (int i = 0; i < 15; i++) {
            this.trainer.updateRightAnswerAmount(quizCardTwo);
        }
        
        QuizCard mostRightQuizCard = this.trainer.getTheMostRightAnsweredQuizCard();
        
        assertEquals(quizCardTwo.getQuestion(), mostRightQuizCard.getQuestion());
    }
    
    @Test
    public void mostAnsweredWrongReturnsRightCard() {
        this.trainer.addANewQuizCard(quizCardOne, "Default Deck");
        this.trainer.addANewQuizCard(quizCardTwo, "Default Deck");
        
        for (int i = 0; i < 10; i++) {
            this.trainer.updateRightAnswerAmount(quizCardOne);
        }
        
        for (int i = 0; i < 10; i++) {
            this.trainer.updateAnswerAmount(quizCardTwo);
        }
        
        QuizCard mostWrongQuizCard = this.trainer.getTheMostWrongAnsweredQuizCard();
        
        assertEquals(quizCardTwo.getQuestion(), mostWrongQuizCard.getQuestion());
    }
    
    @Test
    public void creatingMultipleDecksReturnsAllDecks() {
        this.trainer.addANewDeck("Second Deck");
        this.trainer.addANewDeck("Third Deck");
        List<Deck> decks = this.trainer.updateAllDecks();
        
        // Default deck will be initialized upon creating a new user, hence 3
        assertEquals(3, decks.size());
    }
    
    @Test
    public void updatingDecksContainsAllQuizCards() {
        this.trainer.addANewDeck("Second Deck");
        this.trainer.addANewDeck("Third Deck");
        
        this.trainer.addANewQuizCard(quizCardOne, "Second Deck");
        this.trainer.addANewQuizCard(quizCardTwo, "Third Deck");
        
        List<Deck> decks = this.trainer.updateAllDecks();
        int totalSize = 0;
        for (Deck deck : decks) {
            List<Box> boxes = deck.getBoxes();
            
            for (Box box : boxes) {
                totalSize += box.getQuizCards().size();
            }
        }
        
        assertEquals(2, totalSize); 
    }
}
