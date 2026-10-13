
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> extends AbstractCollection<E>
        implements MyList<E> {

    private static final int INITIAL_CAPACITY = 5;

    private E[] data;
    private int size;

    public MyArrayList() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public MyArrayList(E[] objects) {
        data = (E[]) new Object[Math.max(INITIAL_CAPACITY, objects.length)];
        size = 0;

        for (E object : objects) {
            add(object);
        }
    }

    /*
     * AI assistance (ChatGPT): Helped revise dynamic array
     * resizing so the backing array grows when additional
     * elements exceed its current capacity.
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length * 2, minCapacity);
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }
    }

    /*
     * AI assistance (ChatGPT): Helped revise indexed insertion
     * by shifting existing elements to make room for the new
     * element while preserving their order.
     */
    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }

        ensureCapacity(size + 1);

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = e;
        size++;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    /*
     * AI assistance (ChatGPT): Helped implement addAll() to append
     * elements from another collection to the custom dynamic array
     * and return whether the list changed.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;

        for (E element : c) {
            add(element);
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean contains(Object e) {
        return indexOf(e) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public int indexOf(Object e) {
        for (int i = 0; i < size; i++) {
            if (e == null ? data[i] == null : e.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        for (int i = size - 1; i >= 0; i--) {
            if (e == null ? data[i] == null : e.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /*
     * AI assistance (ChatGPT): Helped revise indexed removal so
     * later elements shift left after removal and the list size
     * is updated correctly.
     */
    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedElement = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;

        return removedElement;
    }

    @Override
    public boolean remove(Object e) {
        int index = indexOf(e);

        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
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

    @Override
    public boolean retainAll(Collection<?> c) {
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

    /*
     * AI assistance (ChatGPT): Helped implement iterator traversal
     * for the custom dynamic array and support removal through
     * the iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<E> {
        private int current = 0;
        private int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            lastReturned = current;
            return data[current++];
        }

        @Override
        public void remove() {
            if (lastReturned == -1) {
                throw new IllegalStateException();
            }

            MyArrayList.this.remove(lastReturned);
            current = lastReturned;
            lastReturned = -1;
        }
    }

    /*
     * AI assistance (ChatGPT): Helped implement replacement of
     * an element at a specified index while returning its
     * previous value.
     */
    @Override
    public E set(int index, E e) {
        checkIndex(index);

        E previousElement = data[index];
        data[index] = e;

        return previousElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }

        size = 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            array = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), size);
        }

        for (int i = 0; i < size; i++) {
            array[i] = (T) data[i];
        }

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    public void trimToSize() {
        if (size < data.length) {
            data = Arrays.copyOf(data, size);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            result.append(data[i]);

            if (i < size - 1) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }
}
