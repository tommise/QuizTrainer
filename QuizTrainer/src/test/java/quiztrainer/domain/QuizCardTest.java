
package quiztrainer.domain;

import java.util.*;
import quiztrainer.logic.Leitner;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuizCardTest {
    
    QuizCard quizCard;
    Deck deck;
    Leitner leitner;

    @Before
    public void setUp() {
        String question = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");
        falseAnswers.add("Stockholm");   
        falseAnswers.add("Tallinn");         
        
        quizCard = new QuizCard(question, answer, falseAnswers, 1, 0, 0);
        leitner = new Leitner();
        deck = new Deck("Default deck");
        
        deck.addANewCard(quizCard);
    }  
    
    @Test
    public void threeAnswerChoicesAreGenerated() {
        ArrayList<String> choices = quizCard.generateChoices();
        assertEquals(3, choices.size());
    }
}
