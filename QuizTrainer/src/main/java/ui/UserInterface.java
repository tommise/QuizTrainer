
package ui;

import domain.QuizCard;
import java.util.*;
import logic.Application;

public class UserInterface {
    
    private Application app;
    private Scanner scanner;
    
    public UserInterface(Application app, Scanner scanner) {
        this.scanner = scanner;
        this.app = app;
    }
    
    public void start() {
        System.out.println("Welcome to QuizTrainer!");
        
        while (true) {
            System.out.println("");
            System.out.println("Please choose a command:");
        
            System.out.println("1 - Rehearse your cards");
            System.out.println("2 - Add a new card");
            System.out.println("q - To exit the application");
            System.out.println("");
            
            System.out.print("> ");
            String userInput = scanner.nextLine();
            
            if (userInput.equals("q")) {
                break;
            }
            
            else if (userInput.equals("1")) {
                app.rehearse(this.scanner);
            }
            
            else if (userInput.equals("2")) {
                
                System.out.println("Please write the question:");
                String question = scanner.nextLine();
                
                System.out.println("Please write the correct answer:");
                String correctAnswer = scanner.nextLine();
                
                System.out.println("Please write as many false answers as you want: (empty answer will quit)");
                
                ArrayList<String> falseAnswers = new ArrayList<>();
                
                while (true) {
                    
                    String falseAnswer = scanner.nextLine();
                    
                    if (falseAnswer.isEmpty()) {
                        break;
                    }
                    
                    falseAnswers.add(falseAnswer);
                }
                
                QuizCard newQuizCard = new QuizCard(question, correctAnswer, falseAnswers);
                
                app.addCard(newQuizCard);
            }
        }
    }
}
