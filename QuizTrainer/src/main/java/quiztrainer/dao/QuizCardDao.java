
package quiztrainer.dao;

import java.util.List;
import quiztrainer.domain.QuizCard;

public interface QuizCardDao {
    QuizCard create(QuizCard quizCard, int userId, int deckId) throws Exception;
    
    void delete(int quizCardId);
    
    int getIdByQuestion(String question, int userId); 
    
    QuizCard findByQuestion(String question, int userId);
    
    void setBox(int quizCardId, int boxNumber);
    
    void setAmountRehearsed(int quizCardId, int amount);
    
    void setAmountAnsweredRight(int quizCardId, int amount);

    List<QuizCard> getAllQuizCards(int userId);
    
    List<QuizCard> getAllQuizCardsByDeckId(int deckId);   
}
