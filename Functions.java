
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
 * Programmer: [Your Name]
 * Assignment: CS303 Program 3 - Dynamic Arrays
 *
 * PRE: A Scanner and MyArrayList are provided when needed.
 * POST: Performs menu operations, validates input, and records
 * actions in Program3Report.txt.
 *
 * References:
 * W3Schools, Java Arrays:
 * https://www.w3schools.com/java/java_arrays.asp
 *
 * W3Schools, Java Generics:
 * https://www.w3schools.com/java/java_generics.asp
 */
public class Functions {

    private static final Path REPORT =
            Paths.get("Program3Report.txt");

    private Functions() {
        // Utility class; no object needs to be created.
    }

    /**
     * PRE: Scanner and list are initialized.
     * POST: Performs the selected menu operation.
     */
    public static String getMenuItem(
            Scanner input, MyArrayList<Integer> list) {

        printMenu();

        System.out.print("Enter your choice: ");
        String choice = input.nextLine().trim().toUpperCase();

        switch (choice) {
            case "A":
                addItem(input, list);
                break;
            case "B":
                addList(input, list);
                break;
            case "C":
                containsAllValues(input, list);
                break;
            case "D":
                deleteValue(input, list);
                break;
            case "E":
                deleteList(input, list);
                break;
            case "F":
                findValue(input, list);
                break;
            case "M":
                modifyValue(input, list);
                break;
            case "P":
                printList(list);
                break;
            case "T":
                list.trimToSize();
                System.out.println("Array trimmed to current size.");
                message("Array trimmed to current size.");
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
     * Compatibility overload for the original starter Main.java.
     */
    public static String getMenuItem(Scanner input) {
        System.out.println(
            "Choose a file and load the list before using the menu."
        );
        return "Q";
    }

    /**
     * PRE: None.
     * POST: Displays all available menu operations.
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
        System.out.println("T: Trim Array to Size");
        System.out.println("Q: Quit");
    }

    /**
     * PRE: A1input.txt and A2input.txt are available.
     * POST: Loads valid integers and skips invalid tokens.
     */
    public static MyArrayList<Integer> loadFile(Scanner input) {

        while (true) {
            System.out.print("Choose input file (1 or 2): ");
            String choice = input.nextLine().trim();

            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid choice. Enter 1 or 2.");
                continue;
            }

            Path path = Paths.get(
                choice.equals("1") ? "A1input.txt" : "A2input.txt"
            );

            MyArrayList<Integer> values = new MyArrayList<>();
            int errors = 0;

            try (Scanner fileInput = new Scanner(path)) {
                while (fileInput.hasNext()) {
                    String token = fileInput.next();

                    try {
                        values.add(Integer.parseInt(token));
                    } catch (NumberFormatException ex) {
                        errors++;
                        message("Rejected invalid input token: " + token);
                    }
                }

                System.out.println(
                    "File " + path.getFileName()
                    + " processed successfully. Loaded "
                    + values.size() + " valid integers; skipped "
                    + errors + " invalid token(s)."
                );

                message(
                    "Loaded " + path.getFileName()
                    + ": " + values.size()
                    + " valid integers; skipped "
                    + errors + " invalid token(s)."
                );

                return values;

            } catch (IOException ex) {
                System.out.println(
                    "Could not read " + path
                    + ". Make sure it is in the project folder."
                );
                message("File read error: " + ex.getMessage());
            }
        }
    }

    /**
     * A: Add one integer.
     * PRE: User enters an integer.
     * POST: Appends the integer to the list.
     */
    public static void addItem(
            Scanner input, MyArrayList<Integer> list) {

        Integer value = readInteger(input, "Enter integer to add: ");

        if (value != null) {
            list.add(value);
            System.out.println("Added: " + value);
            message("Added: " + value);
        }
    }

    /**
     * B: Add a group of integers.
     * PRE: Values are separated by spaces.
     * POST: Appends valid integers to the list.
     */
    public static void addList(
            Scanner input, MyArrayList<Integer> list) {

        System.out.print(
            "Enter values to add separated by spaces: "
        );

        String line = input.nextLine().trim();
        List<Integer> added = new ArrayList<>();

        String[] tokens = line.isEmpty()
                ? new String[0] : line.split("\\s+");

        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token);
                list.add(value);
                added.add(value);
            } catch (NumberFormatException ex) {
                System.out.println("Ignoring invalid value: " + token);
            }
        }

        System.out.println("Items added to your list: " + added);
        message("Items added: " + added);
    }

    /**
     * C: Check whether all supplied values exist.
     * PRE: Values are separated by spaces.
     * POST: Reports whether all requested values are present.
     */
    public static void containsAllValues(
            Scanner input, MyArrayList<Integer> list) {

        System.out.print(
            "Enter the values you would like to check "
            + "separated by spaces: "
        );

        String line = input.nextLine().trim();
        List<Integer> found = new ArrayList<>();
        boolean allFound = true;

        String[] tokens = line.isEmpty()
                ? new String[0] : line.split("\\s+");

        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token);

                if (list.contains(value)) {
                    found.add(value);
                } else {
                    allFound = false;
                }
            } catch (NumberFormatException ex) {
                allFound = false;
            }
        }

        if (allFound) {
            System.out.println(
                "These valid items are in your list: " + found
            );
        } else {
            System.out.println(
                "Not all values provided are in your list."
            );
        }

        message("Contains-all check completed.");
    }

    /**
     * D: Delete an element by index.
     * PRE: User supplies an existing index.
     * POST: Removes the element and reports its former value.
     */
    public static void deleteValue(
            Scanner input, MyArrayList<Integer> list) {

        while (true) {
            Integer index = readInteger(
                input, "Enter index to delete: "
            );

            if (index == null) {
                continue;
            }

            if (index < 0 || index >= list.size()) {
                System.out.println(
                    "Index " + index
                    + " is not in range. Please try again."
                );
                continue;
            }

            Integer removed = list.remove(index.intValue());

            System.out.println(
                "Item at index: " + index + " was "
                + removed + " has been removed."
            );

            message("Removed index " + index + " value " + removed);
            return;
        }
    }

    /**
     * E: Delete a group of values.
     * PRE: Values are separated by spaces.
     * POST: Removes matching values and reports those removed.
     */
    public static void deleteList(
            Scanner input, MyArrayList<Integer> list) {

        System.out.print("Enter the list of values to remove: ");

        String line = input.nextLine().trim();
        List<Integer> removed = new ArrayList<>();

        String[] tokens = line.isEmpty()
                ? new String[0] : line.split("\\s+");

        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token);

                if (list.remove(Integer.valueOf(value))) {
                    removed.add(value);
                }
            } catch (NumberFormatException ex) {
                // Invalid tokens are ignored.
            }
        }

        System.out.println("Items removed to your list: " + removed);
        message("Values removed: " + removed);
    }

    /**
     * F: Find a value.
     * PRE: User supplies an integer.
     * POST: Prints its index or reports that it was not found.
     */
    public static void findValue(
            Scanner input, MyArrayList<Integer> list) {

        Integer value = readInteger(input, "Enter value to find: ");

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

        message("Find " + value + ": position " + position);
    }

    /**
     * M: Modify an element.
     * PRE: User supplies a valid index and integer replacement.
     * POST: Replaces the value and reports old and new values.
     */
    public static void modifyValue(
            Scanner input, MyArrayList<Integer> list) {

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
            // readInteger already reports invalid input.
        }

        Integer old = (Integer) list.set(index, replacement);

        System.out.println(
            "Item at index: " + index + " was "
            + old + " is now " + replacement
        );

        message(
            "Modified index " + index + " from "
            + old + " to " + replacement
        );
    }

    /**
     * P: Print the current array in aligned rows.
     * PRE: List may be empty or populated.
     * POST: Prints all elements with consistent spacing.
     */
    public static void printList(MyArrayList<Integer> list) {

        System.out.println("\nPRINTING YOUR LIST");

        for (int i = 0; i < list.size(); i++) {
            // Fixed-width formatting aligns values of different lengths.
            System.out.printf("%5d ", list.get(i));

            if ((i + 1) % 10 == 0
                    || i == list.size() - 1) {
                System.out.println();
            }
        }

        if (list.isEmpty()) {
            System.out.println("(empty)");
        }

        message("Printed list containing " + list.size() + " values.");
    }

    /**
     * PRE: Scanner and prompt are provided.
     * POST: Returns an integer or null for invalid input.
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
     * PRE: A message is provided.
     * POST: Appends the message to Program3Report.txt.
     */
    private static void message(String text) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                REPORT,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            writer.write(text);
            writer.newLine();

        } catch (IOException ex) {
            System.out.println(
                "Note: could not write report file: "
                + ex.getMessage()
            );
        }
    }
}
