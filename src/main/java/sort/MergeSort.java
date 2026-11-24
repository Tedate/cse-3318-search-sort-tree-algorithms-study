package sort;

public class MergeSort {
    public static void sort(int[] a) {
        int[] temp = new int[a.length];
        sort(a, temp, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        sort(a, temp, left, mid);
        sort(a, temp, mid + 1, right);
        merge(a, temp, left, mid, right);
    }

    private static void merge(int[] a, int[] temp, int left, int mid, int right) {

        for (int k = left; k <= right; k++) {
            temp[k] = a[k];
        }

        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                a[k] = temp[j++];
            } else if (j > right) {
                a[k] = temp[i++];
            } else if (temp[i] <= temp[j]) {
                a[k] = temp[i++];
            } else {
                a[k] = temp[j++];
            }
        }
    }
}
