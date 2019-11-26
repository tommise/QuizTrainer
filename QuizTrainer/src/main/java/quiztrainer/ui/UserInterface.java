
package quiztrainer.ui;

import java.util.*;
import quiztrainer.domain.QuizCard;
import quiztrainer.logic.Trainer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface extends Application {
    private Scene rehearseScene;
    private Scene addANewCardScene;
    private Scene mainScene;
    
    @Override
    public void init() throws Exception {
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Trainer trainer = new Trainer();
        GUIHelper guiHelper = new GUIHelper();
        
        // Primary scene
        VBox primaryPane = new VBox(20);
        primaryPane.setPadding(new Insets(10));
        primaryPane.setPadding(new Insets(10));
        Label welcomeLabel = new Label("Welcome to QuizTrainer!");
        Button primaryAddANewCardButton = new Button("Add a new card");
        Button primaryRehearseButton = new Button("Rehearse");
        Label errorLabel = new Label("");
        primaryPane.getChildren().addAll(welcomeLabel, primaryAddANewCardButton, primaryRehearseButton, errorLabel);
        
        // Scene for adding a new card
        VBox addANewCardPane = new VBox(20);
        addANewCardPane.setPadding(new Insets(10));
        addANewCardPane.setPadding(new Insets(10));
        
        addANewCardScene = new Scene(addANewCardPane, 500, 500);
        
        primaryAddANewCardButton.setOnAction(e-> {
            primaryStage.setScene(addANewCardScene);
        });
        
        VBox newQuestionPane = new VBox(20);
        newQuestionPane.setPadding(new Insets(10));
        newQuestionPane.setPadding(new Insets(10));
        Label newQuestionLabel = new Label("What is the question?");
        TextField newQuestionInput = new TextField("");
        newQuestionPane.getChildren().addAll(newQuestionLabel, newQuestionInput);
        
        VBox newAnswerPane = new VBox(20);
        newAnswerPane.setPadding(new Insets(10));
        newAnswerPane.setPadding(new Insets(10));
        Label rightAnswerLabel = new Label("What is the right answer?");
        TextField newAnswerInput = new TextField(""); 
        newAnswerPane.getChildren().addAll(rightAnswerLabel, newAnswerInput);
        
        VBox wrongChoicesPane = new VBox(20);
        wrongChoicesPane.setPadding(new Insets(10));
        wrongChoicesPane.setPadding(new Insets(10));
        Label wrongChoicesLabel = new Label("Type three (3) false answers.");
        TextField wrongChoice1Input = new TextField(""); 
        TextField wrongChoice2Input = new TextField(""); 
        TextField wrongChoice3Input = new TextField("");         
        wrongChoicesPane.getChildren().addAll(wrongChoicesLabel, wrongChoice1Input, wrongChoice2Input, wrongChoice3Input);        
        
        Label falseInputLabel = new Label("");
        Button addANewCardButton = new Button("Add a new question");
        
        addANewCardButton.setOnAction(e-> {
            String question = newQuestionInput.getText();
            String answer = newAnswerInput.getText();
            String choice1 = wrongChoice1Input.getText();
            String choice2 = wrongChoice2Input.getText();
            String choice3 = wrongChoice3Input.getText();
            ArrayList<String> choices = new ArrayList<>();
            choices.add(choice1);
            choices.add(choice2);
            choices.add(choice3);
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(question);
            inputs.add(answer);
            inputs.add(choice1);
            inputs.add(choice2);
            inputs.add(choice3);       
            
            if (guiHelper.inputsAreValid(inputs)) {
                QuizCard newQuizCard = new QuizCard(question, answer, choices);
                trainer.addCard(newQuizCard);
                newQuestionInput.setText("");
                newAnswerInput.setText("");
                wrongChoice1Input.setText("");
                wrongChoice2Input.setText("");
                wrongChoice3Input.setText(""); 
                falseInputLabel.setText("");
                errorLabel.setText("");  
                primaryStage.setScene(mainScene);
            } else {
                falseInputLabel.setText("One of the input is empty.");
                falseInputLabel.setTextFill(Color.RED);
            }
        });
        
        addANewCardPane.getChildren().addAll(newQuestionPane, newAnswerPane, wrongChoicesPane, addANewCardButton, falseInputLabel);
        
        // Scene for rehearsing 
        VBox rehearsePane = new VBox(20);
        rehearsePane.setPadding(new Insets(10));
        rehearsePane.setPadding(new Insets(10));
        
        rehearseScene = new Scene(rehearsePane, 500, 500);
        
        HBox answerPane = new HBox(20);
        answerPane.setPadding(new Insets(10));
        answerPane.setPadding(new Insets(10));
        
        Label question = new Label("");
        Button answer1Button = new Button("");
        Button answer2Button = new Button("");
        Button answer3Button = new Button("");
        
        answerPane.getChildren().addAll(answer1Button, answer2Button, answer3Button);

        Label resultLabel = new Label("");
        
        answer1Button.setOnAction(e-> {
            String answer = answer1Button.getText();
            QuizCard quizCard = trainer.leitner.getACard(question.getText());
            if (quizCard.isCorrectAnswer(answer)) {
                resultLabel.setText(trainer.correctAnswer(quizCard));             
            } else {
                resultLabel.setText(trainer.wrongAnswer(quizCard));
            }
        });
        
        answer2Button.setOnAction(e-> {
            String answer = answer2Button.getText();     
            QuizCard quizCard = trainer.leitner.getACard(question.getText());
            if (quizCard.isCorrectAnswer(answer)) {
                resultLabel.setText(trainer.correctAnswer(quizCard));
            } else {
                resultLabel.setText(trainer.wrongAnswer(quizCard));
            } 
        });

        answer3Button.setOnAction(e-> {
            String answer = answer3Button.getText();  
            QuizCard quizCard = trainer.leitner.getACard(question.getText());            
            if (quizCard.isCorrectAnswer(answer)) {
                resultLabel.setText(trainer.correctAnswer(quizCard));
            } else {
                resultLabel.setText(trainer.wrongAnswer(quizCard));
            }  
        });        
        
        Button nextQuestionButton = new Button("Next question ->");
        
        primaryRehearseButton.setOnAction(e-> {
            QuizCard quizCard = trainer.getNextQuestion();
            if (quizCard == null) {
                errorLabel.setText("There are no cards to rehearse.");
                errorLabel.setTextFill(Color.RED);             
            } else {
                question.setText(quizCard.getQuestion());
                ArrayList<String> nextCardChoices = quizCard.generateChoices();
                answer1Button.setText(nextCardChoices.get(0));
                answer2Button.setText(nextCardChoices.get(1)); 
                answer3Button.setText(nextCardChoices.get(2));
                resultLabel.setText(""); 
                errorLabel.setText("");                
                primaryStage.setScene(rehearseScene); 
            }
        });
        
        nextQuestionButton.setOnAction(e-> {
            QuizCard nextCard = trainer.getNextQuestion();
            question.setText(nextCard.getQuestion());
            ArrayList<String> nextCardChoices = nextCard.generateChoices();
            answer1Button.setText(nextCardChoices.get(0));
            answer2Button.setText(nextCardChoices.get(1)); 
            answer3Button.setText(nextCardChoices.get(2));
            resultLabel.setText("");            
        });
        
        rehearsePane.getChildren().addAll(question, answerPane, resultLabel, nextQuestionButton);
        
        mainScene = new Scene(primaryPane, 500, 500);
        
        primaryStage.setTitle("QuizTrainer");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        System.out.println("Exiting...");
    }    
    
    public static void main(String[] args) {
        launch(args);
    }
}
