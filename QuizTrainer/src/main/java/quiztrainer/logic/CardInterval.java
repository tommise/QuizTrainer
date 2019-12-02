
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

public class CardInterval {
    Random random;
    
    public CardInterval() {
        this.random = new Random();
    }
    
    public QuizCard drawANewCard(ArrayList<Box> boxes) {
        
        int nextBox = drawACardFromBoxes(boxes);
        
        // Pick a random card from the box
        
        for (Box box : boxes) {
            if (nextBox == box.getBoxNumber()) {
                ArrayList<QuizCard> drawnBox = box.getQuizCards();
                return drawnBox.get(this.random.nextInt(drawnBox.size()));
            }
        }
        
        return null;
    }
    
    public int drawACardFromBoxes(ArrayList<Box> boxes) {
        
        /// The cards will be drawn from the boxes with probabilities of 60%, 20%, 10%, 5%, 5%
        
        while (true) {
            int drawPercentage = this.random.nextInt(100);
            
            if (drawPercentage < 60 && !boxes.get(0).getQuizCards().isEmpty()) { // Box 1 60%
                return 1;
            } else if (drawPercentage < 80 && !boxes.get(1).getQuizCards().isEmpty()) { // Box 2 20%
                return 2;
            } else if (drawPercentage < 90 && !boxes.get(2).getQuizCards().isEmpty()) { // Box 3 10%
                return 3;
            } else if (drawPercentage < 95 && !boxes.get(3).getQuizCards().isEmpty()) { // Box 4 5%
                return 4;
            } else { // Box 5 5%
                if (!boxes.get(4).getQuizCards().isEmpty()) {
                    return 5;
                }
            }
        }
    } 
}