package domain;

import java.util.*;
import logic.Application;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuizCardTest {
    QuizCard quizCard;
    Application app;
    Scanner answer;

    @Before
    public void setUp() {
        String question = "What is the capital city of Finland?";
        String answer = "Helsinki";
        ArrayList<String> falseAnswers = new ArrayList<>();
        falseAnswers.add("Tokyo");
        falseAnswers.add("Oslo");
        falseAnswers.add("Shanghai");
        
        quizCard = new QuizCard(question, answer, falseAnswers);
        
        app = new Application();
        
        app.addCard(quizCard);
    }  
    
    @Test
    public void quizCardReturnsCorrectOnRightAnswer() {
        answer = new Scanner("Helsinki");
        app.rehearse(answer);
        assertEquals("Correct! The answer is " + quizCard.getCorrectAnswer() + ".", quizCard.getAnswerString(true));        
    }   
    
    @Test
    public void quizCardReturnsWrongOnWrongAnswer() {
        answer = new Scanner("Shanghai");
        app.rehearse(answer);
        assertEquals("Wrong. The correct answer is " + quizCard.getCorrectAnswer() + ".", quizCard.getAnswerString(false));        
    }     
    
    @Test
    public void quizCardDifficultyIsSetCorrectlyAtStart() {
        assertEquals(0, quizCard.getDifficulty());
    }     
    
    @Test
    public void quizCardDifficultyChangesAfterRightAnswer() {
        answer = new Scanner("Helsinki");
        app.rehearse(answer);
        assertEquals(0, quizCard.getDifficulty());        
    }   
    
    @Test
    public void quizCardDifficultyChangesAfterWrongAnswer() {
        answer = new Scanner("Shanghai");
        app.rehearse(answer);
        assertEquals(1, quizCard.getDifficulty());        
    }      
}
