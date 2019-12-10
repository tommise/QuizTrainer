
package quiztrainer.domain;

import java.util.ArrayList;
import java.util.Collections;

public class QuizCard {
    
    int boxNumber;
    String question;
    String correctAnswer;
    ArrayList<String> falseAnswers;

    public QuizCard(String question, String correctAnswer, ArrayList<String> falseAnswers, int boxNumber) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
        this.boxNumber = boxNumber;
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
    
    public String getCorrectAnswerString() {
        return "Correct! The answer is " + this.correctAnswer + ".";
    }
    
    public String getWrongAnswerString() {
        return "Wrong. The correct answer is " + this.correctAnswer + ".";
    }  
    
    public String getQuestionAndRightAnswer() {
        return this.question + " " + this.correctAnswer;
    }
}
