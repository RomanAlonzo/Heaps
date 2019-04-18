/***********
Roman Alonzo
CSCI 241
Lab 6, 11/6/2018
Implements AList
***********/

package heap;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * An ArrayList-like dynamic array class that allocates
 * new memory when needed
 */
public class AList<T> {

    protected int size; // number of elements in the AList
    protected T[] a; // the backing array storage, whose length is at least size

    /**
     * return the size of the AList
     */
    public int size() {
        return size;
    }

    /**
     * return the capacity = the size of the underlying array
     */
    protected int getCap() {
        return a.length;
    }

    /**
     * constructor: create an AList with a default capacity of 8
     */
    public AList() {
        a = createArray(8);
        size = 0;
    }

    /**
     * constructor: create an AList with the given capacity
     */
    public AList(int capacity) {
        // TODO 1 - when done, testConstructors should pass
        a = createArray(capacity);
        size = 0;
    }

    /* Grow a if newSize exceeds a's capacity. Do nothing if newSize < a.length.
     * Grow the array by allocating a new array and copying the old array's contents
     * into the new one.  This does *not* change the AList's size.*/
    protected void growIfNeeded(int newSize) {
        // TODO 2a - when growIfNeeded and resize are done, test10Resize should pass

        if (newSize > a.length) {

            T[] b = createArray(2 * a.length);

            for (int i = 0; i < a.length; i++) {
                b[i] = a[i];
            }
            a = b;
        }

    }

    /**
     * Resize the AList.
     * this *does* modify the size, and may modify the size of a.
     */
    public void resize(int newsize) {
        // TODO 2b - when growIfNeeded and resize are done, test10Resize should pass
        growIfNeeded(newsize);
        size = newsize;
    }


    /**
     * get element i from AList.
     *
     * @throws ArrayIndexOutOfBoundsException if 0 <= i < size does not hold
     */
    public T get(int i) {
        // TODO 3a - when get and put are done, test20PutGet should pass
        if (0 <= i && i < size) {
            return a[i];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    /**
     * set element i to value.
     *
     * @throws ArrayIndexOutOfBoundsException if 0 <= i < size does not hold
     */
    public void put(int i, T value) {
        // TODO 3b - when get and put are done, test20PutGet should pass
        if (0 <= i && i < size) {
            a[i] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Append value at the end of the AList, increasing size by 1.
     * grows the array if needed to fit the appended value
     */
    public void append(T value) {
        // TODO 4b - when append and pop are done, test30Append should pass.
        
        resize(size+1);
        a[size-1] = value;
    }

    /**
     * Remove the value at the end of the AList.
     * this *does* modify size and cannot modify the size of a
     *
     * @throws NoSuchElementException if size == 0
     */
    public T pop() {
        // TODO 4b - when append and pop are done, test30Append should pass
      if (size != 0){
         for (int i = a.length-1; i>= 0; i--){
            if (a[i] != null){
               T buf = a[i];
               a[i] = null;
               size--;
               return buf;
            }
         }
      }
      else{
         throw new NoSuchElementException();
      }
      return null;
    }
      

    /*  Create and return a T[] of size size.
     *  This is necessary because generics and arrays don't play well together.*/
    @SuppressWarnings("unchecked")
    protected T[] createArray(int size) {
        return (T[]) new Object[size];
    }
}
