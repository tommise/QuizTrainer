
package quiztrainer.dao;

import java.util.List;
import quiztrainer.domain.Deck;

public interface DeckDao {
    void create(String name, int userId) throws Exception;
    
    Deck findDeckByDeckName(String deckName, int userId);
    
    int getDeckIdByNameAndUserId(String deckName, int userId);
    
    List<Deck> getAllDecksByUserId(int userId);
}
