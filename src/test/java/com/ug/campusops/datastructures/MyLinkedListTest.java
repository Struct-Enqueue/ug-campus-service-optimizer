package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for MyLinkedList. Required: normal, boundary, invalid input, iterator demo. */
class MyLinkedListTest {

    private MyLinkedList<String> list;

    @BeforeEach
    void setUp() { list = new MyLinkedList<>(); }

    @Test void testAddFirst() { /* TODO */ }
    @Test void testAddLast() { /* TODO */ }
    @Test void testInsertAfter() { /* TODO */ }
    @Test void testRemove() { /* TODO */ }
    @Test void testIterator() { /* TODO: iterate and verify order */ }
    @Test void testEmptyList() { /* TODO */ }
    @Test void testSingleElement() { /* TODO */ }
    @Test void testRemoveNonExistent() { /* TODO: should return false */ }
}
