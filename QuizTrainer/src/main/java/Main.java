
import java.util.*;
import logic.Application;
import ui.UserInterface;

public class Main {
        
    public static void main(String[] args) {
        Application app = new Application();
        Scanner scanner = new Scanner(System.in);
            
        UserInterface ui = new UserInterface(app, scanner);
        ui.start();
    }
}
