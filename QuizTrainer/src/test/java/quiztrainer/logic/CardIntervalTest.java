
package quiztrainer.logic;

import quiztrainer.utils.Leitner;
import quiztrainer.utils.CardInterval;
import quiztrainer.domain.Box;
import quiztrainer.domain.Deck;
import quiztrainer.domain.QuizCard;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardIntervalTest {
    
    Deck deck;
    Leitner leitner;
    QuizCard quizCard1;
    QuizCard quizCard2;
    ArrayList<Box> boxes;    
    CardInterval interval;
    
    @Before
    public void setUp() {
        String question1 = "What is the capital city of Finland?";
        String answer1 = "Helsinki";
        ArrayList<String> falseAnswers1 = new ArrayList<>();
        falseAnswers1.add("Tokyo");
        falseAnswers1.add("Oslo");
        falseAnswers1.add("Shanghai");
        
        quizCard1 = new QuizCard(question1, answer1, falseAnswers1, 1, 0, 0);
        
        String question2 = "What is the capital city of Sweden?";
        String answer2 = "Stockholm";
        ArrayList<String> falseAnswers2 = new ArrayList<>();
        falseAnswers2.add("Helsinki");
        falseAnswers2.add("Oslo");
        falseAnswers2.add("Shanghai");    
        
        quizCard2 = new QuizCard(question2, answer2, falseAnswers2, 1, 0, 0);
        
        leitner = new Leitner();
        deck = new Deck("Default deck");
        interval = new CardInterval();

        deck.addACard(quizCard1, 1);
        deck.addACard(quizCard1, 2);
        deck.addACard(quizCard1, 3);
        deck.addACard(quizCard2, 4);        
        deck.addACard(quizCard2, 5);
        
        boxes = deck.getBoxes();
    }
    
    @Test
    public void drawsARandomCardFromBoxes() {
        
        QuizCard drawnCard = interval.drawANewCard(boxes);
        
        boolean cardDrawn = false;
        
        if (drawnCard != null) {
            cardDrawn = true;
        }
        
        assertTrue(cardDrawn);
    }
    
    @Test
    public void drawsBoxOneWithAtLeastFiftyPercentOfTheTime() {
        
        int boxOneCount = 0;
                
        for (int i = 0; i <= 10000000; i++) {
            int nextBox = interval.drawACardFromBoxes(boxes);
            
            if (nextBox == 1) {
                boxOneCount++;
            }
        }
        
        int boxOnePercentage = boxOneCount / 100000;
        
        assertTrue(boxOnePercentage >= 50);
    }
    
    @Test
    public void drawsBoxTwoWithAtLeastFifteenPercentOfTheTime() {
        
        int boxTwoCount = 0;
                
        for (int i = 0; i <= 10000000; i++) {
            int nextBox = interval.drawACardFromBoxes(boxes);
            
            if (nextBox == 2) {
                boxTwoCount++;
            }
        }
        
        int boxTwoPercentage = boxTwoCount / 100000;
        
        assertTrue(boxTwoPercentage >= 15);
    }    
    
    @Test
    public void drawsBoxThreeWithAtLeastSevenPercentOfTheTime() {
        
        int boxThreeCount = 0;
                
        for (int i = 0; i <= 10000000; i++) {
            int nextBox = interval.drawACardFromBoxes(boxes);
            
            if (nextBox == 3) {
                boxThreeCount++;
            }
        }
        
        int boxThreePercentage = boxThreeCount / 100000;
        
        assertTrue(boxThreePercentage >= 7);
    }  
    
    @Test
    public void drawsBoxFourWithAtLeastTwoPercentOfTheTime() {
        
        int boxFourCount = 0;
                
        for (int i = 0; i <= 10000000; i++) {
            int nextBox = interval.drawACardFromBoxes(boxes);
            
            if (nextBox == 4) {
                boxFourCount++;
            }
        }
        
        int boxFourPercentage = boxFourCount / 100000;
        
        assertTrue(boxFourPercentage >= 2);
    } 

    @Test
    public void drawsBoxFiveWithAtLeastTwoPercentOfTheTime() {
        
        int boxFiveCount = 0;
                
        for (int i = 0; i <= 10000000; i++) {
            int nextBox = interval.drawACardFromBoxes(boxes);
            
            if (nextBox == 5) {
                boxFiveCount++;
            }
        }
        
        int boxFivePercentage = boxFiveCount / 100000;
        
        assertTrue(boxFivePercentage >= 2);
    }     
}
