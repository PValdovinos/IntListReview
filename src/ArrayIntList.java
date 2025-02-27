import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIntList implements IntList
{
    // internal (private) representation
    private int[] buffer;
    private int size;       // number of "spots used" in the buffer
    private final static int INITAL_CAPACITY = 10;

    public ArrayIntList()
    {
        buffer = new int[INITAL_CAPACITY];
        size = 0;
    }


    /**
     * Prepends (inserts) the specified value at the front of the list (at index 0).
     * Shifts the value currently at the front of the list (if any) and any
     * subsequent values to the right.
     *
     * This is a relatively slow operation
     * Linear time - O(size) or O(n)
     * because we have to shift every item
     * in the buffer to the right to make room
     * at the front for the new item
     *
     * @param value value to be inserted
     */
    @Override
    public void addFront(int value) // slow, linear time O(size), shift size items right
                                    // assuming no resize, if resize needed, will be even slower
    {
        // check if full
        if(size == buffer.length)
        {
            resize(2 * buffer.length);
        }
        // open a spot at index 0 where value will be saved
        // shift everything over to the right by 1 position
        for(int i = size; i >= 1; i--)
        {
            buffer[i] = buffer[i - 1];
        }


        // put value in position [0]
        buffer[0] = value;

        size ++;
    }

    /**
     * Appends (inserts) the specified value at the back of the list (at index size()-1).
     *
     * @param value value to be inserted
     */
    @Override
    public void addBack(int value) // fast, constant time if no resize
                                   // can be slow, linear time if resize O(n) is needed
    {
        // check to see if we still have room (capacity)
        if(size == buffer.length)
        {
            // if the size matches the  capacity, then I know I'm "full"
            // and I need to resize (create a new larger buffer and copy
            // the values over from the older smaller buffer)

            // make the new size twice the existing capacity
            resize(2 * buffer.length);
        }

        buffer[size] = value;
        size++;
    }

    /**
     * Inserts the specified value at the specified position in this list.
     * Shifts the value currently at that position (if any) and any subsequent
     * values to the right.
     *
     * @param index index at which the specified value is to be inserted
     * @param value value to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void add(int index, int value) // can be as good as addBack(), can be as bad/slow as addFront()
                                          // worst case, add at index 0 & resize, linear O(size)
                                          // best case, add at index size & no resize, constant O(1)
                                          // average case, O(1/2 n)
    {
        if(index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("The index is out of range");
        }

        // check if full
        if(size == buffer.length)
        {
            resize(2 * buffer.length);
        }

        // move elements to right
        for (int i = size; i > index; i--)
        {
            buffer[i] = buffer[i - 1];
        }

        buffer[index] = value;
        size++;
    }

    /**
     * Removes the value located at the front of the list
     * (at index 0), if it is present.
     * Shifts any subsequent values to the left.
     */
    @Override
    public void removeFront() // slow, linear time O(size)
    {
        if(size == 0)
        {
            throw new IllegalStateException("List is empty");
        }

        for (int i = 0; i < size - 1; i++)
        {
            buffer[i] = buffer[i + 1];
        }

        buffer[size - 1] = 0;
        size--;
    }

    /**
     * Removes the value located at the back of the list
     * (at index size()-1), if it is present.
     */
    @Override
    public void removeBack() // fast, constant time O(1)
    {
        if(size == 0)
        {
            throw new IllegalStateException("Already empty!");
        }

        size--;
        buffer[size] = 0;

//        for(int i = 0; i < buffer.length; i++)
//        {
//            buffer[i] = 0;
//        }
//        size--;
    }

    /**
     * Removes the value at the specified position in this list.
     * Shifts any subsequent values to the left. Returns the value
     * that was removed from the list.
     *
     * @param index the index of the value to be removed
     * @return the value previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public int remove(int index) // worst case - add at index 0 & resize, linear time O(size)
                                 // best case, add at index size & no resize, constant time O(1)
                                 // average case, O(1/2 n)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        int removedValue = buffer[index];

        for (int i = index; i < size - 1; i++) {
            buffer[i] = buffer[i + 1];
        }

        buffer[size - 1] = 0;
        size--;

        return removedValue;
    }

    /**
     * Returns the value at the specified position in the list.
     *
     * @param index index of the value to return
     * @return the value at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public int get(int index) // fast, constant time, O(1)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        return buffer[index];
    }

    /**
     * Returns true if this list contains the specified value.
     *
     * @param value value whose presence in this list is to be searched for
     * @return true if this list contains the specified value
     */
    @Override
    public boolean contains(int value) // worst case - linear time O(size)
                                       // when value is not in list or when value is last in list
    {
        for (int i = 0; i < size; i++)
        {
            if (buffer[i] == value)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the index of the first occurrence of the specified value
     * in this list, or -1 if this list does not contain the value.
     *
     * @param value value to search for
     * @return the index of the first occurrence of the specified value in this list
     * or -1 if this list does not contain the value
     */
    @Override
    public int indexOf(int value) // worst case - linear time O(size)
                                  // when value is not in list or when value is last in list
    {
        for (int i = 0; i < size; i++)
        {
            if (buffer[i] == value)
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if this list contains no values.
     *
     * @return true if this list contains no values
     */
    @Override
    public boolean isEmpty() // fast, constant time O(1)
    {
        return size == 0;
    }

    /**
     * Returns the number of values in this list.
     *
     * @return the number of values in this list
     */
    @Override
    public int size() // fast, constant time O(1)
    {
        return size;
    }

    /**
     * Removes all the values from this list.
     * The list will be empty after this call returns.
     */
    @Override
    public void clear()
    {
        size = 0;
    }

    private void resize(int newSize) // "slow", linear time - O(n) or O(size),
                                     // it is slow because it depends on the elements being copied over
    {
        // create a new array that is of the new size
        int[] temp = new int[newSize];

        // copy over values from existing buffer
        for(int i = 0; i < size; i++)
        {
            temp[i]= buffer[i];
        }

        // make the switchover
        buffer = temp;

    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator()
    {
        // return a new instance of the helper iterator class (below)
        return new ArrayIntListIterator();
    }

    @Override
    public String toString() // slow, linear time O(size) because we have to visit every item
    {
        if (size == 0)
        {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(buffer[0]);

        for (int i = 1; i < size; i++)
        {
            sb.append(", ");
            sb.append(buffer[i]);
        }

        sb.append("]");
        return sb.toString();
    }

    // nested or inner class (helper class)
    public class ArrayIntListIterator implements Iterator<Integer>
    {
        private int currentPosition;

        public ArrayIntListIterator()
        {
            currentPosition = 0;
        }

        @Override
        public boolean hasNext()
        {
            //can also use shortcut like:
            // return (currentPosition < size());   (this is all that is needed to do same as code below)
            if (currentPosition < size)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public Integer next()
        {
            // just to be safe - make sure there is a next
            if(!hasNext())
            {
                throw new NoSuchElementException();
            }

            int value = get(currentPosition);
            currentPosition++;
            return value;
        }
    }

} // end of ArrayIntList
