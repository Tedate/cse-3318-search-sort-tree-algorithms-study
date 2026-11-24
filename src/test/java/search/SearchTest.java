package search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SearchTest {
    private static final int[] ARRAY = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};

    @Test
    void findsEveryElement() {
        for (int i = 0; i < ARRAY.length; i++) {
            assertEquals(i, LinearSearch.search(ARRAY, ARRAY[i]));
            assertEquals(i, BinarySearch.search(ARRAY, ARRAY[i]));
        }
    }

    @Test
    void returnsMinusOneWhenTargetIsMissing() {
        int[] missing = {1, 6, 50, 100};
        for (int target : missing) {
            assertEquals(-1, LinearSearch.search(ARRAY, target));
            assertEquals(-1, BinarySearch.search(ARRAY, target));
        }
    }

    @Test
    void returnsMinusOneForEmptyArray() {
        int[] empty = {};
        assertEquals(-1, LinearSearch.search(empty, 5));
        assertEquals(-1, BinarySearch.search(empty, 5));
    }
}