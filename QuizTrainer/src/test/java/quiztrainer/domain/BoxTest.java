
package quiztrainer.domain;

import quiztrainer.logic.Leitner;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoxTest {
    
    QuizCard quizCard;
    Leitner leitner;
    Deck deck;
    Box box1;
    ArrayList<Box> boxes; 
    
    @Before
    public void setUp() {
        String question = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");
        
        quizCard = new QuizCard(question, answer, falseAnswers, 1);

        int boxNumber = 1;
        box1 = new Box(boxNumber);
        
        box1.addACard(quizCard);
    }
    
    @Test
    public void addingACardToABoxIncreasesSize() {
        assertEquals(1, box1.getQuizCards().size());
    }
    
    @Test
    public void removingACardFromTheBoxReducesSize() {
        box1.removeACard(quizCard);
        assertEquals(0, box1.getQuizCards().size());
    }
}
