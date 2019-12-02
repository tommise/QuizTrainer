
package quiztrainer.logic;

import quiztrainer.domain.Box;
import quiztrainer.domain.Deck;
import quiztrainer.domain.QuizCard;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardIntervalTest {
    
    QuizCard quizCard1;
    QuizCard quizCard2;
    Leitner leitner;
    CardInterval interval;
    ArrayList<Box> boxes;    
    Deck deck;
    Box box1;
    Box box4;
    
    @Before
    public void setUp() {
        String question1 = "What is the capital city of Finland?";
        String answer1 = "Helsinki";
        ArrayList<String> falseAnswers1 = new ArrayList<>();
        falseAnswers1.add("Tokyo");
        falseAnswers1.add("Oslo");
        falseAnswers1.add("Shanghai");
        
        quizCard1 = new QuizCard(question1, answer1, falseAnswers1);
        
        String question2 = "What is the capital city of Sweden?";
        String answer2 = "Stockholm";
        ArrayList<String> falseAnswers2 = new ArrayList<>();
        falseAnswers2.add("Helsinki");
        falseAnswers2.add("Oslo");
        falseAnswers2.add("Shanghai");    
        
        quizCard2 = new QuizCard(question2, answer2, falseAnswers2);
        
        leitner = new Leitner();
        deck = new Deck("Default deck");
        interval = new CardInterval();
        boxes = deck.getBoxes();
        box1 = boxes.get(0);
        box4 = boxes.get(3);
    }
    
    @Test
    public void drawsARandomCardFromBoxes() {
        deck.addACard(quizCard1, 1);
        deck.addACard(quizCard2, 4);
        
        QuizCard drawnCard = interval.drawANewCard(boxes);
        
        boolean cardDrawn = false;
        
        if (drawnCard != null) {
            cardDrawn = true;
        }
        
        assertTrue(cardDrawn);
    }
}
