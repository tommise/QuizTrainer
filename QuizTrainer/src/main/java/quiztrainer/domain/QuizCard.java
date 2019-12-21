
package quiztrainer.domain;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Java class for QuizCard
 */
public class QuizCard {
    
    int boxNumber;
    int totalAnswers;      
    int answeredRight;  
    String question;
    String correctAnswer;
    ArrayList<String> falseAnswers;
    int deckId;

    public QuizCard(String question, String correctAnswer, ArrayList<String> falseAnswers, int boxNumber, int totalRight, int totalAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
        this.boxNumber = boxNumber;
        this.answeredRight = totalRight;
        this.totalAnswers = totalAnswers;
    }
    
    /**
     * Generates three answer choices to choose from.
     * A correct answer and two random false answers
     * will be shuffled and given to the user to choose from.
     * 
     * @return an ArrayList of three possible choices as Strings.
     */  
    
    public ArrayList<String> generateChoices() {
        ArrayList<String> choices = new ArrayList<>();

        choices.add(this.correctAnswer);
        Collections.shuffle(falseAnswers);
        choices.add(falseAnswers.get(0));
        choices.add(falseAnswers.get(1));

        Collections.shuffle(choices);
        
        return choices;
    }
    
    public String getQuestion() {
        return this.question;
    }
    
    public int getBoxNumber() {
        return this.boxNumber;
    }
    
    public int setBoxNumber(int boxNumber) {
        return this.boxNumber = boxNumber;
    }    
    
    public Boolean isCorrectAnswer(String answer) {
        return answer.equals(this.correctAnswer);
    }
    
    public ArrayList<String> getFalseAnswers() {
        return this.falseAnswers;
    }
    
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
    
    public int getTotalAnswers() {
        return this.totalAnswers;
    }
    
    public void setTotalAnswers(int amount) {
        this.totalAnswers = amount;
    }
    
    public int getTotalAnsweredRight() {
        return this.answeredRight;
    }
    
    public void setTotalAnsweredRight(int amount) {
        this.answeredRight = amount;
    } 
    
    public int getTotalAnsweredWrong() {
        return this.totalAnswers - this.answeredRight;
    }
    
    public String getCorrectAnswerString() {
        return "Correct! The answer is " + this.correctAnswer + ".";
    }
    
    public String getWrongAnswerString() {
        return "Wrong. The correct answer is " + this.correctAnswer + ".";
    }
    
    public String getQuestionAndRightAnswer() {
        return this.question + " " + this.correctAnswer;
    }
    
    public int getDeckId() {
        return this.deckId;
    }
        
    public void setDeckId(int id) {
        this.deckId = id;
    }
}
