package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for HashTable. Required: collision statistics at different load factors. */
class HashTableTest {

    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() { table = new HashTable<>(); }

    @Test void testPutAndGet() { /* TODO */ }
    @Test void testRemove() { /* TODO */ }
    @Test void testContainsKey() { /* TODO */ }
    @Test void testCollisionHandling() { /* TODO: insert keys that hash to same bucket */ }
    @Test void testLoadFactor() { /* TODO: insert many keys, check getLoadFactor() */ }
    @Test void testCollisionStats() { /* TODO: compare collisions at table sizes 10, 37, 97, 197 */ }
    @Test void testGetNonExistent() { /* TODO: should return null */ }
    @Test void testEmptyTable() { /* TODO */ }
    @Test void testUpdateExistingKey() { /* TODO: put same key twice, verify value updated */ }
}
