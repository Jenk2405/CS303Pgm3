import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A dynamic array implementation for CS303 Program 3.
 *
 * PRE: Elements are stored in insertion order and indexes are zero-based.
 * POST: The backing array grows when needed and list operations preserve order.
 *
 * References:
 * W3Schools, Java Arrays:
 * https://www.w3schools.com/java/java_arrays.asp
 *
 * W3Schools, Java Generics:
 * https://www.w3schools.com/java/java_generics.asp
 *
 * W3Schools, Java Iterator:
 * https://www.w3schools.com/java/java_iterator.asp
 */
public class MyArrayList<E> extends AbstractCollection<E>
        implements MyList<E> {

    public static final int INITIAL_CAPACITY = 5;

    private E[] data;
    private int size;

    /**
     * PRE: None.
     * POST: Creates an empty array with an initial capacity of five.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * PRE: An array of objects may be supplied.
     * POST: Copies the supplied elements into this dynamic array.
     */
    public MyArrayList(E[] objects) {
        this();

        if (objects != null) {
            ensureCapacityFor(objects.length);

            for (E item : objects) {
                data[size++] = item;
            }
        }
    }

    /**
     * PRE: The requested capacity is nonnegative.
     * POST: Expands the backing array if the requested capacity is larger.
     *
     * W3Schools reference:
     * https://www.w3schools.com/java/java_arrays.asp
     */
    private void ensureCapacityFor(int needed) {
        if (needed <= data.length) {
            return;
        }

        int newCapacity = data.length;

        while (newCapacity < needed) {
            newCapacity = newCapacity * 2 + 1;
        }

        data = Arrays.copyOf(data, newCapacity);
    }

    /**
     * PRE: Index should identify an existing element.
     * POST: Throws an exception if the index is invalid.
     */
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is not in range 0.."
                + (size - 1)
            );
        }
    }

    /**
     * PRE: Index should identify an insertion position.
     * POST: Throws an exception if the position is invalid.
     */
    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is not in range 0.." + size
            );
        }
    }

    /**
     * PRE: Index is between zero and size, inclusive.
     * POST: Inserts the element at the requested position.
     * Existing elements are shifted right when necessary.
     */
    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);
        ensureCapacityFor(size + 1);

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = element;
        size++;
    }

    /**
     * PRE: An element is supplied.
     * POST: Appends the element to the end of the list.
     */
    @Override
    public boolean add(E element) {
        add(size, element);
        return true;
    }

    /**
     * PRE: Index identifies an existing element.
     * POST: Returns the element at that index.
     */
    @Override
    public E get(int index) {
        checkElementIndex(index);
        return data[index];
    }

    /**
     * PRE: None.
     * POST: Returns the number of stored elements.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * PRE: None.
     * POST: Removes all elements and resets the size to zero.
     */
    @Override
    public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    /**
     * PRE: A value is supplied for comparison.
     * POST: Returns true if the value is present.
     */
    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    /**
     * PRE: A value is supplied for comparison.
     * POST: Returns its first index, or -1 if not found.
     */
    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (value == null
                    ? data[i] == null
                    : value.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * PRE: A value is supplied for comparison.
     * POST: Returns its last index, or -1 if not found.
     */
    @Override
    public int lastIndexOf(E value) {
        for (int i = size - 1; i >= 0; i--) {
            if (value == null
                    ? data[i] == null
                    : value.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * PRE: Index identifies an existing element.
     * POST: Removes and returns that element.
     * Elements after it shift one position left.
     */
    @Override
    public E remove(int index) {
        checkElementIndex(index);

        E removed = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[--size] = null;

        return removed;
    }

    /**
     * PRE: Index identifies an existing element.
     * POST: Replaces the element and returns its previous value.
     */
    @Override
    public Object set(int index, E element) {
        checkElementIndex(index);

        E previous = data[index];
        data[index] = element;

        return previous;
    }

    /**
     * PRE: None.
     * POST: Reduces the backing array to the current number of elements.
     */
    public void trimToSize() {
        if (data.length != size) {
            data = Arrays.copyOf(data, size);
        }
    }

    /**
     * PRE: None.
     * POST: Returns an iterator for traversing the list.
     *
     * W3Schools reference:
     * https://www.w3schools.com/java/java_iterator.asp
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Inner iterator class used to traverse this dynamic array.
     */
    private class ArrayListIterator implements Iterator<E> {

        private int current;
        private int lastReturned = -1;

        /**
         * POST: Returns true if another element is available.
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * POST: Returns the next element and advances the iterator.
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            lastReturned = current;
            return data[current++];
        }

        /**
         * PRE: next() has been called and remove() has not
         * already been called for that element.
         * POST: Removes the last element returned by next().
         */
        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException(
                    "next() must be called before remove()"
                );
            }

            MyArrayList.this.remove(lastReturned);
            current = lastReturned;
            lastReturned = -1;
        }
    }

    /**
     * PRE: None.
     * POST: Returns a readable representation of the list.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }

            result.append(data[i]);
        }

        return result.append(']').toString();
    }
}