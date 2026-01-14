import java.util.Collection;

public interface MyList<E> extends java.util.Collection<E> {

    //Abstract Method: Create a new larger array, double the current size + 1   
    public void add(int index, E e);

    //Abstract Method: Clear the list
    public void clear();

    //Abstract Method: Retrieve the element at the index position
    public E get(int index); 

    //Abstract Method: Return the index of the object provided or -1 if not found
    public int indexOf(Object e);    
    public int lastIndexOf(E e);

    //Abstract Method: Remove the element at the specified position
    // in this list. Shift any subsequent elements to the left.
    // Return the element that was removed from the list.  
    public E remove (int index);

    //Abstract Method: Replace the element at the specified position with the new value
    public Object set(int index, E e);

    //Abstract Method: return the size of the array
    public int size();

    @Override 
    //Default Method: Return true if this list contains no elements  
    //PRE: none
    //POST:return true if the array is empty, false if not 
    public default boolean isEmpty() {
      return size() == 0;
    }

    @Override 
    //Default Method: remove the given element if found    
    //PRE: accepts the element to remove
    //POST:return true if the element is found, false if not  
    public default boolean remove(Object e) {
      if (indexOf(e) >= 0) {
        remove(indexOf(e));
        return true;
      }
      else
        return false;
    }

    @Override
    //Default Method: add the given element to the end of the array   
    //PRE: accepts the element to remove
    //POST:adds element to the end of the array  
    public default boolean add(E e){
        add(size(), e);
        return true;
    }
 
    @Override
    //Default Method: returns null  
    public default Object[] toArray() {
        // Left as an exercise in MyArrayList Class
        return null;
    }

    //abstract method
    public boolean addAll(Collection<? extends E> c);
    /*
    //Default Method: Returns false 
    public default boolean addAll(Collection<? extends E> c){
      // Left as an exercise in MyArrayList Class
      return false;
    }  
    */
    //Default Method: Returns false
    public default boolean containsAll(Collection<?> c){
      // Left as an exercise in MyArrayList Class
      return false;
    } 

    //Default Method: Removes all the elements in c from this collection.
    public default boolean removeAll(Collection<?> c) {
      // Left as an exercise in MyArrayList Class
      return false;
    } 
}

