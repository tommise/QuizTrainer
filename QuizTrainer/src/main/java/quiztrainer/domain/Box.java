
package quiztrainer.domain;

import java.util.*;

public class Box {
    
    int boxNumber;
    ArrayList<QuizCard> quizCards;
    
    public Box(int number) {
        this.boxNumber = number;
        this.quizCards = new ArrayList<>();
    }
    
    public void setQuizCards(ArrayList<QuizCard> quizCards) {
        this.quizCards = quizCards;
    }
    
    public int getBoxNumber() {
        return this.boxNumber;
    }
    
    public void add(QuizCard quizCard) {
        quizCards.add(quizCard);
    }

    public void remove(QuizCard quizCard) {
        quizCards.remove(quizCard);
    }  
    
    public ArrayList<QuizCard> getQuizCards() {
        return this.quizCards;
    }
}