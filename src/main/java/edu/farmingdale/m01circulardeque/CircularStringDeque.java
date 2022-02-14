/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m01circulardeque;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class implementing a contiguous memory circular deque. note that this
 * automatically grows, and is design to be O(1)* for inserts.
 *
 * @author gerstl
 */
public class CircularStringDeque implements Iterable<String> {

    // You cannot use an extensible array (e.g., ArrayList<>). You 
    // must use an array
    String data[];
    int first;
    int last;
    int count;
    int capacity; // bufferSize

    /**
     * Allocates the initial contiguous memory block of size "size" Note that
     * there is no default ctor for this class
     *
     * @param size int with the initial size of the memory.
     */
    public CircularStringDeque(int size) {
        this.data = new String[size];
        this.first = 0;
        this.last = size - 1;//if it is set to 0, then there are errors present, first and last should not be 0
        this.count = 0;
        this.capacity = size;

    }

    /**
     * Adds the parameter String to the beginning of the deque.
     *
     * @param addMe String to add
     */
    public void addFirst(String addMe) {

         if (count == capacity) {

            String temp[] = new String[capacity];

            for (int i = 0; i < capacity; i++) {
                temp[i] = data[first];
                first = nextIndex(first);
                if (first == last) {
                    temp[i] = data[first];
                    break;
                }
            }
            capacity *= 2;//growth 

            data = new String[capacity];

            for (int i = 0; i < temp.length; i++) {
                data[i] = temp[i];
            }

            first = 0;
            last = capacity / 2 - 1;
            
            
             first = previousIndex(first);
             
             first = previousIndex(first);
            data[first] = addMe;
            count++;
            
            

        } else {
            first = previousIndex(first);
            data[first] = addMe;

            count++;

        }

    }

    /**
     * Adds the parameter String to the end of the deque.
     *
     * @param addMe String to add
     */
    public void addLast(String addMe) {

        if (count == capacity) {
            System.out.println("full");
            String temp[] = new String[capacity];

            for (int i = 0; i < data.length; i++) {
                temp[i] = data[first];
                first = nextIndex(first);
                if (first == last) {
                    temp[i] = data[first];
                    break;
                }
            }
            capacity *= 2;

            data = new String[capacity];

            for (int i = 0; i < temp.length; i++) {
                data[i] = temp[i];
            }

            first = 0;
            last = capacity / 2 - 1;

            last = nextIndex(last);
            data[last] = addMe;

            count++;

        } else {
            last = nextIndex(last);
            data[last] = addMe;

            count++;
        }
        
    }

    /**
     * Remove the first item in the deque. Undefined if the deque is empty
     *
     * @return A string, the former first item in the deque.
     */
    public String removeFirst() {
        int PrevFirst = first;
        first = nextIndex(first);
        count--;

        return data[PrevFirst];
    }

    /**
     * Remove the last item in the deque. Undefined if the deque is empty
     *
     * @return A String, the former last item in the deque
     */
    public String removeLast() {
        int PrevLast = last;
        last = previousIndex(last);
        count--;

        return data[PrevLast];
    }

    /**
     * Undefined if the deque is empty
     *
     * @return A string, the first item in the deque
     */
    public String getFirst() {
        return data[first];
    }

    /**
     * Undefined if the deque is empty
     *
     * @return A string, the last item in the deque
     */
    public String getLast() {
        return data[last];
    }

    /**
     * Checks if the deque is empty. Must be used as a guard before any of the
     * first/last methods
     *
     * @return a Boolean, if the deque is empty
     */
    public Boolean isEmpty() {
        return null;
    }

    int nextIndex(int index) {
        return (index + 1) % capacity;
    }

    int previousIndex(int index) {
        return (index == 0) ? capacity - 1 : index - 1;
    }

    /**
     * returns an Iterator<String> for the deque. Note that remove() will throw
     * an exception
     *
     * @return an Iterator defining hasNext() and next()
     */
    public Iterator<String> iterator() {
        var iterRv = new Iterator<String>() {
            // iterator local variables go here
            int whereAmI = first;
            int itemsLeft = count;

            @Override
            public boolean hasNext() {
                return itemsLeft > 0;
            } // hasNext()

            @Override
            public String next() {
                // sanity check--is there a next?
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                // decrement itemsLeft 
                --itemsLeft;
                //  return item
                String rv = data[whereAmI];
                //  move whereAmI
                whereAmI = nextIndex(whereAmI);
                return rv;
            } // next()

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            } // remove()
        }; // anon inner class iterator
        return iterRv;
    }

    /**
     * Convert the deque to String (used in println)
     *
     * @return a string representation of the deque, staring at the front, empty
     * string for empty deque
     */
    public String toString() {
        var rv = new StringBuffer();
        Iterator<String> myIter = iterator();
        while (myIter.hasNext()) {
            rv.append(" " + myIter.next());
        }
        return rv.toString();
    }
}
