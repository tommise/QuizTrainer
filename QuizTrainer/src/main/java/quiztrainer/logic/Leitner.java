
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

public class Leitner {
    
    ArrayList<Box> boxes;
    Box box1, box2, box3, box4, box5;
    
    public Leitner() {
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
    
    public void addANewCard(QuizCard card) {
        this.box1.add(card);
    }
    
    public void moveCardUp(QuizCard card) {
        Box currentBox = getCurrentBox(card);
        int currentBoxNumber = currentBox.getBoxNumber();
        
        if (currentBoxNumber == 5) {
            return;
        }
        
        currentBox.remove(card);
        
        if (currentBoxNumber == 4) {
            this.box5.add(card);
        } else if (currentBoxNumber == 3) {
            this.box4.add(card);
        } else if (currentBoxNumber == 2) {
            this.box3.add(card);
        } else if (currentBoxNumber == 1) {
            this.box2.add(card);
        }
    }
    
    public void moveCardToBoxOne(QuizCard card) {
        Box currentBox = getCurrentBox(card);
        int currentBoxNumber = currentBox.getBoxNumber();
        
        if (currentBoxNumber == 1) {
            return;
        }
        
        currentBox.remove(card);

        this.box1.add(card);  
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
    
    public ArrayList<Box> getBoxes() {
        return this.boxes;
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
}
