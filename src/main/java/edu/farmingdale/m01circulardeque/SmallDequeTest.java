/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m01circulardeque;

import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author gerstl
 */
public class SmallDequeTest implements RunTest {
    
    public String runTest() {
        Deque theJavaDeque = new LinkedList<String>(); // note that deque is an interface
        // create our own CircularDeque of size 5
        CircularStringDeque myStringDeque = new CircularStringDeque(5);
        // insert 10 items at varying locations
        // compare the java Deque and our deque
        // current state [front] [back]
        theJavaDeque.addFirst("A");
        myStringDeque.addFirst("A");
        // current state [front] A [back]
        theJavaDeque.addFirst("B");
        myStringDeque.addFirst("B");
        // current state [front] B A [back]
        theJavaDeque.addLast("C");
        myStringDeque.addLast("C");
        System.out.println("---------");
        // current state [front] B A C [back]
        if (!theJavaDeque.getFirst().equals("B") || !theJavaDeque.getFirst().equals(myStringDeque.getFirst())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0001";
        }
        String rJava = (String) theJavaDeque.removeFirst();
        String rStudent = (String) myStringDeque.removeFirst();
        if (!rJava.equals("B") || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0002";
        }
        // current state [front] A C [back]
        if (!theJavaDeque.getFirst().equals("A") || !theJavaDeque.getFirst().equals(myStringDeque.getFirst())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0003";
        }
        
        rJava = (String) theJavaDeque.removeFirst();
        rStudent = (String) myStringDeque.removeFirst();
        if (!rJava.equals("A") || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0004";
        }
        System.out.println(myStringDeque.getFirst());
        // current state [front] C [back]
        
        theJavaDeque.addLast("D");
        myStringDeque.addLast("D");
        // current state [front] C D [back]
        System.out.println(myStringDeque.getFirst());
        theJavaDeque.addLast("E");
        myStringDeque.addLast("E");
        System.out.println(myStringDeque.getFirst());
        // current state [front] C D E [back]
        theJavaDeque.addLast("F");
        myStringDeque.addLast("F");
        // current state [front] C D E F[back]
        theJavaDeque.addLast("G");
        myStringDeque.addLast("G");
        System.out.println(myStringDeque.getLast());
        System.out.println(myStringDeque.getFirst());
        // current state [front] C D E F G [back]
        System.out.println("At point 1, the deque is " + myStringDeque);
        theJavaDeque.addLast("H");
        myStringDeque.addLast("H");
        // current state [front] C D E F G H [back]
        theJavaDeque.addLast("I");
        myStringDeque.addLast("I");
        // current state [front] C D E F G H I [back]
        theJavaDeque.addLast("J");
        myStringDeque.addLast("J");
        // current state [front] C D E F G H I J [back]        // current state [front] C [back]
        theJavaDeque.addLast("K");
        myStringDeque.addLast("K");
        // current state [front] C D E F G H I J K [back]
        System.out.println("At point 2, the deque is " + myStringDeque);
        String currentElement = "C";
        // now start tearing apart and comparing
        if (!theJavaDeque.getFirst().equals(currentElement) || !theJavaDeque.getFirst().equals(myStringDeque.getFirst())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0005";
        }
        rJava = (String) theJavaDeque.removeFirst();
        rStudent = (String) myStringDeque.removeFirst();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0006";
        }
        currentElement = "K";
        // current state [front] D E F G H I J K [back]
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0007";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0008";
        }
        // current state [front] D E F G H I J [back]
        currentElement = "J";
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0009";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0010";
        }
        currentElement = "D";
        // current state [front] D E F G H I [back]
        if (!theJavaDeque.getFirst().equals(currentElement) || !theJavaDeque.getFirst().equals(myStringDeque.getFirst())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0011";
        }
        rJava = (String) theJavaDeque.removeFirst();
        rStudent = (String) myStringDeque.removeFirst();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0012";
        }
        currentElement = "I";
        // current state [front] E F G H I [back]
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0013";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0014";
        }
        // current state [front] E F G H [back]
        currentElement = "H";
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            System.err.println("error. The deque is " + myStringDeque);
            return "Failed at A0015";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            return "Failed at A0016";
        }
        System.out.println("At point 3, the deque is " + myStringDeque);
        // current state [front] E F G [back]
        currentElement = "G";
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            return "Failed at A0017";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            return "Failed at A0018";
        }
        // current state [front] E F [back]
        currentElement = "F";
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            return "Failed at A0019";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            return "Failed at A0020";
        }
        // current state [front] E [back]
        currentElement = "E";
        if (!theJavaDeque.getLast().equals(currentElement) || !theJavaDeque.getLast().equals(myStringDeque.getLast())) {
            return "Failed at A0021";
        }
        rJava = (String) theJavaDeque.removeLast();
        rStudent = (String) myStringDeque.removeLast();
        if (!rJava.equals(currentElement) || !rJava.equals(rStudent)) {
            return "Failed at A0022";
        }
        if (!myStringDeque.isEmpty() || !theJavaDeque.isEmpty()) {
            return "Failed at A0023";
        }
        return "";
    }
}
