
package quiztrainer.dao;

import quiztrainer.domain.User;

public interface UserDao {

    User create(User user) throws Exception;

    User findByUsername(String username);

    int getIdByUsername(String username);
}
