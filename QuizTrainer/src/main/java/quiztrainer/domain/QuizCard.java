
package quiztrainer.domain;

import java.util.ArrayList;
import java.util.Collections;

public class QuizCard {
    
    String question;
    String correctAnswer;
    ArrayList<String> falseAnswers;

    public QuizCard(String question, String correctAnswer, ArrayList<String> falseAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
    }
    
    public String getQuestion() {
        return this.question;
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
    
    public ArrayList<String> generateChoices() {
        ArrayList<String> choices = new ArrayList<>();

        choices.add(this.correctAnswer);
        Collections.shuffle(falseAnswers);
        choices.add(falseAnswers.get(0));
        choices.add(falseAnswers.get(1));

        Collections.shuffle(choices);
        
        return choices;
    }
}
