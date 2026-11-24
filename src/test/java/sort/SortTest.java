package sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import benchmark.InputGenerator;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SortTest {
    private static final int[] SIZES = {0, 1, 2, 3, 10, 100, 1000};
    private static final int SEED = 17;

    @Test
    void sortsRandomInput() {
        for (int n : SIZES) {
            checkAllSorts(InputGenerator.random(n, SEED));
        }
    }

    @Test
    void sortsSortedInput() {
        for (int n : SIZES) {
            checkAllSorts(InputGenerator.sorted(n));
        }
    }

    @Test
    void sortsReverseSortedInput() {
        for (int n : SIZES) {
            checkAllSorts(InputGenerator.reverseSorted(n));
        }
    }

    @Test
    void sortsNearlySortedInput() {
        for (int n : SIZES) {
            checkAllSorts(InputGenerator.nearlySorted(n, 0.05, SEED));
        }
    }

    @Test
    void sortsInputWithManyDuplicates() {
        for (int n : SIZES) {
            checkAllSorts(InputGenerator.manyDuplicates(n, 5, SEED));
        }
    }

    private static void checkAllSorts(int[] input) {
        int[] expected = input.clone();
        Arrays.sort(expected);

        int[] a = input.clone();
        InsertionSort.sort(a);
        assertArrayEquals(expected, a, "InsertionSort");

        a = input.clone();
        MergeSort.sort(a);
        assertArrayEquals(expected, a, "MergeSort");

        a = input.clone();
        HeapSort.sort(a);
        assertArrayEquals(expected, a, "HeapSort");

        a = input.clone();
        QuickSort.sort(a, PivotStrategy.FIRST);
        assertArrayEquals(expected, a, "QuickSort FIRST");

        a = input.clone();
        QuickSort.sort(a, PivotStrategy.RANDOM);
        assertArrayEquals(expected, a, "QuickSort RANDOM");

        a = input.clone();
        QuickSort.sort(a, PivotStrategy.MEDIAN_OF_THREE);
        assertArrayEquals(expected, a, "QuickSort MEDIAN_OF_THREE");
    }
}