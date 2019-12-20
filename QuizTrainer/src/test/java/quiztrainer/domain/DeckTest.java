
package quiztrainer.domain;

import quiztrainer.utils.Leitner;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {
    
    QuizCard quizCard;
    Leitner leitner;
    Deck deck;
    ArrayList<Box> boxes;    
    
    @Before
    public void setUp() {
        String question = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");
        
        quizCard = new QuizCard(question, answer, falseAnswers, 1, 0, 0);
        leitner = new Leitner();
        deck = new Deck("Default deck");
        boxes = deck.getBoxes();       
    }
    
    @Test
    public void allBoxesAreEmptyAtStart() {
        int totalSize = 0;
        
        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            totalSize += box.getQuizCards().size();
            
        }
        assertEquals(0, totalSize);
    }
    
    @Test
    public void addingACardToSpecificBoxGoesToRightBox() {
        Box box5 = boxes.get(4);
        deck.addACard(quizCard, 5);
        assertEquals(1, box5.getQuizCards().size());
    }
    
    @Test
    public void searchingWithQuestionReturnsRightCard() {
        deck.addACard(quizCard, 1);
        String question = "What is the capital city of Finland?";
        
        assertEquals(quizCard, deck.getACard(question));
    } 
    
    @Test
    public void searchingForABoxWithGivenCardReturnsRightBox() {
        deck.addACard(quizCard, 4);
        int boxNumber = deck.getCurrentBox(quizCard).getBoxNumber();
        
        assertEquals(4, boxNumber);
    }
}
