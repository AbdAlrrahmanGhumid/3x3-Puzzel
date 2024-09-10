
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Template template = new Template(); // an instance of the 3x3 puzzle is initilised

        while (!template.won) { // as long as the game is not won
            template.viewPuzzle();
            try { // try-catch to avoid invalid input: i < 1, i >8 , strings
                template.move(getUserInput()); // getUserInput ask the user to enter a number
            } catch (Exception e) {
                System.out.println("invalid input!");
            }
            template.checkWinning();
        }
        
        template.viewPuzzle();
        System.out.println("YOU WON THE GAME!");    

    }

    public static int getUserInput() {
        Scanner myObj = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter Puzzle Number to Move");
        String input = myObj.nextLine(); // Read user input
        return Integer.valueOf(input);
    }

}
