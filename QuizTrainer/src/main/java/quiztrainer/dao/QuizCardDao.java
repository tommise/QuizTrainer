
package quiztrainer.dao;

import java.util.List;
import quiztrainer.domain.QuizCard;

public interface QuizCardDao {
    QuizCard create(QuizCard quizCard, int userId) throws Exception;
    
    QuizCard findByQuestion(String question);
    
    void setBox(int quizCardId, int boxNumber);
    
    void setAmountRehearsed(int quizCardId, int amount);
    
    void setAmountAnsweredRight(int quizCardId, int amount);

    List<QuizCard> getAllQuizCards(int userId);
    
    int getIdByQuestion(String question);    
}
