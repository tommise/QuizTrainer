
package logic;

import java.util.*;
import domain.QuizCard;

public class Application {
    
    ArrayList<QuizCard> defaultDeck;
    
    public Application() {
        this.defaultDeck = new ArrayList<>();
    }
    
    public void addCard(QuizCard card) {
        this.defaultDeck.add(card);
    }
    
    public void rehearse(Scanner scanner) {
        
        System.out.println("Now rehearsing...");
        
        for (QuizCard quizCard : defaultDeck) {
            System.out.println("");    
            
            ArrayList<String> options = new ArrayList<>();
            
            options.add(quizCard.getCorrectAnswer());
            
            ArrayList<String> falseAnswers = quizCard.getFalseAnswers();
            Collections.shuffle(falseAnswers);
            options.add(falseAnswers.get(0));
            options.add(falseAnswers.get(1));
            
            Collections.shuffle(options);
            
            System.out.println(quizCard.getQuestion());
                
            for (int i = 0; i < options.size(); i++) {
                
                if (i == options.size()-1) {
                    System.out.print(options.get(i));
                    System.out.println("");
                } else {
                    System.out.print(options.get(i) + ", ");
                }
            }
            
            System.out.print("> ");
                
            String userAnswer = scanner.nextLine();
                
            if (userAnswer.isEmpty()) {
                break;
            } else if (quizCard.isCorrectAnswer(userAnswer)) {
                System.out.println(quizCard.getAnswerString(true));
                quizCard.setDifficulty(1);
            } else {
                System.out.println(quizCard.getAnswerString(false));
                quizCard.setDifficulty(-1);                
            }
        }
        System.out.println("");
        System.out.println("Leaving rehearse mode...");
    }
}
