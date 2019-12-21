
package quiztrainer.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quiztrainer.dao.*;
import quiztrainer.utils.*;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class for application logic
 */
public class QuizTrainerService {
    
    Database database;
    
    private UserDao userDao;
    private DeckDao deckDao;
    private QuizCardDao quizCardDao;
    
    private Leitner leitner;
    private CardInterval interval;
    private User currentUser;
    private int currentUserId;
    
    public QuizTrainerService(String databaseUrl) throws SQLException {
        
        try {     
            this.database = new Database(databaseUrl);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuizTrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.deckDao = new DbDeckDao(database);
        this.userDao = new DbUserDao(database);
        this.quizCardDao = new DbQuizCardDao(database);
        this.leitner = new Leitner();
        this.interval = new CardInterval();
        this.currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Adding a new user to the database.
     *
     * @param   username   Username which was chosen by the user.
     * @param   name   Name which was chosen by the user.
     * 
     * @return true if user was successfully added.
     */
    
    public boolean addANewUser(String username, String name) {
        
        if (userDao.findByUsername(username) != null) {
            return false;
        }

        User newUser = new User(username, name);
        
        try {
            userDao.create(newUser);
            this.currentUser = newUser;
            int userId = userDao.getIdByUsername(username);
            initializeDefaultDeck(userId);
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
    /**
     * Logging user in. 
     * The user will be logged in if given username is
     * found with userDao.findByUsername() method.
     *
     * @param   username   Username to be logged in.
     * 
     * @return true if user was successfully found.
     */    
    
    public boolean login(String username) {       
        User user = userDao.findByUsername(username);          
        
        if (user == null) {
            return false;
        }
        
        currentUserId = userDao.getIdByUsername(username);
        currentUser = user; 
        return true;
    }
    
    /**
     * Logging user out. 
     * currentUser variable will be assigned a null value.
     */   
    
    public void logout() {
        this.currentUser = null;
        this.currentUserId = -1;
    }    
    
     /**
     * Adding a new QuizCard.If given quizCard was not found within the database, 
     * a quizCard will be added to the database with quizCardDao.create() method.
     *
     * @param   quizCard   QuizCard to be added to the database.
     * @param deckName  Name of the deck where the QuizCard is to be added.
     * 
     * @return true if QuizCard was successfully added.
     */  
    
    public boolean addANewQuizCard(QuizCard quizCard, String deckName) {        
        if (quizCardDao.findByQuestion(quizCard.getQuestion(), currentUserId) != null) {
            return false;
        }
        
        int deckId = deckDao.getDeckIdByNameAndUserId(deckName, currentUserId);
        
        try {
            quizCardDao.create(quizCard, currentUserId, deckId);
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
     /**
     * Removing a QuizCard from the database.
     *
     * @param   quizCard   QuizCard to be removed from the database.
     * 
     * @return true if QuizCard was successfully removed.
     */  
    
    public boolean removeAQuizCard(QuizCard quizCard) {
        int quizCardId = quizCardDao.getIdByQuestion(quizCard.getQuestion(), currentUserId);
        
        try {
            quizCardDao.delete(quizCardId);
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
     /**
     * Adding new deck to the database.
     *
     * @param   deckName   The name of the deck to be added.
     * 
     * @return true if Deck was successfully added.
     */ 
    
    public boolean addANewDeck(String deckName) {
        
        if (deckDao.findDeckByDeckName(deckName, currentUserId) != null) {
            return false;
        }
        
        try {
            deckDao.create(deckName, currentUserId);
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
     /**
     * User has answered a QuizCard right.
     * Moves a QuizCard to a higher box within the database
     * with method quizCardDao.setBox()
     * 
     * @param   quizCard   QuizCard to be moved to a different box in the database.
     * @param   currentDeck   Current deck to be passed to the Leitner object.
     */ 
    
    public void correctAnswer(QuizCard quizCard, Deck currentDeck) {
        updateRightAnswerAmount(quizCard);
        updateAnswerAmount(quizCard);
        
        int moveCardToBox = quizCard.getBoxNumber() + 1;
        
        if (moveCardToBox > 5) {
            return;
        }
        
        this.leitner.moveCardUp(quizCard, currentDeck);
        
        int quizCardId = this.quizCardDao.getIdByQuestion(quizCard.getQuestion(), currentUserId);

        try {
            this.quizCardDao.setBox(quizCardId, moveCardToBox);
        } catch (Exception e) {
            return;
        } 
    }
    
     /**
     * User has answered a QuizCard wrong.
     * Moves a QuizCard to box number one within the database
     * with method quizCardDao.setBox()
     * 
     * @param   quizCard   QuizCard to be moved to a different box in the database.
     * @param   currentDeck   Current deck to be passed to the Leitner object.
     */  
    
    public void wrongAnswer(QuizCard quizCard, Deck currentDeck) {
        updateAnswerAmount(quizCard);
        this.leitner.moveCardToBoxOne(quizCard, currentDeck);
        
        int quizCardId = this.quizCardDao.getIdByQuestion(quizCard.getQuestion(), currentUserId);
        
        try {
            this.quizCardDao.setBox(quizCardId, 1);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
     /**
     * Updates the answer amount of a particular QuizCard given in the paramater.
     * 
     * @param   quizCard   QuizCard object whose answer amount is to be updated.
     */ 
    
    public void updateAnswerAmount(QuizCard quizCard) {
        int amount = quizCard.getTotalAnswers() + 1;
        quizCard.setTotalAnswers(amount);
        int quizCardId = this.quizCardDao.getIdByQuestion(quizCard.getQuestion(), currentUserId);
        
        try {
            this.quizCardDao.setAmountRehearsed(quizCardId, amount);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
     /**
     * Updates the right answer amount of a particular QuizCard given in the paramater.
     * 
     * @param   quizCard   QuizCard object whose right answer amount is to be updated.
     */ 
    
    public void updateRightAnswerAmount(QuizCard quizCard) {
        int amount = quizCard.getTotalAnsweredRight() + 1;
        quizCard.setTotalAnsweredRight(amount);
        int quizCardId = this.quizCardDao.getIdByQuestion(quizCard.getQuestion(), currentUserId);
        
        try {
            this.quizCardDao.setAmountAnsweredRight(quizCardId, amount);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
    public List<QuizCard> getAllQuizCards() {
        List<QuizCard> allQuizCards = this.quizCardDao.getAllQuizCards(currentUserId);
        
        return allQuizCards;
    }    
    
     /**
     * Searches from all the QuizCards the most rehearsed quizCard.
     * 
     * @return the most rehearsed QuizCard.
     */ 
    
    public QuizCard getTheMostRehearsedQuizCard() {
        List<QuizCard> allQuizCards = getAllQuizCards();
        
        if (allQuizCards.isEmpty()) {
            return null;
        }
        QuizCard foundCard = allQuizCards.get(0);
        
        for (QuizCard quizCard : allQuizCards) {
            if (quizCard.getTotalAnswers() > foundCard.getTotalAnswers()) {
                foundCard = quizCard;
            }
        }
        
        return foundCard;
    }
    
     /**
     * Searches from all the QuizCards the most wrong answered QuizCard.
     *
     * @return the most wrong answered QuizCard.
     */ 
    
    public QuizCard getTheMostWrongAnsweredQuizCard() {
        List<QuizCard> allQuizCards = getAllQuizCards();
        
        if (allQuizCards.isEmpty()) {
            return null;
        }
        QuizCard foundCard = allQuizCards.get(0);
        
        for (QuizCard quizCard : allQuizCards) {
            
            if (quizCard.getTotalAnsweredWrong() > foundCard.getTotalAnsweredWrong()) {
                foundCard = quizCard;
            }
        }
        
        return foundCard;
    }
    
     /**
     * Searches from all the QuizCards the QuizCard which has been answered right the most.
     *
     * @return the most right answered QuizCard.
     */ 
    
    public QuizCard getTheMostRightAnsweredQuizCard() {
        List<QuizCard> allQuizCards = getAllQuizCards();
        
        if (allQuizCards.isEmpty()) {
            return null;
        }
        
        QuizCard foundCard = allQuizCards.get(0);
        for (QuizCard quizCard : allQuizCards) {
            if (quizCard.getTotalAnsweredRight() > foundCard.getTotalAnsweredRight()) {
                foundCard = quizCard;
            }
        }
        
        return foundCard;
    }    
    
     /**
     * Initializes a new "Default Deck" for new user.
     *
     * @param userId    User id where the created deck is to be connected to.
     * @return true if "Default Deck" was successfully created.
     */ 
    
    public boolean initializeDefaultDeck(int userId) {
        
        try {
            deckDao.create("Default Deck", userId);
            return true;
        } catch (Exception e) {
            return false;
        } 
    } 
    
     /**
     * Fetches all the QuizCards from a particular deck based on deck name.
     *
     * @param deckName  Name of the deck where the search is targeted.
     * @return All the quizCards in a searched deck.
     */ 
    
    public List<QuizCard> getQuizCardsInDeck(String deckName) {
        int deckId = deckDao.getDeckIdByNameAndUserId(deckName, currentUserId);
        List<QuizCard> quizCards = quizCardDao.getAllQuizCardsByDeckId(deckId);
        
        return quizCards;
    }
    
    /**
     * Updates a particular deck.
     * 
     * @param deck The deck object to be updated.
     * @return true if the deck is not empty after update.
     */
    
    public boolean updateDeck(Deck deck) {
        List<QuizCard> quizCards = getQuizCardsInDeck(deck.getDeckName());
        ArrayList<Box> boxes = deck.getBoxes();
        
        if (quizCards.isEmpty()) {
            return false;
        }
        
        for (QuizCard card : quizCards) {
            for (Box box : boxes) {
                if (card.getBoxNumber() == box.getBoxNumber()) {
                    box.addACard(card);
                }
            }
        }
        
        return true;
    }
    
     /**
     * Updating all decks and returning them based on user id.
     * 
     * @return all Decks found from the database added to a List object.
     */
    
    public List<Deck> updateAllDecks() {
        List<Deck> allDecks = deckDao.getAllDecksByUserId(currentUserId);
        
        for (Deck initDeck : allDecks) {
            List<QuizCard> cardsInTheDeck = quizCardDao.getAllQuizCardsByDeckId(initDeck.getDeckId());
            
            if (cardsInTheDeck.isEmpty()) {
                continue;
            }
            
            for (QuizCard quizCard : cardsInTheDeck) {
                initDeck.addACard(quizCard, quizCard.getBoxNumber());
            }
        }
        
        return allDecks;
    }
    
     /**
     * Returns a particular Deck from the database based on a deck name.
     * 
     * @param deckName Name of the deck to be searched.
     * @return found deck as a Deck object.
     */
    
    public Deck getDeckByName(String deckName) {
        return deckDao.findDeckByDeckName(deckName, currentUserId);
    }
        
     /**
     * Fetches a next card to be rehearsed.
     * If there are cards available uses interval.drawANewCard() 
     * with the help of object from the CardInterval class.
     *
     * @param deck The deck where the next question is to be drawn from.
     * @return next QuizCard to be rehearsed.
     */
    
    public QuizCard drawNextQuestion(Deck deck) {
        ArrayList<Box> boxes = deck.getBoxes();
        
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
}
