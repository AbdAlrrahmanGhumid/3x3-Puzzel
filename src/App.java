import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


public class App {
    public static void main(String[] args) throws Exception {
        int steps = 0;
        Template template = new Template();
        Terminal terminal = TerminalBuilder.terminal();
        terminal.enterRawMode();
        java.io.Reader reader = terminal.reader();
        template.viewPuzzle();
        double enterTime = System.currentTimeMillis();
        while(!template.won){
            int firstChar = reader.read();  // First character of the sequence

            if (firstChar == '\u001B') {  // Escape character
                int secondChar = reader.read();  // Next part of the sequence
                if (secondChar == '[') {
                    int thirdChar = reader.read();  // This identifies the arrow key
                    switch (thirdChar) {
                        case 'A':  // Up arrow
                            template.move("Down");
                            break;
                        case 'B':  // Down arrow
                            template.move("Up");
                            break;
                        case 'C':  // Right arrow
                            template.move("Left");
                            break;
                        case 'D':  // Left arrow
                            template.move("Right");
                            break;
                    }
                }
                steps ++;
            } else {
                System.out.println("invaild input!");
                template.viewPuzzle();
            }
        }
        double exitTime = System.currentTimeMillis();
        double requiredTimeInSeconds = (exitTime - enterTime) / 1000;
        System.out.println(steps + " steps.");
        System.out.println(requiredTimeInSeconds + " seconds.");

    }

}
