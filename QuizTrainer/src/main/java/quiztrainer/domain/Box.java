
package quiztrainer.domain;

import java.util.*;

public class Box {
    
    int boxNumber;
    ArrayList<QuizCard> quizCards;
    
    public Box(int number) {
        this.boxNumber = number;
        this.quizCards = new ArrayList<>();
    }
    
    public int getBoxNumber() {
        return this.boxNumber;
    }
    
    public void addACard(QuizCard quizCard) {
        quizCards.add(quizCard);
    }

    public void removeACard(QuizCard quizCard) {
        quizCards.remove(quizCard);
    }  
    
    public ArrayList<QuizCard> getQuizCards() {
        return this.quizCards;
    }
}