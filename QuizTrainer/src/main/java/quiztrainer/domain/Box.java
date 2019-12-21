
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
    
     /**
     * Adds a QuizCard to the Box
     * 
     * @param quizCard  A QuizCard object to be added to the box.
     */
    
    public void addACard(QuizCard quizCard) {
        quizCards.add(quizCard);
    }
    
     /**
     * Removes a QuizCard from the Box
     * 
     * @param quizCard  A QuizCard object to be removed from the box.
     */ 
    
    public void removeACard(QuizCard quizCard) {
        quizCards.remove(quizCard);
    }  
    
    public ArrayList<QuizCard> getQuizCards() {
        return this.quizCards;
    }
}