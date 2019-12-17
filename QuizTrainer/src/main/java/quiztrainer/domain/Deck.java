
package quiztrainer.domain;

import java.util.*;
import quiztrainer.logic.CardInterval;
import quiztrainer.logic.Leitner;

public class Deck {
    
    ArrayList<Box> boxes;
    Box box1, box2, box3, box4, box5;
    Leitner leitner;
    CardInterval interval;
    String deckName;
    
    public Deck(String deckName) {
        this.deckName = deckName;
        this.leitner = new Leitner();
        this.interval = new CardInterval();
        
        this.boxes = new ArrayList<>();
        
        this.box1 = new Box(1);
        this.box2 = new Box(2);
        this.box3 = new Box(3);
        this.box4 = new Box(4);
        this.box5 = new Box(5);
        
        this.boxes.add(box1);
        this.boxes.add(box2);
        this.boxes.add(box3);
        this.boxes.add(box4);
        this.boxes.add(box5);  
    }
    
     /**
     * Adds a new card to box number one.
     * 
     * @param card  A QuizCard to be added to box one.
     */ 
    
    public void addANewCard(QuizCard card) {
        this.box1.addACard(card);
    }
    
     /**
     * Adds a card to a given box.
     * 
     * @param card  A QuizCard to be added to a different box.
     * @param boxNumber A Box number where the QuizCard is going to be added.
     */ 
    
    public void addACard(QuizCard card, int boxNumber) {
        
        for (Box box : boxes) {
            if (box.getBoxNumber() == boxNumber) {
                box.addACard(card);
            }
        }
    }    
    
    public Box getCurrentBox(QuizCard card) {

        for (Box box : this.boxes) {
            if (box.getQuizCards().isEmpty()) {
                continue;
            }
            
            ArrayList<QuizCard> kortit = box.getQuizCards();
            if (kortit.contains(card)) {
                return box;
            }
        }
        
        return null;
    }
    
    public QuizCard getACard(String question) {
        
        for (Box box : this.boxes) {
            if (box.getQuizCards().isEmpty()) {
                continue;
            }
            
            ArrayList<QuizCard> kortit = box.getQuizCards();
            for (QuizCard quizCard : kortit) {
                if (quizCard.getQuestion().equals(question)) {
                    return quizCard;
                }
            }
        }
        
        return null;
    }
    
     /**
     * Fetches a next card to be rehearsed.
     * If there are cards available uses 
     * interval.drawANewCard() with the help
     * of object from the CardInterval class.
     * 
     * @return next QuizCard to be rehearsed.
     */
    
    public QuizCard drawNextQuestion() {
        int totalSize = 0;
            
        for (Box box: boxes) {
            int sizeOfBox = box.getQuizCards().size();
            totalSize += sizeOfBox;
        }
        
        if (totalSize == 0) {
            return null;
        }
            
        QuizCard nextCard = this.interval.drawANewCard(boxes);
        
        return nextCard;
    } 
        
    public ArrayList<Box> getBoxes() {
        return this.boxes;
    }
}
