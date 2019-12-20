
package quiztrainer.ui;

import java.util.*;
import quiztrainer.domain.Deck;
import quiztrainer.domain.QuizCard;
import quiztrainer.domain.QuizTrainerService;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface extends Application {
    
    private Deck currentDeck;    
    private List<Deck> decks;
    private String chosenDeck = ""; 
    private QuizTrainerService trainer;
    
    private Scene startingScene;
    private Scene loginScene;
    private Scene signupScene;    
    private Scene mainScene;
    private Scene mainRehearseScene;
    private Scene startingRehearseScene;    
    private Scene listingScene;
    private Scene addANewCardScene;
    private Scene addANewDeckScene;    
    private Scene statisticsScene;
    
    private VBox statisticsPane;
    private VBox quizCardListNodes;
    private MenuButton listingDeckMenu;    
    private MenuButton addACardDeckMenu;
    private MenuButton startRehearsingDeckMenu;
    
    private Label listLabel = new Label(""); 
    
    @Override
    public void init() throws Exception {
        this.trainer = new QuizTrainerService("jdbc:sqlite:quiztrainer.db");
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        GUIHelper guiHelper = new GUIHelper();
        
        // Starting scene
        
        VBox startingPane = new VBox(20);
        startingPane.setPadding(new Insets(20));
        startingPane.setPadding(new Insets(20));
        Text welcomeText = new Text("Welcome to QuizTrainer!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label welcomeInstructionLabel = new Label("Please login or signup in order to proceed."); 
        Button loginStartSceneButton = new Button("Login");
        Button signupStartSceneButton = new Button("Signup");
        
        startingPane.getChildren().addAll(welcomeText, welcomeInstructionLabel, loginStartSceneButton, signupStartSceneButton); 
        startingScene = new Scene(startingPane, 575, 575); 
        
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
        Label loginLabel = new Label("");
        Text loginText = new Text("Login");
        loginText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Text currentUserText = new Text("");  
        currentUserText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label notificationLabel = new Label(""); 
        
        loginPane.getChildren().addAll(loginText, usernameInstructionLabel, 
                usernameInput, loginButton, returnFromLoginButton, loginLabel); 
        loginScene = new Scene(loginPane, 575, 575);
        
        loginButton.setOnAction(e-> {
            String username = usernameInput.getText();
            if (trainer.login(username)){
                usernameInput.setText("");
                loginLabel.setText("");
                notificationLabel.setText("");
                currentUserText.setText("Welcome " + trainer.getCurrentUser().getName() + "!");
                decks = this.trainer.updateAllDecks();
                primaryStage.setScene(mainScene);  
            } else {
                loginLabel.setText("User does not exist.");
                loginLabel.setTextFill(Color.RED);                
            }
        });
        
        returnFromLoginButton.setOnAction(e-> {
            usernameInput.setText("");
            loginLabel.setText("");            
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
        Text signupText = new Text("Signup");
        signupText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
 
        signupPane.getChildren().addAll(signupText, newUserInstruction, newUsernameInput, newNameInstruction, 
                newNameInput, signupButton, signupMessage, returnFromSignupButton);
        signupScene = new Scene(signupPane, 575, 575);
        
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
                loginLabel.setText("You have successfully created an account with username: " + newUsernameInput.getText());
                loginLabel.setTextFill(Color.GREEN);
                newUsernameInput.setText("");
                newNameInput.setText("");            
                signupMessage.setText(""); 
                primaryStage.setScene(loginScene);
                
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
        Button primaryAddANewDeckButton = new Button("Add a new Deck");
        Button primaryAddANewCardButton = new Button("Add a new QuizCard");     
        Button primaryListingButton = new Button("My QuizCards");
        Button primaryStatisticsButton = new Button("Statistics");
        Button primaryRehearseButton = new Button("Rehearse");        
        Button logoutButton = new Button("Logout");
        primaryPane.getChildren().addAll(currentUserText, primaryAddANewDeckButton, primaryAddANewCardButton,
                primaryListingButton, primaryStatisticsButton, primaryRehearseButton, notificationLabel, logoutButton);

        logoutButton.setOnAction(e-> {
            trainer.logout();
            decks = null;            
            currentDeck = null;
            chosenDeck = "";
            currentUserText.setText("");
            primaryStage.setScene(startingScene);
        });
        
        primaryListingButton.setOnAction(e-> {
            chosenDeck = "";
            notificationLabel.setText(""); 
            refreshQuizCardList();
            refreshMenuItems(listingDeckMenu);
            primaryStage.setScene(listingScene);
        });      
                
        // Scene for adding a new card
        
        VBox addANewCardPane = new VBox(20);
        addANewCardPane.setPadding(new Insets(20));
        addANewCardPane.setPadding(new Insets(20));
        Button returnFromAddButton = new Button("<- Return to menu");
        Text newCardText = new Text("Add a new QuizCard");
        newCardText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        addANewCardScene = new Scene(addANewCardPane, 575, 575);
        
        primaryAddANewCardButton.setOnAction(e-> {
            refreshMenuItems(addACardDeckMenu);
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
        addACardDeckMenu = new MenuButton("Select a deck");
        Button addANewCardButton = new Button("Add the created QuizCard");
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
            
            if (guiHelper.inputsAreValid(inputs) && !chosenDeck.isEmpty()) {
                QuizCard newQuizCard = new QuizCard(question, answer, choices, 1, 0, 0);
                this.trainer.addANewQuizCard(newQuizCard, chosenDeck);  
                notificationLabel.setText("You have successfully created a new QuizCard.");
                notificationLabel.setTextFill(Color.GREEN);
                newQuestionInput.setText("");
                newAnswerInput.setText("");
                wrongChoice1Input.setText("");
                wrongChoice2Input.setText("");
                wrongChoice3Input.setText(""); 
                falseInputLabel.setText("");
                primaryStage.setScene(mainScene);
            } else if (chosenDeck.isEmpty()) {
                falseInputLabel.setText("Deck has not been chosen.");
                falseInputLabel.setTextFill(Color.RED);
            } else {
                falseInputLabel.setText("One of the forms is empty.");
                falseInputLabel.setTextFill(Color.RED);
            }
        });
        
        returnFromAddButton.setOnAction(e-> {
            chosenDeck = "";
            newQuestionInput.setText("");
            newAnswerInput.setText("");
            wrongChoice1Input.setText("");
            wrongChoice2Input.setText("");
            wrongChoice3Input.setText(""); 
            falseInputLabel.setText("");
            notificationLabel.setText(""); 
            primaryStage.setScene(mainScene);
        });
        
        Label addANewDeckLabel = new Label("Choose a deck from the dropdown menu:");
 
        addANewCardPane.getChildren().addAll(newCardText, newQuestionPane, newAnswerPane, 
                wrongChoicesPane, addANewDeckLabel, addACardDeckMenu, addButtonsPane);
        
        // Scene for adding a new deck
        
        VBox addANewDeckPane = new VBox(20);
        addANewDeckPane.setPadding(new Insets(20));
        addANewDeckPane.setPadding(new Insets(20));
        Button returnFromAddDeckButton = new Button("<- Return to menu");      
        
        Label newDeckInstruction = new Label("Choose a name for the deck as a category for the cards: (ie. Anatomy or Cities)");
        TextField newDeckNameInput = new TextField(); 
        Button addANewDeckButton = new Button("Add a deck"); 
        Label newDeckMessage = new Label("");
        Text newDeckText = new Text("Add a new Deck");
        newDeckText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        primaryAddANewDeckButton.setOnAction(e-> {
            notificationLabel.setText(""); 
            primaryStage.setScene(addANewDeckScene);
        });
        
        returnFromAddDeckButton.setOnAction(e-> {
            newDeckNameInput.setText("");            
            newDeckMessage.setText("");  
            primaryStage.setScene(mainScene);
        });
        
        addANewDeckButton.setOnAction(e-> {
            String deckName = newDeckNameInput.getText();
            
            if (!guiHelper.inputIsValid(deckName)) {
                newDeckMessage.setText("Deck name contains invalid characters or form is empty.");
                newDeckMessage.setTextFill(Color.RED);
            } else if (trainer.addANewDeck(deckName)) {
                notificationLabel.setText("You have successfully created a new deck: " + deckName);
                notificationLabel.setTextFill(Color.GREEN);
                newDeckNameInput.setText("");            
                newDeckMessage.setText(""); 
                refreshDecks();                
                primaryStage.setScene(mainScene);
            } else {
                newDeckMessage.setText("Deck name is already taken.");
                newDeckMessage.setTextFill(Color.RED);
            }
        }); 
        
        addANewDeckPane.getChildren().addAll(newDeckText, newDeckInstruction, newDeckNameInput, addANewDeckButton, newDeckMessage, returnFromAddDeckButton);
        addANewDeckScene = new Scene(addANewDeckPane, 575, 575);
        
        // Scene for listing cards
        
        VBox quizCardListingPane = new VBox(20);
        quizCardListingPane.setPadding(new Insets(20));
        quizCardListingPane.setPadding(new Insets(20)); 
        Label listDeckInstruction = new Label("Choose a deck: ");
        listingDeckMenu = new MenuButton("Select a deck"); 
        Button returnFromListingButton = new Button("<- Return to menu"); 
        Text listingText = new Text("Listing QuizCards");
        listingText.setFont(Font.font("Arial", FontWeight.BOLD, 20));        
        listingScene = new Scene(quizCardListingPane, 575, 575);
        
        quizCardListNodes = new VBox(10);
        ScrollPane quizCardScrollPane = new ScrollPane();
        quizCardScrollPane.setContent(quizCardListNodes);
        
        quizCardListingPane.getChildren().addAll(listingText, listDeckInstruction, listingDeckMenu, listLabel, quizCardScrollPane, returnFromListingButton);
        
        returnFromListingButton.setOnAction(e-> {
            chosenDeck = "";
            primaryStage.setScene(mainScene);
        });
        
        // Main rehearse scene
        
        VBox rehearsePane = new VBox(20);
        rehearsePane.setPadding(new Insets(20));
        rehearsePane.setPadding(new Insets(20));
        
        mainRehearseScene = new Scene(rehearsePane, 575, 575);
        
        HBox answerPane = new HBox(20);        
        Text question = new Text("");
        question.setFont(Font.font("Arial", FontWeight.BOLD, 15)); 
        Button answer1Button = new Button("");
        Button answer2Button = new Button("");
        Button answer3Button = new Button("");
        
        answerPane.getChildren().addAll(answer1Button, answer2Button, answer3Button);

        Label resultLabel = new Label("");
        Button nextQuestionButton = new Button("Next question ->");
        Button returnFromRehearseButton = new Button("<- Return to menu");
        rehearsePane.getChildren().addAll(question, answerPane, resultLabel, returnFromRehearseButton);
        
        answer1Button.setOnAction(e-> {
            String answer = answer1Button.getText();
            QuizCard quizCard = currentDeck.getACard(question.getText());
            if (quizCard.isCorrectAnswer(answer)) {  
                this.trainer.correctAnswer(quizCard, currentDeck);                
                resultLabel.setText(quizCard.getCorrectAnswerString());
            } else {     
                this.trainer.wrongAnswer(quizCard, currentDeck);
                resultLabel.setText(quizCard.getWrongAnswerString());
            }
            rehearsePane.getChildren().add(3, nextQuestionButton);
            rehearsePane.getChildren().remove(answerPane);
        });
        
        answer2Button.setOnAction(e-> {
            String answer = answer2Button.getText();    
            QuizCard quizCard = currentDeck.getACard(question.getText());
            if (quizCard.isCorrectAnswer(answer)) {
                this.trainer.correctAnswer(quizCard, currentDeck);                
                resultLabel.setText(quizCard.getCorrectAnswerString());
            } else {
                this.trainer.wrongAnswer(quizCard, currentDeck);                
                resultLabel.setText(quizCard.getWrongAnswerString());
            } 
            rehearsePane.getChildren().add(3, nextQuestionButton);
            rehearsePane.getChildren().remove(answerPane);        
        });

        answer3Button.setOnAction(e-> {
            String answer = answer3Button.getText();  
            QuizCard quizCard = currentDeck.getACard(question.getText());       
            if (quizCard.isCorrectAnswer(answer)) {
                this.trainer.correctAnswer(quizCard, currentDeck);                
                resultLabel.setText(quizCard.getCorrectAnswerString());
            } else {
                this.trainer.wrongAnswer(quizCard, currentDeck);
                resultLabel.setText(quizCard.getWrongAnswerString());
            }  
            rehearsePane.getChildren().add(3, nextQuestionButton);
            rehearsePane.getChildren().remove(answerPane);
        });        
        
        nextQuestionButton.setOnAction(e-> {
            QuizCard nextCard = this.trainer.drawNextQuestion(currentDeck);
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
            currentDeck = null;
            chosenDeck = "";
            notificationLabel.setText(""); 
            question.setText("");
            refreshMenuItems(startRehearsingDeckMenu);
            primaryStage.setScene(mainScene);
        });
        
        // Initial rehearse scene
        
        VBox startingRehearsePane = new VBox(20);
        startingRehearsePane.setPadding(new Insets(20));
        startingRehearsePane.setPadding(new Insets(20));
        Text initialRehearseText = new Text("Choose a deck");
        initialRehearseText.setFont(Font.font("Arial", FontWeight.BOLD, 20)); 
        Button startRehearsingButton = new Button("Rehearse mode ->");
        startRehearsingDeckMenu = new MenuButton("Select a deck");         
        Button returnFromStartingRehearseButton = new Button("<- Return to menu");
        Label chooseADeckLabel = new Label("Choose a deck to rehearse from: ");
        Label rehearseErrorLabel = new Label("");
        
        startingRehearsePane.getChildren().addAll(initialRehearseText, chooseADeckLabel, startRehearsingDeckMenu, 
                startRehearsingButton, returnFromStartingRehearseButton, rehearseErrorLabel);
        
        startingRehearseScene = new Scene(startingRehearsePane, 575, 575);  
        
        returnFromStartingRehearseButton.setOnAction(e-> { 
            currentDeck = null;
            chosenDeck = "";
            question.setText("");
            notificationLabel.setText("");
            refreshMenuItems(startRehearsingDeckMenu);
            primaryStage.setScene(mainScene);
        });
        
        startRehearsingButton.setOnAction(e-> {
            notificationLabel.setText("");
            
            if (!chosenDeck.isEmpty()) {
                currentDeck = trainer.getDeckByName(chosenDeck);
            }
            
            if (chosenDeck.isEmpty()) {
                rehearseErrorLabel.setText("Deck has not been chosen.");
                rehearseErrorLabel.setTextFill(Color.RED); 
            } else if (!trainer.updateDeck(currentDeck)) {
                rehearseErrorLabel.setText("There are no cards to rehearse in this deck.");
                rehearseErrorLabel.setTextFill(Color.RED);  
            } else {
                resultLabel.setText("Welcome to rehearse mode! \n"
                        + "\n"
                        + "- You will be provided a question and \n"
                        + "three (3) possible answers to choose from. \n"
                        + "\n"
                        + "- The questions will be drawn from the deck \n"
                        + " '" + chosenDeck + "' based on your previous performance. \n"
                        + "\n"                        
                        + "- After your answer, you will be shown \n"
                        + "the result and correct answer. \n"
                        + "\n"
                        + "Enjoy! \n"
                        + "\n");
                
                rehearseErrorLabel.setText("");
                
                rehearsePane.getChildren().remove(answerPane);
                
                if (!rehearsePane.getChildren().contains(nextQuestionButton)) {
                    rehearsePane.getChildren().add(2, nextQuestionButton);
                }
                
                primaryStage.setScene(mainRehearseScene); 
            }
        });
        
        primaryRehearseButton.setOnAction(e-> {
            question.setText("");
            notificationLabel.setText("");
            refreshMenuItems(startRehearsingDeckMenu);
            primaryStage.setScene(startingRehearseScene); 
        });
        
        // Scene for statistics
        
        statisticsPane = new VBox(20);
        statisticsPane.setPadding(new Insets(20));
        statisticsPane.setPadding(new Insets(20));
        
        statisticsScene = new Scene(statisticsPane, 575, 575);
        
        Button returnFromStatisticsButton = new Button("<- Return to menu");
        
        primaryStatisticsButton.setOnAction(e-> {
            notificationLabel.setText(""); 
            
            if (this.trainer.getAllQuizCards().isEmpty()) {
                notificationLabel.setText("There are no cards to show statistics from.");
                notificationLabel.setTextFill(Color.RED);
            } else {
                refreshQuizCardStatistics();
                statisticsPane.getChildren().add(returnFromStatisticsButton);
                primaryStage.setScene(statisticsScene);
            }
        });  
        
        returnFromStatisticsButton.setOnAction(e-> {
            primaryStage.setScene(mainScene);
        });        
        
        /// Setting mainScene
        
        mainScene = new Scene(primaryPane, 575, 575);
        
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
    
    public void refreshQuizCardList() {
        quizCardListNodes.getChildren().clear(); 
        listLabel.setText("Listing all your QuizCards from deck: " + chosenDeck);
        
        List<QuizCard> allQuizCards = this.trainer.getQuizCardsInDeck(chosenDeck);

        for (QuizCard card : allQuizCards) {
            quizCardListNodes.getChildren().add(createQuizCardNode(card, card.getQuestionAndRightAnswer()));
        }   
    }
    
    public Node createQuizCardNode(QuizCard card, String labelString) {
        HBox quizCard = new HBox(5);
        Label label = new Label(labelString);
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e->{
            this.trainer.removeAQuizCard(card);
            refreshQuizCardList();
        });
        Region spaces = new Region();
        HBox.setHgrow(spaces, Priority.ALWAYS);
        quizCard.getChildren().addAll(label, spaces, removeButton);
        return quizCard;
    }
    
    public void refreshDecks() {
        this.decks = this.trainer.updateAllDecks();
    }
    
    public void refreshQuizCardStatistics() {
        statisticsPane.getChildren().clear();
        
        Text statisticsTitle = new Text("Statistics");
        statisticsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        
        QuizCard mostAnsweredCard = this.trainer.getTheMostRehearsedQuizCard();
        QuizCard mostRightAnsweredCard = this.trainer.getTheMostRightAnsweredQuizCard();
        QuizCard mostWrongAnsweredCard = this.trainer.getTheMostWrongAnsweredQuizCard();        
                
        Label mostAnsweredLabel = new Label("");   
        Text mostAnsweredTitle = new Text ("The card that has been most rehearsed:");
        mostAnsweredTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        mostAnsweredLabel.setText(mostAnsweredCard.getQuestion() + " \n" + mostAnsweredCard.getTotalAnswers() + " times");  
    
        Label mostAnsweredRightLabel = new Label("");
        Text mostRightTitle = new Text ("The card that has been most answered right:");
        mostRightTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        mostAnsweredRightLabel.setText(mostRightAnsweredCard.getQuestion() + " \n" + mostRightAnsweredCard.getTotalAnsweredRight() + " times");
        
        Label mostAnsweredWrongLabel = new Label("");
        Text mostWrongTitle = new Text ("The card that has been most answered wrong:");
        mostWrongTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        mostAnsweredWrongLabel.setText(mostWrongAnsweredCard.getQuestion() + " \n" + mostWrongAnsweredCard.getTotalAnsweredWrong() + " times");  
        
        statisticsPane.getChildren().addAll(statisticsTitle, mostAnsweredTitle, mostAnsweredLabel, 
                mostRightTitle, mostAnsweredRightLabel, mostWrongTitle, mostAnsweredWrongLabel);
    }
    
    public void refreshMenuItems(MenuButton menuButton) {
        menuButton.getItems().clear();
        menuButton.setText("Select a deck");
        chosenDeck = "";
        
        EventHandler<ActionEvent> event = (ActionEvent e) -> {
            chosenDeck = ((MenuItem)e.getSource()).getText();
            menuButton.setText(chosenDeck);
            refreshQuizCardList();
        }; 
        
        for (Deck deck : decks) {
            MenuItem deckName = new MenuItem(deck.getDeckName());
            menuButton.getItems().add(deckName);
            
            deckName.setOnAction(event);
        }
    }
}
