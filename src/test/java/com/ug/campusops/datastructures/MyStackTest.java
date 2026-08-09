package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/** Tests for MyStack. Required: normal, boundary, invalid input, undo log demo. */
class MyStackTest {

    private MyStack<Integer> stack;

    @BeforeEach
    void setUp() { stack = new MyStack<>(); }

    @Test void testPushAndPop() { 
        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
     }
    
    @Test void testPeek() { 
         stack.push(10);
        stack.push(20);

        assertEquals(20, stack.peek());

        // Peek should not remove the element
        assertEquals(2, stack.size());
        assertEquals(20, stack.peek());
     }
    @Test void testLIFOOrder() { 
          stack.push(1);
         stack.push(2);
         stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
     }
    @Test void testIsEmpty() { 
         assertTrue(stack.isEmpty());

        stack.push(100);

        assertFalse(stack.isEmpty());

        stack.pop();

        assertTrue(stack.isEmpty());;
     }
    @Test void testPopEmpty() { 
         assertThrows(
                NoSuchElementException.class,
                () -> stack.pop()
        );
     }
    @Test void testSingleElement() { 
        stack.push(42);


       assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(42, stack.peek());

        assertEquals(42, stack.pop());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
     }
}
