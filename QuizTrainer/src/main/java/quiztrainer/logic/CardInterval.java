
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

public class CardInterval {
    Random random;
    
    public CardInterval() {
        this.random = new Random();
    }
    
    /**
     * Draws a random card from the box which was chosen 
     * with a method drawACardFromBoxes().
     *
     * @param   boxes   All boxes in an ArrayList
     * 
     * @return random QuizCard from a drawn box
     */
    
    public QuizCard drawANewCard(ArrayList<Box> boxes) {
        
        int nextBox = drawACardFromBoxes(boxes);
        
        for (Box box : boxes) {
            if (nextBox == box.getBoxNumber()) {
                ArrayList<QuizCard> drawnBox = box.getQuizCards();
                return drawnBox.get(this.random.nextInt(drawnBox.size()));
            }
        }
        
        return null;
    }
    
     /**
     * Draws a random box from 1...5 based on the probabilities 
     * 60% (Box 1), 20% (Box 2), 10% (Box 3), 5% (Box 4), 5% (Box 5).
     * If a box is empty a new draw will be made.
     * 
     * @param   boxes   All boxes in an ArrayList
     * 
     * @return random box number as an int based on the probabilities
     */
    
    public int drawACardFromBoxes(ArrayList<Box> boxes) {
        
        while (true) {
            int drawPercentage = this.random.nextInt(100);
            
            if (drawPercentage < 60 && !boxes.get(0).getQuizCards().isEmpty()) {
                return 1;
            } else if (drawPercentage < 80 && !boxes.get(1).getQuizCards().isEmpty()) {
                return 2;
            } else if (drawPercentage < 90 && !boxes.get(2).getQuizCards().isEmpty()) {
                return 3;
            } else if (drawPercentage < 95 && !boxes.get(3).getQuizCards().isEmpty()) {
                return 4;
            } else {
                if (!boxes.get(4).getQuizCards().isEmpty()) {
                    return 5;
                }
            }
        }
    } 
}