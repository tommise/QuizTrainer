
package quiztrainer.logic;

import quiztrainer.domain.Box;
import quiztrainer.domain.QuizCard;
import java.util.ArrayList;

public class Trainer {
    
    public Leitner leitner;
    private Interval interval;
    
    public Trainer() {
        this.leitner = new Leitner();
        this.interval = new Interval();
    }
    
    public void addCard(QuizCard quizCard) {                
        this.leitner.addANewCard(quizCard);
    }
    
    public String correctAnswer(QuizCard quizCard) {
        this.leitner.moveCardUp(quizCard);
        return "Correct! The answer is " + quizCard.getCorrectAnswer() + ".";
    }
    
    public String wrongAnswer(QuizCard quizCard) {
        this.leitner.moveCardToBoxOne(quizCard);
        return "Wrong. The correct answer is " + quizCard.getCorrectAnswer() + ".";
    }

    public QuizCard getNextQuestion() {
        ArrayList<Box> boxes = this.leitner.getBoxes();
        ArrayList<Integer> boxSizes = new ArrayList<>();
        int totalSize = 0;
            
        for (Box box: boxes) {
            int sizeOfBox = box.getQuizCards().size();
            boxSizes.add(sizeOfBox);
            totalSize += sizeOfBox;
        }
        
        if (totalSize == 0) {
            return null;
        }
            
        QuizCard nextCard = this.interval.drawANewCard(boxes, boxSizes, totalSize);
        
        return nextCard;
    } 
}
