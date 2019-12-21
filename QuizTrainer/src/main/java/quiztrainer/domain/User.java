
package quiztrainer.domain;
/**
 * Java class for User
 */
public class User {
    
    private String username;
    private String name;
    
    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUsername() {
        return this.username;
    }
}
