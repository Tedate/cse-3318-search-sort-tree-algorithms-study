package benchmark;

import java.util.Random;

public class InputGenerator {
    public static int[] random(int n, int seed) {
        Random rng = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rng.nextInt();
        }
        return a;
    }

    public static int[] sorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    public static int[] reverseSorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = n - 1 - i;
        }
        return a;
    }

    public static int[] nearlySorted(int n, double swapFraction, int seed) {       // swapFraction = 0.1 means 10% of the positions get swapped
        Random rng = new Random(seed);
        int[] a = sorted(n);
        int swaps = (int) (n * swapFraction);
        for (int s = 0; s < swaps; s++) {
            int i = rng.nextInt(n);
            int j = rng.nextInt(n);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        return a;
    }

    public static int[] manyDuplicates(int n, int distinctValues, int seed) {       // distinctValues is supposed to be less than n to get the expected result
        Random rng = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rng.nextInt(distinctValues);
        }
        return a;
    }

    public static int[] adversarial(int n) {       // this is already the worst case for quicksort with first element and insert into BST
        return sorted(n);
    }
}
