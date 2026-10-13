
import java.util.*;

/**
 * Programmer: Dorian Jenkins
 * Date: October 11, 2026
 * Assignment: CS303 Program 3 - Dynamic Arrays
 */

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to Program 3: Creating MyList");
        Scanner input = new Scanner(System.in);

        String choice = " ";

        choice = Functions.getMenuItem(input);

        while (!(choice.equals("Q"))) {
            choice = Functions.getMenuItem(input);
        }

        input.close();
    }
}
