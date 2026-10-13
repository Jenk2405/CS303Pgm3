
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu and input/output functions for CS303 Program 3.
 *
 * PRE: The user supplies input through a Scanner.
 * POST: Menu operations update the dynamic array and record actions.
 *
 * W3Schools references:
 * Java Arrays: https://www.w3schools.com/java/java_arrays.asp
 * Java Generics: https://www.w3schools.com/java/java_generics.asp
 */
public class Functions {

    private static MyArrayList<Integer> list;

    private static final Path REPORT =
            Paths.get("Program3Report.txt");

    private Functions() {
    }

    /**
     * PRE: A Scanner connected to user input is supplied.
     * POST: Loads the file on the first call, then processes menu
     * operations until the user selects Q.
     *
     * AI assistance (ChatGPT): Helped adapt menu processing to
     * the original Main.java, which repeatedly calls getMenuItem()
     * until the user selects Q. The original menu choices and
     * their corresponding functions were preserved.
     */
    public static String getMenuItem(Scanner input) {

        if (list == null) {
            list = loadFile(input);
        }

        printMenu();

        System.out.print("Enter your choice: ");
        String choice = input.nextLine().trim().toUpperCase();

        switch (choice) {
            case "A":
                addItem(input);
                break;

            case "B":
                addList(input);
                break;

            case "C":
                containsAllValues(input);
                break;

            case "D":
                deleteValue(input);
                break;

            case "E":
                deleteList(input);
                break;

            case "F":
                findValue(input);
                break;

            case "M":
                modifyValue(input);
                break;

            case "P":
                printList();
                break;

            case "Q":
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println(
                    "Invalid menu choice. Please try again."
                );
        }

        return choice;
    }

    /**
     * PRE: None.
     * POST: Displays all available menu choices.
     */
    private static void printMenu() {
        System.out.println("\nMODIFY YOUR LIST");
        System.out.println("A: Add Number");
        System.out.println("B: Add a Group of Values to List");
        System.out.println("C: Contains All Values in the List");
        System.out.println("D: Delete (by Index)");
        System.out.println("E: Delete a Group of Values From the List");
        System.out.println("F: Find a Value");
        System.out.println("M: Modify a Value");
        System.out.println("P: Print Array");
        System.out.println("Q: Quit");
    }

    /**
     * PRE: The user selects 1 or 2 and the corresponding file exists.
     * POST: Loads valid integers and skips malformed tokens.
     *
     * AI assistance (ChatGPT): Helped revise file loading to
     * populate the custom MyArrayList implementation while
     * handling invalid integer tokens and file-reading errors.
     */
    private static MyArrayList<Integer> loadFile(Scanner input) {

        while (true) {
            System.out.print("Choose input file (1 or 2): ");
            String choice = input.nextLine().trim();

            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println(
                    "Invalid choice. Enter 1 or 2."
                );
                continue;
            }

            Path path = Paths.get(
                choice.equals("1")
                    ? "A1input.txt"
                    : "A2input.txt"
            );

            MyArrayList<Integer> loaded = new MyArrayList<>();
            int errors = 0;

            try (Scanner fileInput = new Scanner(path)) {

                while (fileInput.hasNext()) {
                    String token = fileInput.next();

                    try {
                        loaded.add(Integer.parseInt(token));
                    } catch (NumberFormatException ex) {
                        errors++;
                        report("Skipped invalid token: " + token);
                    }
                }

                System.out.println(
                    "File " + path.getFileName()
                    + " processed successfully. Loaded "
                    + loaded.size()
                    + " valid integers; skipped "
                    + errors + " invalid token(s)."
                );

                report(
                    "Loaded " + path.getFileName()
                    + ": " + loaded.size()
                    + " valid integers; skipped "
                    + errors + " invalid token(s)."
                );

                return loaded;

            } catch (IOException ex) {
                System.out.println(
                    "Could not read " + path
                    + ". Make sure it is in the working directory."
                );

                report("File read error: " + ex.getMessage());
            }
        }
    }

    /**
     * A: Add one integer.
     * PRE: The user enters an integer.
     * POST: Appends the value to the list.
     */
    private static void addItem(Scanner input) {

        Integer value = readInteger(
            input, "Enter integer to add: "
        );

        if (value != null) {
            list.add(value);

            System.out.println("Added: " + value);
            report("Added: " + value);
        }
    }

    /**
     * B: Add a group of integers.
     * PRE: Values are separated by spaces.
     * POST: Appends all valid values.
     */
    private static void addList(Scanner input) {

        System.out.print(
            "Enter values to add separated by spaces: "
        );

        String line = input.nextLine().trim();
        List<Integer> added = new ArrayList<>();

        if (!line.isEmpty()) {
            for (String token : line.split("\\s+")) {
                try {
                    int value = Integer.parseInt(token);

                    list.add(value);
                    added.add(value);

                } catch (NumberFormatException ex) {
                    System.out.println(
                        "Ignoring invalid value: " + token
                    );
                }
            }
        }

        System.out.println("Items added to your list: " + added);
        report("Items added: " + added);
    }

    /**
     * C: Check whether all requested values exist.
     * PRE: Values are separated by spaces.
     * POST: Reports whether all valid requested values are present.
     *
     * AI assistance (ChatGPT): Helped adapt the collection check
     * to use the containsAll() operation implemented in MyArrayList.
     */
    private static void containsAllValues(Scanner input) {

        System.out.print(
            "Enter the values you would like check "
            + "separated by spaces: "
        );

        String line = input.nextLine().trim();
        List<Integer> requested = new ArrayList<>();
        boolean valid = !line.isEmpty();

        if (!line.isEmpty()) {
            for (String token : line.split("\\s+")) {
                try {
                    requested.add(Integer.parseInt(token));
                } catch (NumberFormatException ex) {
                    valid = false;
                }
            }
        }

        if (valid && list.containsAll(requested)) {
            System.out.println(
                "These valid items are in your list: " + requested
            );
        } else {
            System.out.println(
                "Not all values provided are in your list."
            );
        }

        report("ContainsAll checked: " + requested);
    }

    /**
     * D: Delete an element by index.
     * PRE: The user enters a valid index.
     * POST: Removes and reports the selected element.
     */
    private static void deleteValue(Scanner input) {

        while (true) {
            Integer index = readInteger(
                input, "Enter index to delete: "
            );

            if (index == null) {
                continue;
            }

            if (index < 0 || index >= list.size()) {
                System.out.println(
                    "Index: " + index
                    + " is not in range. Please try again"
                );
                continue;
            }

            Integer removed = list.remove(index.intValue());

            System.out.println(
                "Item at index: " + index + " was "
                + removed + " has been removed."
            );

            report(
                "Removed index " + index + " value " + removed
            );

            return;
        }
    }

    /**
     * E: Delete a group of values.
     * PRE: Values are separated by spaces.
     * POST: Removes matching values and reports those removed.
     *
     * AI assistance (ChatGPT): Helped adapt group deletion to use
     * the custom list's remove operation while collecting and
     * reporting the values that were successfully removed.
     */
    private static void deleteList(Scanner input) {

        System.out.print(
            "Enter the list of values to remove: "
        );

        String line = input.nextLine().trim();
        List<Integer> removed = new ArrayList<>();

        if (!line.isEmpty()) {
            for (String token : line.split("\\s+")) {
                try {
                    int value = Integer.parseInt(token);

                    if (list.remove(Integer.valueOf(value))) {
                        removed.add(value);
                    }

                } catch (NumberFormatException ex) {
                    // Invalid values are ignored.
                }
            }
        }

        System.out.println(
            "Items removed to your list: " + removed
        );

        report("Values removed: " + removed);
    }

    /**
     * F: Find a value.
     * PRE: The user enters an integer.
     * POST: Reports the first matching index or that it was not found.
     */
    private static void findValue(Scanner input) {

        Integer value = readInteger(
            input, "Enter value to find: "
        );

        if (value == null) {
            return;
        }

        int position = list.indexOf(value);

        if (position >= 0) {
            System.out.println(
                "The value " + value
                + " was found at position: " + position
            );
        } else {
            System.out.println(
                "The value " + value + " could not be found"
            );
        }

        report(
            "Find " + value + ": "
            + (position >= 0 ? "position " + position : "not found")
        );
    }

    /**
     * M: Modify an element.
     * PRE: The user supplies a valid index and replacement integer.
     * POST: Replaces the value and reports old and new values.
     */
    private static void modifyValue(Scanner input) {

        int index;

        while (true) {
            Integer candidate = readInteger(
                input, "Enter index to modify: "
            );

            if (candidate == null
                    || candidate < 0
                    || candidate >= list.size()) {

                System.out.println(
                    "Index is not in range. Please try again."
                );
                continue;
            }

            index = candidate;
            break;
        }

        Integer replacement;

        while ((replacement = readInteger(
                input, "Enter new value: ")) == null) {
            // Prompt again when input is invalid.
        }

        Integer old = list.set(index, replacement);

        System.out.println(
            "Item at index: " + index + " was "
            + old + " is now " + replacement
        );

        report(
            "Modified index " + index + " from "
            + old + " to " + replacement
        );
    }

    /**
     * P: Print the array in aligned rows of ten.
     * PRE: The list may be empty or populated.
     * POST: Prints all elements in their current order.
     */
    private static void printList() {

        System.out.println("\nPRINTING YOUR LIST");

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%5d ", list.get(i));

            if ((i + 1) % 10 == 0
                    || i == list.size() - 1) {
                System.out.println();
            }
        }

        if (list.isEmpty()) {
            System.out.println("(empty)");
        }

        report(
            "Printed list containing " + list.size() + " values."
        );
    }

    /**
     * PRE: A prompt and Scanner are provided.
     * POST: Returns the integer entered, or null for invalid input.
     */
    private static Integer readInteger(
            Scanner input, String prompt) {

        System.out.print(prompt);
        String token = input.nextLine().trim();

        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException ex) {
            System.out.println(
                "You entered incorrect data. Please try again"
            );
            return null;
        }
    }

    /**
     * PRE: A report message is provided.
     * POST: Appends the message to Program3Report.txt.
     */
    private static void report(String text) {

        try (BufferedWriter writer = Files.newBufferedWriter(
                REPORT,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            writer.write(text);
            writer.newLine();

        } catch (IOException ex) {
            System.out.println(
                "Could not write report file: " + ex.getMessage()
            );
        }
    }
}
