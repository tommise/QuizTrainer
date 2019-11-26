
package quiztrainer.ui;

import java.util.*;

public class GUIHelper {
    
    public Boolean inputsAreValid(ArrayList<String> inputs) {
        
        for (String input : inputs) {
            if (input.isEmpty()) {
                return false;
            }
            
            if (inputContainsOnlyWhiteSpaces(input)) {
                return false;
            }
        }

        return true;
    }
    
    public Boolean inputIsValid(String input) {
        
        if (input.isEmpty()) {
            return false;
        }
        
        if (inputContainsOnlyWhiteSpaces(input)) {
            return false;
        }

        return true;
    }
    
    public Boolean inputContainsOnlyWhiteSpaces(String input) {
        String regex = "^(?!\\s+$).+";
        
        if (input.matches(regex)) {
            return false;
        }
        
        return true;
    }
}
