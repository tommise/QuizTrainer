
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LeitnerTest {
    QuizCard quizCard;
    Trainer quizTrainer;
    Leitner leitner;
    ArrayList<Box> boxes;
    
    @Before
    public void setUp() {
        String question = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");
        
        quizCard = new QuizCard(question, answer, falseAnswers);
        leitner = new Leitner();
        quizTrainer = new Trainer();
        boxes = leitner.getBoxes();
        
        leitner.addANewCard(quizCard);
    }  
    
    @Test
    public void newCardGoesToBoxOne() {
        Box box1 = boxes.get(0);
        assertEquals(1, box1.getQuizCards().size());
    }
    
    @Test
    public void allOtherBoxesAreEmptyAtStart() {
        int totalSize = 0;
        
        for (int i = 1; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            totalSize += box.getQuizCards().size();
            
        }
        assertEquals(0, totalSize);
    }
    
    @Test
    public void rightAnswerMovesTheCardUpOneBox() {
        Box box2 = boxes.get(1);
        leitner.moveCardUp(quizCard);
        
        assertEquals(1, box2.getQuizCards().size());
    }
    
    @Test
    public void wrongAnswerMovesTheCardUpOneBox() {
        Box box1 = boxes.get(0);
        leitner.moveCardUp(quizCard);
        leitner.moveCardUp(quizCard);
        leitner.moveCardUp(quizCard);
        leitner.moveCardToBoxOne(quizCard);
        
        assertEquals(1, box1.getQuizCards().size());
    }
    
    @Test
    public void notAbleToMoveUpWhenCurrentBoxIsFive() {
        Box box5 = boxes.get(4);
        
        for (int i = 0; i < 10; i++) {
            this.leitner.moveCardUp(quizCard);
        }
        
        assertEquals(1, box5.getQuizCards().size());
    }
    
    @Test
    public void notAbleToMoveDownWhenCurrentBoxIsOne() {
        Box box1 = boxes.get(0);
        
        leitner.moveCardToBoxOne(quizCard);
        
        assertEquals(1, box1.getQuizCards().size());
    }
}
