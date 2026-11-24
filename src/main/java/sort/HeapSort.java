package sort;

public class HeapSort {
    public static void sort(int[] a) {
        int n = a.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(a, i, n);
        }

        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);
            siftDown(a, 0, end);
        }
    }


    private static void siftDown(int[] a, int i, int heapSize) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < heapSize && a[left] > a[largest]) {
                largest = left;
            }
            if (right < heapSize && a[right] > a[largest]) {
                largest = right;
            }

            if (largest == i) {
                return;
            }

            swap(a, i, largest);
            i = largest;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
