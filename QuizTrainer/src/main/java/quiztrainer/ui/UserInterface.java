
package quiztrainer.ui;

import java.util.*;
import quiztrainer.domain.Deck;
import quiztrainer.domain.QuizCard;
import quiztrainer.domain.QuizTrainerService;

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
    private QuizTrainerService trainer;
    private Deck deck;
    
    private Scene startingScene;
    private Scene loginScene;
    private Scene signupScene;    
    private Scene mainScene;
    private Scene rehearseScene;
    private Scene addANewCardScene;
    private Label currentUserLabel = new Label("");  
    
    @Override
    public void init() throws Exception {
        this.trainer = new QuizTrainerService();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        GUIHelper guiHelper = new GUIHelper();
        
        // Starting scene
        
        VBox startingPane = new VBox(20);
        startingPane.setPadding(new Insets(20));
        startingPane.setPadding(new Insets(20));        
        Label welcomeLabel = new Label("Welcome to QuizTrainer!"); 
        Button loginStartSceneButton = new Button("Login");
        Button signupStartSceneButton = new Button("Signup");
        
        startingPane.getChildren().addAll(welcomeLabel, loginStartSceneButton, signupStartSceneButton); 
        startingScene = new Scene(startingPane, 500, 500); 
        
        loginStartSceneButton.setOnAction(e-> {
            primaryStage.setScene(loginScene);
        });

        signupStartSceneButton.setOnAction(e-> {
            primaryStage.setScene(signupScene);
        });        
        
        // Login scene
        
        VBox loginPane = new VBox(20); 
        loginPane.setPadding(new Insets(20));
        loginPane.setPadding(new Insets(20));         
        Label usernameInstructionLabel = new Label("Login with your username:") ;   
        TextField usernameInput = new TextField();        
        Button loginButton = new Button("Login");
        Button returnFromLoginButton = new Button("<- Return to menu"); 
        Label loginWarning = new Label("");
        
        loginPane.getChildren().addAll(usernameInstructionLabel, usernameInput, loginButton, returnFromLoginButton, loginWarning); 
        loginScene = new Scene(loginPane, 500, 500);
        
        loginButton.setOnAction(e-> {
            String username = usernameInput.getText();
            if (trainer.login(username)){
                usernameInput.setText("");
                loginWarning.setText("");
                currentUserLabel.setText("Welcome " + trainer.getCurrentUser().getName() + "!");
                deck = this.trainer.initDeck();
                primaryStage.setScene(mainScene);  
            } else {
                loginWarning.setText("User does not exist.");
                loginWarning.setTextFill(Color.RED);                
            }
        });
        
        returnFromLoginButton.setOnAction(e-> {
            usernameInput.setText("");
            loginWarning.setText("");            
            primaryStage.setScene(startingScene);
        });        
        
        // Signup scene
        
        VBox signupPane = new VBox(20);
        signupPane.setPadding(new Insets(20));
        signupPane.setPadding(new Insets(20)); 
        Label newUserInstruction = new Label("Choose a username:");   
        TextField newUsernameInput = new TextField(); 
        Label newNameInstruction = new Label("Please fill in your name:");         
        TextField newNameInput = new TextField(); 
        Button signupButton = new Button("Signup"); 
        Button returnFromSignupButton = new Button("<- Return to menu"); 
        Label signupMessage = new Label("");
 
        signupPane.getChildren().addAll(newUserInstruction, newUsernameInput, newNameInstruction, newNameInput, signupButton, signupMessage, returnFromSignupButton);
        signupScene = new Scene(signupPane, 500, 500);
        
        signupButton.setOnAction(e-> {
            String username = newUsernameInput.getText();
            String name = newNameInput.getText();
            
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(username);
            inputs.add(name);
            
            if (!guiHelper.inputsAreValid(inputs)) {
                signupMessage.setText("Username contains invalid characters or form is empty.");
                signupMessage.setTextFill(Color.RED);
            } else if (trainer.addANewUser(username, name)) {
                signupMessage.setText("You have succesfully created an account with username: " + newUsernameInput.getText());
                signupMessage.setTextFill(Color.BLACK);
                newUsernameInput.setText("");
                newNameInput.setText("");
            } else {
                signupMessage.setText("Username is already taken.");
                signupMessage.setTextFill(Color.RED);
            }
        });
            
        returnFromSignupButton.setOnAction(e-> {
            newUsernameInput.setText("");
            newNameInput.setText("");            
            signupMessage.setText("");  
            primaryStage.setScene(startingScene);
        });
        
        // Primary scene
        
        VBox primaryPane = new VBox(20);
        primaryPane.setPadding(new Insets(20));
        primaryPane.setPadding(new Insets(20));
        Button primaryAddANewCardButton = new Button("Add a new card");
        Button primaryRehearseButton = new Button("Rehearse");
        Button logoutButton = new Button("Logout");
        Label errorLabel = new Label("");
        primaryPane.getChildren().addAll(currentUserLabel, primaryAddANewCardButton, primaryRehearseButton, errorLabel, logoutButton);

        logoutButton.setOnAction(e-> {
            trainer.logout();
            deck = null;
            currentUserLabel.setText("");
            primaryStage.setScene(startingScene);
        });
                
        // Scene for adding a new card
        
        VBox addANewCardPane = new VBox(20);
        addANewCardPane.setPadding(new Insets(20));
        addANewCardPane.setPadding(new Insets(20));
        Button returnFromAddButton = new Button("<- Return to menu");        
        
        addANewCardScene = new Scene(addANewCardPane, 500, 500);
        
        primaryAddANewCardButton.setOnAction(e-> {
            primaryStage.setScene(addANewCardScene);
        });
        
        VBox newQuestionPane = new VBox(20);
        Label newQuestionLabel = new Label("What is the question?");
        TextField newQuestionInput = new TextField("");
        newQuestionPane.getChildren().addAll(newQuestionLabel, newQuestionInput);
        
        VBox newAnswerPane = new VBox(20);
        Label rightAnswerLabel = new Label("What is the right answer?");
        TextField newAnswerInput = new TextField(""); 
        newAnswerPane.getChildren().addAll(rightAnswerLabel, newAnswerInput);
        
        VBox wrongChoicesPane = new VBox(20);
        Label wrongChoicesLabel = new Label("Type three (3) false answers.");
        TextField wrongChoice1Input = new TextField(""); 
        TextField wrongChoice2Input = new TextField(""); 
        TextField wrongChoice3Input = new TextField("");         
        wrongChoicesPane.getChildren().addAll(wrongChoicesLabel, wrongChoice1Input, wrongChoice2Input, wrongChoice3Input);
        
        VBox addButtonsPane = new VBox(20);
        
        HBox addACardButtonPane = new HBox(10);
        Button addANewCardButton = new Button("Add a new question");
        Label falseInputLabel = new Label("");
        addACardButtonPane.getChildren().addAll(addANewCardButton, falseInputLabel);
        
        addButtonsPane.getChildren().addAll(addACardButtonPane, returnFromAddButton);
        
        addANewCardButton.setOnAction(e-> {
            String question = newQuestionInput.getText();
            String answer = newAnswerInput.getText();
            ArrayList<String> choices = new ArrayList<>();
            choices.add(wrongChoice1Input.getText());
            choices.add(wrongChoice2Input.getText());
            choices.add(wrongChoice3Input.getText());
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(question);
            inputs.add(answer);
            inputs.add(wrongChoice1Input.getText());
            inputs.add(wrongChoice2Input.getText());
            inputs.add(wrongChoice3Input.getText());       
            
            if (guiHelper.inputsAreValid(inputs)) {
                QuizCard newQuizCard = new QuizCard(question, answer, choices, 1);
                this.trainer.addANewQuizCard(newQuizCard);                
                deck.addANewCard(newQuizCard);
                newQuestionInput.setText("");
                newAnswerInput.setText("");
                wrongChoice1Input.setText("");
                wrongChoice2Input.setText("");
                wrongChoice3Input.setText(""); 
                falseInputLabel.setText("");
                errorLabel.setText("");  
                primaryStage.setScene(mainScene);
            } else {
                falseInputLabel.setText("One of the forms is empty.");
                falseInputLabel.setTextFill(Color.RED);
            }
        });
        
        returnFromAddButton.setOnAction(e-> {
            newQuestionInput.setText("");
            newAnswerInput.setText("");
            wrongChoice1Input.setText("");
            wrongChoice2Input.setText("");
            wrongChoice3Input.setText(""); 
            falseInputLabel.setText("");
            errorLabel.setText(""); 
            primaryStage.setScene(mainScene);
        });
        
        addANewCardPane.getChildren().addAll(newQuestionPane, newAnswerPane, wrongChoicesPane, addButtonsPane);
               
        // Scene for rehearsing 
        
        VBox rehearsePane = new VBox(20);
        rehearsePane.setPadding(new Insets(20));
        rehearsePane.setPadding(new Insets(20));
        
        rehearseScene = new Scene(rehearsePane, 500, 500);
        
        HBox answerPane = new HBox(20);        
        Label question = new Label("");
        Button answer1Button = new Button("");
        Button answer2Button = new Button("");
        Button answer3Button = new Button("");
        
        answerPane.getChildren().addAll(answer1Button, answer2Button, answer3Button);

        Label resultLabel = new Label("");
        Button nextQuestionButton = new Button("Next question ->");
        Button returnFromRehearseButton = new Button("<- Return to menu");
        rehearsePane.getChildren().addAll(question, answerPane, resultLabel, returnFromRehearseButton);
        
        primaryRehearseButton.setOnAction(e-> {
            QuizCard quizCard = deck.getNextQuestion();
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
        
        answer1Button.setOnAction(e-> {
            String answer = answer1Button.getText();
            QuizCard quizCard = deck.getACard(question.getText());
            if (quizCard.isCorrectAnswer(answer)) {  
                this.trainer.correctAnswer(quizCard, deck);                
                resultLabel.setText(quizCard.getCorrectAnswerString());
            } else {     
                this.trainer.wrongAnswer(quizCard, deck);
                resultLabel.setText(quizCard.getWrongAnswerString());
            }
            rehearsePane.getChildren().add(3, nextQuestionButton);
            rehearsePane.getChildren().remove(answerPane);
        });
        
        answer2Button.setOnAction(e-> {
            String answer = answer2Button.getText();     
            QuizCard quizCard = deck.getACard(question.getText());
            if (quizCard.isCorrectAnswer(answer)) {
                this.trainer.correctAnswer(quizCard, deck);                
                resultLabel.setText(quizCard.getCorrectAnswerString());
            } else {
                this.trainer.wrongAnswer(quizCard, deck);                
                resultLabel.setText(quizCard.getWrongAnswerString());
            } 
            rehearsePane.getChildren().add(3, nextQuestionButton);
            rehearsePane.getChildren().remove(answerPane);        
        });

        answer3Button.setOnAction(e-> {
            String answer = answer3Button.getText();  
            QuizCard quizCard = deck.getACard(question.getText());            
            if (quizCard.isCorrectAnswer(answer)) {
                this.trainer.correctAnswer(quizCard, deck);                
                resultLabel.setText(quizCard.getCorrectAnswerString());
            } else {
                this.trainer.wrongAnswer(quizCard, deck);
                resultLabel.setText(quizCard.getWrongAnswerString());
            }  
            rehearsePane.getChildren().add(3, nextQuestionButton);
            rehearsePane.getChildren().remove(answerPane);
        });        
        
        nextQuestionButton.setOnAction(e-> {
            QuizCard nextCard = deck.getNextQuestion();
            question.setText(nextCard.getQuestion());
            ArrayList<String> nextCardChoices = nextCard.generateChoices();
            answer1Button.setText(nextCardChoices.get(0));
            answer2Button.setText(nextCardChoices.get(1)); 
            answer3Button.setText(nextCardChoices.get(2));
            resultLabel.setText("");
            rehearsePane.getChildren().add(1, answerPane);
            rehearsePane.getChildren().remove(nextQuestionButton);
        });
        
        returnFromRehearseButton.setOnAction(e-> {
            primaryStage.setScene(mainScene);
        });
        
        mainScene = new Scene(primaryPane, 500, 500);
        
        primaryStage.setTitle("QuizTrainer");
        primaryStage.setScene(startingScene);
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
