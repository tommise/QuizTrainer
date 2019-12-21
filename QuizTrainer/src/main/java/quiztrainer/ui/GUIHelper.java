
package quiztrainer.ui;

import java.util.*;

public class GUIHelper {
    
    public boolean inputIsValid(String input) {
        if (input.length() > 25) {
            return false;
        }
        
        return inputIsEmpty(input);
    }
    
    public boolean questionStringIsValid(String question) {

        if (question.length() > 40) {
            return false;
        }
        
        return inputIsEmpty(question);
    }
    
    public boolean inputIsEmpty(String input) {
        
        if (input.isEmpty()) {
            return false;
        }
        
        if (inputContainsOnlyWhiteSpaces(input)) {
            return false;
        }

        return true;
    }
    
    public boolean inputContainsOnlyWhiteSpaces(String input) {
        String regex = "^(?!\\s+$).+";
        
        if (input.matches(regex)) {
            return false;
        }
        
        return true;
    }
    
    public boolean inputsAreValid(ArrayList<String> inputs) {
        
        for (String input : inputs) {
            if (!inputIsValid(input)) {
                return false;
            }
        }

        return true;
    }
}
