
package domain;

import java.util.ArrayList;

public class QuizCard {
    
    String question;
    String correctAnswer;
    int difficulty;
    ArrayList<String> falseAnswers;

    public QuizCard(String question, String correctAnswer, ArrayList<String> falseAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
        this.difficulty = 0;
    }
    
    public String getQuestion() {
        return this.question;
    }
    
    public Boolean isCorrectAnswer(String answer) {
        return answer.equals(this.correctAnswer);
    }
    
    public String getAnswerString(boolean answer) {
        if (answer == true) {
            return "Correct! The answer is " + this.correctAnswer + ".";
        } else {
            return "Wrong. The correct answer is " + this.correctAnswer + ".";
        }
    }
    
    public ArrayList<String> getFalseAnswers() {
        return this.falseAnswers;
    }
    
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
    
    public int getDifficulty() {
        return this.difficulty;
    }
    
    public void setDifficulty(int result) {
        if (this.difficulty - result <= 0) {
            this.difficulty = 0;
        } else if (result < 0) {
            this.difficulty -= result;
        } else {
            this.difficulty += result;
        }
    }
}
