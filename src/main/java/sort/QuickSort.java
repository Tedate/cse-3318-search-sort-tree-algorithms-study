package sort;

import java.util.Random;

public class QuickSort {
    private static final Random rng = new Random(17);

    public static void sort(int[] a, PivotStrategy strategy) {
        sort(a, 0, a.length - 1, strategy);
    }

    private static void sort(int[] a, int left, int right, PivotStrategy strategy) {
        while (left < right) {
            int p = partition(a, left, right, strategy);

            if (p - left < right - p) {
                sort(a, left, p - 1, strategy);
                left = p + 1;
            } else {
                sort(a, p + 1, right, strategy);
                right = p - 1;
            }
        }
    }

    private static int partition(int[] a, int left, int right, PivotStrategy strategy) {
        int pivotIndex = choosePivot(a, left, right, strategy);
        swap(a, pivotIndex, right);
        int pivot = a[right];

        int i = left;
        for (int j = left; j < right; j++) {
            if (a[j] < pivot) {
                swap(a, i, j);
                i++;
            }
        }

        swap(a, i, right);
        return i;
    }

    private static int choosePivot(int[] a, int left, int right, PivotStrategy strategy) {
        switch (strategy) {
            case FIRST:
                return left;
            case RANDOM:
                return left + rng.nextInt(right - left + 1);
            case MEDIAN_OF_THREE:
                int mid = left + (right - left) / 2;
                return medianIndex(a, left, mid, right);
            default:
                return -1;
        }
    }

    private static int medianIndex(int[] a, int i, int j, int k) {
        if ((a[i] <= a[j] && a[j] <= a[k]) || (a[k] <= a[j] && a[j] <= a[i])) {
            return j;
        }
        if ((a[j] <= a[i] && a[i] <= a[k]) || (a[k] <= a[i] && a[i] <= a[j])) {
            return i;
        }
        return k;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}