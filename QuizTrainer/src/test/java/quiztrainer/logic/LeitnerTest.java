
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import quiztrainer.domain.Deck;

public class LeitnerTest {
    
    Deck deck;   
    Leitner leitner;
    QuizCard quizCard;
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
        
        deck.addACard(quizCard, 1);
    }  
    
    @Test
    public void rightAnswerMovesTheCardUpOneBox() {
        Box box2 = boxes.get(1);
        leitner.moveCardUp(quizCard, deck);
        
        assertEquals(1, box2.getQuizCards().size());
    }
    
    @Test
    public void wrongAnswerMovesTheCardUpOneBox() {
        Box box1 = boxes.get(0);
        leitner.moveCardUp(quizCard, deck);
        leitner.moveCardUp(quizCard, deck);
        leitner.moveCardUp(quizCard, deck);
        leitner.moveCardToBoxOne(quizCard, deck);
        
        assertEquals(1, box1.getQuizCards().size());
    }
    
    @Test
    public void notAbleToMoveUpWhenCurrentBoxIsFive() {
        Box box5 = boxes.get(4);
        
        for (int i = 0; i < 10; i++) {
            this.leitner.moveCardUp(quizCard, deck);
        }
        
        assertEquals(1, box5.getQuizCards().size());
    }
    
    @Test
    public void notAbleToMoveDownWhenCurrentBoxIsOne() {
        Box box1 = boxes.get(0);
        
        leitner.moveCardToBoxOne(quizCard, deck);
        
        assertEquals(1, box1.getQuizCards().size());
    }
}
