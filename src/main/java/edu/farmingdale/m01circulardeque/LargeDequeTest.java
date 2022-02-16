/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m01circulardeque;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author gerstl
 */
public class LargeDequeTest implements RunTest {

    /**
     * Runs a large, randomized test that compares a CircularStringDeque to a 
     * java.util.LinkedList with ~100,000 random operations. 
     * @return an empty string if the two work identically, an error String otherwise
     */
    public String runTest() {
        Deque theJavaDeque = new LinkedList<String>(); // note that deque is an interface
        // create our own CircularDeque of size 5
        CircularStringDeque myStringDeque = new CircularStringDeque(5);
        final int TEST_SIZE = 100_000;
        var random = new Random();
        // this is defined in java, so not necessary to compute, but I do so 
        // you can see how I compute it
        int MAX_WIDTH = (int) Math.log10(Integer.MAX_VALUE) + 1;
        // System.out.println("MAX_WIDTH is " + MAX_WIDTH);
        String s;
        for (int i = 0; i < TEST_SIZE; ++i) {

            int action = random.nextInt(6);
            // System.err.println("Iteration " + i + " action " + action);
            switch (action) {
                case 0: // isEmpty
                    if (myStringDeque.isEmpty() != theJavaDeque.isEmpty()) {
                        return "Failed at A0001";
                    }
                    break;
                case 1: // addFirst
                    s = generateRandomString(random, MAX_WIDTH);
                    myStringDeque.addFirst(s);
                    theJavaDeque.addFirst(s);
                    break;
                case 2: // addLast
                    s = generateRandomString(random, MAX_WIDTH);
                    myStringDeque.addLast(s);
                    theJavaDeque.addLast(s);
                    break;
                case 3: // getFirst
                    // threshold. Don't do if empty
                    if (theJavaDeque.isEmpty()) {
                        continue;
                    }
                    if (!myStringDeque.getFirst().equals(theJavaDeque.getFirst())) {
                        return "Failed at A0004";
                    }
                    break;
                case 4: // getLast
                    // threshold. Don't do if empty
                    if (theJavaDeque.isEmpty()) {
                        continue;
                    }
                    if (!myStringDeque.getLast().equals(theJavaDeque.getLast())) {
                        return "Failed at A0005";
                    }
                    break;
                case 5: // removeFirst
                    // threshold. Don't do if empty
                    if (theJavaDeque.isEmpty()) {
                        continue;
                    }
                    if (!theJavaDeque.removeFirst().equals(myStringDeque.removeFirst())) {
                        return "Failed at A0006";
                    }
                    break;
                case 6: // removeLast
                    // threshold. Don't do if empty
                    if (theJavaDeque.isEmpty()) {
                        continue;
                    }
                    if (!theJavaDeque.removeLast().equals(myStringDeque.removeLast())) {
                        return "Failed at A0007";
                    }
                    break;
            }

        } // for
        return "";
    }
/**
 * Produces a random number, and converts it to a left-zero-padded string
 * @param random a Random
 * @param width the desired width of the String returned
 * @return A string of the random number, left padded with 0s so that it is the correct width 
 */
    String generateRandomString(Random random, int width) {
        StringBuilder sb = new StringBuilder();
        int aNumber = random.nextInt();
        String sNumber = Integer.toString(aNumber);
        // now pad it.
        sb.delete(0, sb.length());
        while (sb.length() < width - sNumber.length()) {
            sb.append("0");
        }
        sb.append(sNumber);
        return sb.toString();
    }
}
