import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedIntList implements IntList
{
    // helper inner/nested class
    public class Node
    {
        int data;       // holds the data value
        Node next;      // holds address of next node

        public Node()
        {
            data = 0;
            next = null;
        }

        // T = 2 is O(1) constant time
        public Node(int data, Node next)
        {
            this.data = data;
            this.next = next;
        }
    } // end of class Node

    // field for LinkedIntList class

    private Node head;      // address of first node in list
    private int size;       // number of nodes/items in list

    /*

    */
    public LinkedIntList()
    {
        head = null;
        size = 0;
    }


    /**
     * Prepends (inserts) the specified value at the front of the list (at index 0).
     * Shifts the value currently at the front of the list (if any) and any
     * subsequent values to the right.
     *
     * T = 7 is O(1) constant time
     *
     * @param value value to be inserted
     */
    @Override
    public void addFront(int value)
    {
        // can erase the if statement since it does the same as else, nice to write it out just in case
        // if the list is empty
        if(head == null)
        {
            head = new Node(value, null);
        }
        else
        {
            // if the list is not empty
            head = new Node(value, head);

        }

        size++;

    }

    /**
     * Appends (inserts) the specified value at the back of the list (at index size()-1).
     *
     * if list is empty (go into if), T = 5 which is O(1) constant time
     * if list is not empty (go into else), T = 2 * size + 6, which is O(n) linear
     *                                      T = 2n + 6, which is 0(n) linear
     *
     * @param value value to be inserted
     */
    @Override
    public void addBack(int value)
    {
        // if list is empty
        if (head == null)
        {
            head = new Node(value, null);
        }
        else {
            // if list is not empty
            Node current = head;
            // loop and stop on last node in list
            // (but not all the way to null)
            while (current.next != null)
            {
                // move current forward
                current = current.next;
            }
            // when I am here - current is referencing the last node
            current.next = new Node(value, null);
        }
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
    public void add(int index, int value)
    {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        if (index == 0) {
            addFront(value);
        } else if (index == size) {
            addBack(value);
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = new Node(value, current.next);
            size++;
        }
    }

    /**
     * Removes the value located at the front of the list
     * (at index 0), if it is present.
     * Shifts any subsequent values to the left.
     */
    @Override
    public void removeFront()
    {
        if(head != null)
        {
            head = head.next;
            size--;
        }
    }

    /**
     * Removes the value located at the back of the list
     * (at index size()-1), if it is present.
     */
    @Override
    public void removeBack()
    {
        if (head == null) {
            return;
        } else if (head.next == null) {
            // Only one element in the list
            head = null;
        } else {
            Node current = head;
            while (current.next.next != null) {
                current = current.next;
            }
            current.next = null;
        }
        size--;
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
    public int remove(int index)
    {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        int removedValue;
        if (index == 0) {
            removedValue = head.data;
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedValue = current.next.data;
            current.next = current.next.next;
        }
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
    public int get(int index)
    {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns true if this list contains the specified value.
     *
     * @param value value whose presence in this list is to be searched for
     * @return true if this list contains the specified value
     */
    @Override
    public boolean contains(int value)
    {
        Node current = head;
        while(current != null)
        {
            if(current.data == value)
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
    public int indexOf(int value)
    {
        int count = 0;
        Node current = head;
        while(current != null)
        {
            if(current.data == value)
            {
                return count;
            }
            count++;
            current = current.next;
        }
        return -1;
    }

    /**
     * Returns true if this list contains no values.
     *
     * @return true if this list contains no values
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns the number of values in this list.
     *
     * @return the number of values in this list
     */
    @Override
    public int size()
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
        head = null;
        size = 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator()
    {
        // LinkedIterator iterator = new LinkedIterator();
        return new LinkedIterator();
    }

    public void print()
    {
        //create temp variable
        //copy address from head
        Node curr = head;
        while(curr != null)
        {
            System.out.println(curr.data);
            curr = curr.next;
        }
    }


    @Override
    public String toString()
    {
        if (head == null)
        {
            //if list is empty, indicate with []
            return "[]";
        }

        // if I get here, the list is not empty for sure
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node current = head;

        // stop one before the last node
        while (current.next != null)
        {
            sb.append(current.data);
            sb.append(", ");

            current = current.next;
        }

        // add in the last node
        sb.append(current.data);

        sb.append("]");
        return sb.toString();
    }

    // helper class
    public class LinkedIterator implements Iterator<Integer>
    {
        // keep track of my current position
        private Node current;       // holds address of current node

        public LinkedIterator()
        {
            // start the current position at the first node in list
            current = head;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext()
        {
            // return (current!= null); <-- this works too

            if(current == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Integer next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();
            }

            int result = current.data;
            current = current.next;
            return result;
        }
    }
}
