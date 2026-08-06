package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for MyDeque. Required: urgent insertion at front, normal insertion at rear. */
class MyDequeTest {

    private MyDeque<String> deque;

    @BeforeEach
    void setUp() { deque = new MyDeque<>(); }

    @Test void testAddFrontRemoveFront() { /* TODO */ }
    @Test void testAddRearRemoveRear() { /* TODO */ }
    @Test void testUrgentInsertionExample() { /* TODO: addRear normal items, addFront urgent → verify front is urgent */ }
    @Test void testIsEmpty() { /* TODO */ }
    @Test void testRemoveFromEmptyFront() { /* TODO: should throw NoSuchElementException */ }
    @Test void testRemoveFromEmptyRear() { /* TODO: should throw NoSuchElementException */ }
}
