
import java.util.Collection;

public interface MyList<E> extends java.util.Collection<E> {

    // ABSTRACT METHODS

    // Abstract Method: Create a new larger array, double current size + 1
    public void add(int index, E e);

    // Abstract Method: Clear the list
    public void clear();

    // Abstract Method: Retrieve the element at the index position
    public E get(int index);

    // Abstract Method: Return index of object or -1 if not found
    public int indexOf(Object e);
    public int lastIndexOf(E e);

    // Abstract Method: Remove element at specified position.
    // Shift subsequent elements left and return removed element.
    public E remove(int index);

    // Abstract Method: Replace element at specified position.
    public Object set(int index, E e);

    // Abstract Method: Return size of the array
    public int size();

    // DEFAULT METHODS

    @Override
    // PRE: Accepts the element to add.
    // POST: Adds element to the end of the array.
    public default boolean add(E e) {
        System.out.println("adding: " + e);
        add(size(), e);
        return true;
    }

    @Override
    // Copy all elements in c to the list.
    public default boolean addAll(Collection<? extends E> c) {
        // Implemented in MyArrayList.
        return false;
    }

    // Returns whether all elements in c are present.
    public default boolean containsAll(Collection<?> c) {
        // Implemented in MyArrayList.
        return false;
    }

    @Override
    // PRE: None.
    // POST: Returns true if the array is empty.
    public default boolean isEmpty() {
        return size() == 0;
    }

    @Override
    // PRE: Accepts the element to remove.
    // POST: Returns true if the element was found and removed.
    public default boolean remove(Object e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        } else {
            return false;
        }
    }

    @Override
    // Removes all elements in c from this collection.
    public default boolean removeAll(Collection<?> c) {
        // Implemented in MyArrayList.
        return false;
    }

    @Override
    // Retains only elements also present in c.
    public default boolean retainAll(Collection<?> c) {
        // Not implemented in the original interface.
        return false;
    }

    @Override
    // Returns an array containing the elements.
    public default Object[] toArray() {
        // Implemented in MyArrayList.
        return null;
    }

    @Override
    public default <T> T[] toArray(T[] array) {
        // Not implemented in the original interface.
        return null;
    }
}
