
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.*;
import java.awt.event.*;


public class Template extends JFrame implements KeyListener{
    public Template() {
        do {
            generateRandomPuzzle();
        } while (!isSolvable());
    }

    Integer rightNeighbour, leftNeighbour, upperNeighbour, lowerNeighbour; // these variables are not the true
                                                                           // neighbours however their positions in the
                                                                           // array
    Set<Integer> numbers = new HashSet<>();
    private Integer[] content = new Integer[9];
    private int nullPosition = position(null);
    public boolean won = false;

    public Integer pickUpRandomElemnt() {
        int size = numbers.size();
        int randomNumber = new Random().nextInt(size);
        int i = 0;
        for (Integer number : numbers) {
            if (i == randomNumber) {
                numbers.remove(number);
                return number;
            }
            i++;
        }
        return null;
    }

    private boolean isSolvable() { // tests if the randomly generated puzzle is solveable
        return (countInversions() % 2 == 0); // solvable puzzles have even number of inversion
    }

    private int countInversions() { // one inversion for each pair of tiles (a, b), if a > b and a comes before b
        int inversions = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                
                if (content[i]!= null && content[j]!= null && content[i] > content[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    private void generateRandomPuzzle() {
        numbers.clear();
        for (int i = 1; i <= 8; i++) { // numbers 1 to 8 are added to the set numbers
            numbers.add(i);
        }
        numbers.add(null); // element null is added to have an empty field in the puzzle
        for (int i = 0; i < 9; i++) {
            content[i] = pickUpRandomElemnt(); // the elements of set numbers are put randomly in the arry content to
                                               // produce random puzzle
        }
        updateNullPosition();
        updateNeighbours();
    }

    public void viewPuzzle() {
        System.out.printf("|-------|-------|-------|%n");
        for (int i = 0; i < 9; i = i + 3) {
            System.out.printf("|%4s   |%4s   |%4s   | %n",
                    this.content[i] != null ? this.content[i].toString() : "",
                    this.content[i + 1] != null ? this.content[i + 1].toString() : "",
                    this.content[i + 2] != null ? this.content[i + 2].toString() : "");
            System.out.printf("|-------|-------|-------|%n");
        }
    }

    public void move(int number) {
        move(checkNeighbourhood(number));
    }

    private String checkNeighbourhood(int number) { // try-catch is used because Neighbour might be a null element
        try {
            if (number == content[rightNeighbour])
                return "Right";
        } catch (NullPointerException e) {
        }
        try {
            if (number == content[leftNeighbour])
                return "Left";
        } catch (NullPointerException e) {
        }
        try {
            if (number == content[upperNeighbour])
                return "Up";
        } catch (Exception e) {
        }
        try {
            if (number == content[lowerNeighbour])
                return "Down";
        } catch (Exception e) {
        }

        return null;
    }

    public void move(String direction) { // the method move the null element in the given direction
        int row = nullPosition / 3; // the variables row and column are useful hinder the movement takes the null
                                    // element outside of the puzzle. (outside means graphicly)
        int column = nullPosition % 3;
        switch (direction) { // the positions of the null elmemnt and its according neighbour are switched
                             // with the usage of help varaible. after that the new positions are updated.
            case "Right":
                if (column < 2) {
                    Integer helpMemory = content[rightNeighbour];
                    content[nullPosition] = helpMemory;
                    content[rightNeighbour] = null;
                    updateNullPosition();
                    updateNeighbours();
                    viewPuzzle();
                    checkWinning();
                }
                break;
            case "Left":
                if (column > 0) {
                    Integer helpMemory = content[leftNeighbour];
                    content[nullPosition] = helpMemory;
                    content[leftNeighbour] = null;
                    updateNullPosition();
                    updateNeighbours();
                    viewPuzzle();
                    checkWinning();
                }
                break;
            case "Up":
                if (row > 0) {
                    Integer helpMemory = content[upperNeighbour];
                    content[nullPosition] = helpMemory;
                    content[upperNeighbour] = null;
                    updateNullPosition();
                    updateNeighbours();
                    viewPuzzle();
                    checkWinning();
                }
                break;
            case "Down":
                if (row < 2) {
                    Integer helpMemory = content[lowerNeighbour];
                    content[nullPosition] = helpMemory;
                    content[lowerNeighbour] = null;
                    updateNullPosition();
                    updateNeighbours();
                    viewPuzzle();
                    checkWinning();
                }
                break;
        }

    }

    private void updateNullPosition() {
        nullPosition = position(null);
    }

    private void updateNeighbours() {
        int row = nullPosition / 3; // the two variables help in giving the null element the right neighbours. if
                                    // the null elment is sitting on the right edge, the rightNeighbour is null.
        int column = nullPosition % 3;

        if (column < 2)
            rightNeighbour = nullPosition + 1;
        else
            rightNeighbour = null;

        if (column > 0)
            leftNeighbour = nullPosition - 1;
        else
            leftNeighbour = null;

        if (row > 0)
            upperNeighbour = nullPosition - 3;
        else
            upperNeighbour = null;

        if (row < 2)
            lowerNeighbour = nullPosition + 3;
        else
            lowerNeighbour = null;
    }

    private int position(Integer number) { // gives the position of the input number in the array.
        int position = 0;
        for (Integer i : content) {
            if (i == number)
                return position;
            position++;
        }
        return 0;
    }

    public void checkWinning() { // check if the array is arranged coorectly.
        won = true;
        for (int i = 0; i < 8; i++) {
            if (content[i] == null ||content[i] != i + 1) {
                won = false;
                break;
            }
        }
        if (won) System.out.println("You Won The Game!");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) { 
            case KeyEvent.VK_UP: 
                move("Down");
                break; 
            case KeyEvent.VK_DOWN: 
                move("Up");
                break; 
            case KeyEvent.VK_LEFT: 
                move("Right");
                break; 
            case KeyEvent.VK_RIGHT: 
                move("Left");
                break; 
        } 
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}