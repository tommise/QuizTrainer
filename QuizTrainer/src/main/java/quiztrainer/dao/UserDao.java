
package quiztrainer.dao;

import java.util.List;
import quiztrainer.domain.User;

public interface UserDao {

    User create(User user) throws Exception;

    User findByUsername(String username);

    int getIdByUsername(String username);
        
    List<User> getAll();
}
