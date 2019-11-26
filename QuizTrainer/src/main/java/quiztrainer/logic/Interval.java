
package quiztrainer.logic;

import java.util.*;
import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;

public class Interval {
    Random random;
    int box1Size, box2Size, box3Size, box4Size, box5Size;
    
    public Interval() {
        this.random = new Random();
    }
    
    public QuizCard drawANewCard(ArrayList<Box> boxes, ArrayList<Integer> boxSizes, int totalSize) {
        
        box1Size = boxSizes.get(0);
        box2Size = boxSizes.get(1);
        box3Size = boxSizes.get(2);
        box4Size = boxSizes.get(3);
        box5Size = boxSizes.get(4);
        
        int nextBox = drawACardFromBoxes(boxSizes, totalSize);
        
        // Pick a random card from the box
        
        if (nextBox == 1) {
            ArrayList<QuizCard> box1Cards = boxes.get(0).getQuizCards();
            return box1Cards.get(this.random.nextInt(box1Size));
        } else if (nextBox == 2) {
            ArrayList<QuizCard> box2Cards = boxes.get(1).getQuizCards();
            return box2Cards.get(this.random.nextInt(box2Size));         
        } else if (nextBox == 3) {
            ArrayList<QuizCard> box3Cards = boxes.get(2).getQuizCards();
            return box3Cards.get(this.random.nextInt(box3Size));            
        } else if (nextBox == 4) {
            ArrayList<QuizCard> box4Cards = boxes.get(3).getQuizCards();
            return box4Cards.get(this.random.nextInt(box4Size));            
        } else {
            ArrayList<QuizCard> box5Cards = boxes.get(4).getQuizCards(); 
            return box5Cards.get(this.random.nextInt(box5Size));            
        }
    }
    
    public int drawACardFromBoxes(ArrayList<Integer> boxSizes, int totalSize) {
        
        /// The cards will be drawn from the boxes with probabilities of 60%, 20%, 10%, 5%, 5%
        
        while (true) {
            
            if (totalSize == 0) {
                break;
            }
            
            int drawPercentage = this.random.nextInt(100);
            
            if (drawPercentage < 60) { // Box 1 60%
                if (box1Size == 0) {
                    continue;
                }
                return 1;
            } else if (drawPercentage < 80) { // Box 2 20%
                if (box2Size == 0) {
                    continue;
                }
                return 2;
            } else if (drawPercentage < 90) { // Box 3 10%
                if (box3Size == 0) {
                    continue;
                }
                return 3;
            } else if (drawPercentage < 95) { // Box 4 5%
                if (box4Size == 0) {
                    continue;
                }
                return 4;
            } else { // Box 5 5%
                if (box5Size == 0) {
                    continue;
                }
                return 5;
            }
        }

        return 0;
    } 
}
