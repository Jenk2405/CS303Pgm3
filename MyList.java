import java.util.Collection;

/**
 * Generic interface for the CS303 dynamic array assignment.
 *
 * Reference: W3Schools, Java Generics
 * https://www.w3schools.com/java/java_generics.asp
 */
public interface MyList<E> extends Collection<E> {

    // ABSTRACT METHODS

    /**
     * PRE: Index is between zero and size, inclusive.
     * POST: Inserts the element at the specified index.
     */
    public void add(int index, E e);

    /**
     * PRE: None.
     * POST: Removes all elements.
     */
    public void clear();

    /**
     * PRE: Index identifies an existing element.
     * POST: Returns the element at that index.
     */
    public E get(int index);

    /**
     * PRE: An object is provided for comparison.
     * POST: Returns its first index, or -1 if not found.
     */
    public int indexOf(Object e);

    /**
     * PRE: An element is provided for comparison.
     * POST: Returns its last index, or -1 if not found.
     */
    public int lastIndexOf(E e);

    /**
     * PRE: Index identifies an existing element.
     * POST: Removes and returns the element at that index.
     */
    public E remove(int index);

    /**
     * PRE: Index identifies an existing element.
     * POST: Replaces that element and returns its previous value.
     */
    public Object set(int index, E e);

    /**
     * PRE: None.
     * POST: Returns the number of elements in the collection.
     */
    public int size();


    // DEFAULT METHODS

    /**
     * PRE: An element is provided.
     * POST: Adds it to the end of the list.
     */
    @Override
    public default boolean add(E e) {
        add(size(), e);
        return true;
    }

    /**
     * PRE: A collection is provided.
     * POST: Adds all its elements to this collection.
     */
    @Override
    public default boolean addAll(
            Collection<? extends E> c) {

        boolean changed = false;

        for (E element : c) {
            add(element);
            changed = true;
        }

        return changed;
    }

    /**
     * PRE: A collection is provided.
     * POST: Returns true if every element in it is present.
     */
    @Override
    public default boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * PRE: None.
     * POST: Returns true if the collection is empty.
     */
    @Override
    public default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * PRE: An object is provided.
     * POST: Removes its first occurrence if found.
     */
    @Override
    public default boolean remove(Object e) {
        int index = indexOf(e);

        if (index >= 0) {
            remove(index);
            return true;
        }

        return false;
    }

    /**
     * PRE: A collection is provided.
     * POST: Removes all elements also present in c.
     */
    @Override
    public default boolean removeAll(Collection<?> c) {
        boolean changed = false;

        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                changed = true;
            }
        }

        return changed;
    }

    /**
     * PRE: A collection is provided.
     * POST: Retains only elements also present in c.
     */
    @Override
    public default boolean retainAll(Collection<?> c) {
        boolean changed = false;

        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                changed = true;
            }
        }

        return changed;
    }

    /**
     * PRE: None.
     * POST: Returns an array containing all elements.
     */
    @Override
    public default Object[] toArray() {
        Object[] result = new Object[size()];
        int index = 0;

        for (E element : this) {
            result[index++] = element;
        }

        return result;
    }

    /**
     * PRE: An array is provided.
     * POST: Returns an array containing all elements, using
     * the supplied array when it is large enough.
     */
    @Override
    public default <T> T[] toArray(T[] array) {
        int count = size();

        @SuppressWarnings("unchecked")
        T[] result = array.length >= count
                ? array
                : (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), count);

        int index = 0;

        for (E element : this) {
            result[index++] = (T) element;
        }

        if (result.length > count) {
            result[count] = null;
        }

        return result;
    }
}