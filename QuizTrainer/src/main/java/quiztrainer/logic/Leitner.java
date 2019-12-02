
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.Deck;
import quiztrainer.domain.QuizCard;

public class Leitner {
    
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
    }
    
    public void moveCardToBoxOne(QuizCard card, Deck currentDeck) {
        Box currentBox = currentDeck.getCurrentBox(card);
        int currentBoxNumber = currentBox.getBoxNumber();
        
        if (currentBoxNumber == 1) {
            return;
        }
        
        currentBox.removeACard(card);
        
        currentDeck.addACard(card, 1);  
    }
}
