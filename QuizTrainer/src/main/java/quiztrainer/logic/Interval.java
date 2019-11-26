
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

public class Interval {
    Random random;
    ArrayList<QuizCard> box1, box2, box3, box4, box5;
    
    public Interval() {
        this.random = new Random();
    }
    
    public QuizCard drawANewCard(ArrayList<Box> boxes) {
        
        box1 = boxes.get(0).getQuizCards();
        box2 = boxes.get(1).getQuizCards();
        box3 = boxes.get(2).getQuizCards();
        box4 = boxes.get(3).getQuizCards();
        box5 = boxes.get(4).getQuizCards();
        
        int nextBox = drawACardFromBoxes();
        
        // Pick a random card from the box
        
        if (nextBox == 1) {
            return box1.get(this.random.nextInt(box1.size()));
        } else if (nextBox == 2) {
            return box2.get(this.random.nextInt(box2.size()));         
        } else if (nextBox == 3) {
            return box3.get(this.random.nextInt(box3.size()));            
        } else if (nextBox == 4) {
            return box4.get(this.random.nextInt(box4.size()));            
        } else {
            return box5.get(this.random.nextInt(box5.size()));            
        }
    }
    
    public int drawACardFromBoxes() {
        
        /// The cards will be drawn from the boxes with probabilities of 60%, 20%, 10%, 5%, 5%
        while (true) {
            int drawPercentage = this.random.nextInt(100);
            
            if (drawPercentage < 60 && !box1.isEmpty()) { // Box 1 60%
                return 1;
            } else if (drawPercentage < 80 && !box2.isEmpty()) { // Box 2 20%
                return 2;
            } else if (drawPercentage < 90 && !box3.isEmpty()) { // Box 3 10%
                return 3;
            } else if (drawPercentage < 95 && !box4.isEmpty()) { // Box 4 5%
                return 4;
            } else { // Box 5 5%
                if (!box5.isEmpty()) {
                    return 5;
                }
            }
        }
    } 
}