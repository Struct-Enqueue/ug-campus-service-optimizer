package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for MyStack. Required: normal, boundary, invalid input, undo log demo. */
class MyStackTest {

    private MyStack<Integer> stack;

    @BeforeEach
    void setUp() { stack = new MyStack<>(); }

    @Test void testPushAndPop() { /* TODO */ }
    @Test void testPeek() { /* TODO */ }
    @Test void testLIFOOrder() { /* TODO: push 1,2,3 → pop should give 3,2,1 */ }
    @Test void testIsEmpty() { /* TODO */ }
    @Test void testPopEmpty() { /* TODO: should throw NoSuchElementException */ }
    @Test void testSingleElement() { /* TODO */ }
}
