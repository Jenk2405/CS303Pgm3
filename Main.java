
import java.util.Scanner;

/**
 * Programmer: Dorian Jenkins
 * Date: October 11, 2026
 * Assignment: CS303 Program 3 - Dynamic Arrays
 *
 * PRE: A1input.txt and A2input.txt are available in the
 * project's working directory.
 *
 * POST: Loads the user's selected input file, displays the menu
 * until Q is selected, and closes the Scanner when finished.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println(
            "Welcome to Program 3: Creating MyList"
        );

        Scanner input = new Scanner(System.in);

        // Load the input file selected by the user.
        MyArrayList<Integer> list = Functions.loadFile(input);

        String choice;

        // Keep running until the user chooses Q to quit.
        do {
            choice = Functions.getMenuItem(input, list);
        } while (!choice.equals("Q"));

        input.close();
    }
}
