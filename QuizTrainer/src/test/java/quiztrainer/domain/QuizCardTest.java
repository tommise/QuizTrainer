package quiztrainer.domain;

import java.util.*;
import quiztrainer.logic.Trainer;
import quiztrainer.logic.Leitner;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuizCardTest {
    QuizCard quizCard;
    Trainer quizTrainer;
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
        
        quizCard = new QuizCard(question, answer, falseAnswers);
        leitner = new Leitner();
        quizTrainer = new Trainer();
        
        leitner.addANewCard(quizCard);
    }  
    
    @Test
    public void threeAnswerChoicesAreGenerated() {
        ArrayList<String> choices = quizCard.generateChoices();
        assertEquals(3, choices.size());
    }
}
