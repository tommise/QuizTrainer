
package quiztrainer.utils;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.Deck;
import quiztrainer.domain.QuizCard;

public class Leitner {
    
    /**
     * Moves a QuizCard to a higher box within range 1..5.
     * If current box is number 5, return.
     *
     * @param   card   A QuizCard which is going to be moved to a different box.
     * @param   currentDeck   A Deck where the QuizCard is currently.
     */
    
    public void moveCardUp(QuizCard card, Deck currentDeck) {
        Box currentBox = currentDeck.getCurrentBox(card);
        int currentBoxNumber = currentBox.getBoxNumber();
        
        if (currentBoxNumber == 5) {
            return;
        }
        
        currentBox.removeACard(card);
        
        ArrayList<Box> boxes = currentDeck.getBoxes();
        
        int nextBox = currentBoxNumber + 1;
        
        for (Box box : boxes) {
            if (box.getBoxNumber() == nextBox) {
                box.addACard(card);
            }
        }
        card.setBoxNumber(nextBox);
    }
    
    /**
     * Moves a QuizCard to a box number 1.
     * If current box is number 1, return.
     *
     * @param   card   A QuizCard which is going to be moved to box number one.
     * @param   currentDeck   A Deck where the QuizCard is currently.
     */
    
    public void moveCardToBoxOne(QuizCard card, Deck currentDeck) {
        Box currentBox = currentDeck.getCurrentBox(card);
        int currentBoxNumber = currentBox.getBoxNumber();
        
        if (currentBoxNumber == 1) {
            return;
        }
        
        currentBox.removeACard(card);
        currentDeck.addACard(card, 1);
        card.setBoxNumber(1);
    }
}
