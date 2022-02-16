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
    public void addFirst(String addMe) {//adds first to the front of the deque

         if (count == capacity) {//this is how we determine if the array is full or not

            String temp[] = new String[capacity];//create a temp array to store current data before copying

            for (int i = 0; i < capacity; i++) {//this will copy the data from data array to the temp array
                temp[i] = data[first];//starts at first, since the new array needs to be in order
                first = nextIndex(first);////iterates to the next element 
             
            }
            capacity *= 2; //grows the capacity by the growth factor, in this case its two

            data = new String[capacity];//re declares a new array

            for (int i = 0; i < temp.length; i++) {//this loop will add the data from the temp array into the new 
                data[i] = temp[i];//the temp data is added to the new array, its alreay in order at this point
            }

            first = 0;//sets first to 0
            last = capacity / 2 - 1;//sets last to the old capacity, which is divided by 2
            
            
            
             
            first = previousIndex(first);//after all the copying and re adding, first is moved back one, 
            data[first] = addMe;//data at index first is set to addMe
            count++;//count is adjusted
            
            

        } else {//if the array does not need to be copied, then 
            first = previousIndex(first);//first is moved back one
            data[first] = addMe;//data at index first is set too addMe

            count++;//count is adjusted

        }

    }

    /**
     * Adds the parameter String to the end of the deque.
     *
     * @param addMe String to add
     */
    public void addLast(String addMe) {//adds to the back of the deque 

        if (count == capacity) {//if the array is full, the same copy method from addFirst is used 
            System.out.println("full");
            String temp[] = new String[capacity];

            for (int i = 0; i < data.length; i++) {
                temp[i] = data[first];
                first = nextIndex(first);

            }
           
            capacity *= 2;

            data = new String[capacity];

            for (int i = 0; i < temp.length; i++) {
                data[i] = temp[i];
            }

            first = 0;
            last = capacity / 2 - 1 ;  

            last = nextIndex(last);//then last is moved foward one position 
            data[last] = addMe;//data at index last is set to addMe

            count++;//count is adjusted 

        } else {// if the array is not full, then just add last 
            last = nextIndex(last);//last is moved foward one 
            data[last] = addMe;//data at index last is set to addMe

            count++;//count is adjusted
        }
        
    }

    /**
     * Remove the first item in the deque. Undefined if the deque is empty
     *
     * @return A string, the former first item in the deque.
     */
    public String removeFirst() {
      
        
        int PrevFirst = first;//sets first to the previous index
        first = nextIndex(first);//moves first to the next index 
        count--;//count is adjusted

        return data[PrevFirst];//returns data at old first
        
   
    }

    /**
     * Remove the last item in the deque. Undefined if the deque is empty
     *
     * @return A String, the former last item in the deque
     */
    public String removeLast() {
        int PrevLast = last;//sets the old last to prevLast
        last = previousIndex(last);//moves last back one 
        count--;//count is adjusted 

        return data[PrevLast];//returns the old last 
    }

    /**
     * Undefined if the deque is empty
     *
     * @return A string, the first item in the deque
     */
    public String getFirst() {
        return data[first];//returns data at index first
    }

    /**
     * Undefined if the deque is empty
     *
     * @return A string, the last item in the deque
     */
    public String getLast() {
        return data[last];//returns data at index last 
    }

    /**
     * Checks if the deque is empty. Must be used as a guard before any of the
     * first/last methods
     *
     * @return a Boolean, if the deque is empty
     */
    public Boolean isEmpty() {// ask about this 
        boolean emp = false;
        if(count == 0){
            emp = true;
        }
        
        return emp;
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
