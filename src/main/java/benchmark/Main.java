package benchmark;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

import search.BinarySearch;
import search.LinearSearch;
import sort.HeapSort;
import sort.InsertionSort;
import sort.MergeSort;
import sort.PivotStrategy;
import sort.QuickSort;
import tree.BinarySearchTree;
import tree.RedBlackTree;

public class Main {
    private static final int SEED = 17;
    private static final int RUNS = 7;
    private static final int LOOKUPS = 10_000;

    private static final int[] SIZES = {1000, 10000, 100000};
    private static final int[] SEARCH_SIZES = {10, 100, 1000, 10000, 100000};

    private static final String[] DISTRIBUTIONS = {
            "random", "nearlySorted", "reverseSorted", "manyDuplicates", "adversarial", "realWorld"
    };
    private static final String DATASET_FILE = "data/texas_constitution.txt";

    private static final String[] SORTS = {
            "InsertionSort", "MergeSort", "HeapSort",
            "QuickSortFirst", "QuickSortRandom", "QuickSortMedianOfThree"
    };

    private static long checksum;

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("results"));

        benchmarkSorts();
        benchmarkSearches();
        benchmarkTrees();

        System.out.println("Done.");
    }

    private static int[] makeInput(String distribution, int n) throws IOException {
        switch (distribution) {
            case "random":
                return InputGenerator.random(n, SEED);
            case "nearlySorted":
                return InputGenerator.nearlySorted(n, 0.01, SEED);
            case "reverseSorted":
                return InputGenerator.reverseSorted(n);
            case "manyDuplicates":
                return InputGenerator.manyDuplicates(n, 10, SEED);
            case "adversarial":
                return InputGenerator.adversarial(n);
            case "realWorld":
                return DatasetLoader.load(DATASET_FILE, n);
            default:
                throw new IllegalArgumentException("Unknown distribution: " + distribution);
        }
    }

    private static long median(long[] times) {
        Arrays.sort(times);
        return times[times.length / 2];
    }

    private static void runSort(String name, int[] a) {
        switch (name) {
            case "InsertionSort":
                InsertionSort.sort(a);
                break;
            case "MergeSort":
                MergeSort.sort(a);
                break;
            case "HeapSort":
                HeapSort.sort(a);
                break;
            case "QuickSortFirst":
                QuickSort.sort(a, PivotStrategy.FIRST);
                break;
            case "QuickSortRandom":
                QuickSort.sort(a, PivotStrategy.RANDOM);
                break;
            case "QuickSortMedianOfThree":
                QuickSort.sort(a, PivotStrategy.MEDIAN_OF_THREE);
                break;
            default:
                throw new IllegalArgumentException("Unknown sort: " + name);
        }
    }

    private static void benchmarkSorts() throws IOException {
        PrintWriter out = new PrintWriter("results/sorts.csv");
        out.println("algorithm,distribution,n,nanos");

        for (String name : SORTS) {
            for (String distribution : DISTRIBUTIONS) {
                for (int n : SIZES) {
                    int[] input = makeInput(distribution, n);

                    long[] times = new long[RUNS];
                    for (int run = 0; run < RUNS; run++) {
                        int[] copy = input.clone();
                        long start = System.nanoTime();
                        runSort(name, copy);
                        times[run] = System.nanoTime() - start;
                    }
                    long nanos = median(times);

                    out.println(name + "," + distribution + "," + n + "," + nanos);
                    System.out.println(name + " " + distribution + " " + n + ": " + nanos / 1_000_000.0 + " ms");
                }
            }
        }

        out.close();
    }

    private static void benchmarkSearches() throws IOException {
        PrintWriter out = new PrintWriter("results/searches.csv");
        out.println("algorithm,n,nanosPerLookup");

        for (int n : SEARCH_SIZES) {
            int[] array = InputGenerator.sorted(n);

            Random rng = new Random(SEED);
            int[] targets = new int[LOOKUPS];
            for (int i = 0; i < LOOKUPS; i++) {
                targets[i] = array[rng.nextInt(n)];
            }

            long[] times = new long[RUNS];
            for (int run = 0; run < RUNS; run++) {
                long start = System.nanoTime();
                for (int target : targets) {
                    checksum += LinearSearch.search(array, target);
                }
                times[run] = System.nanoTime() - start;
            }
            double linearNanos = (double) median(times) / LOOKUPS;

            for (int run = 0; run < RUNS; run++) {
                long start = System.nanoTime();
                for (int target : targets) {
                    checksum += BinarySearch.search(array, target);
                }
                times[run] = System.nanoTime() - start;
            }
            double binaryNanos = (double) median(times) / LOOKUPS;

            out.println("LinearSearch," + n + "," + linearNanos);
            out.println("BinarySearch," + n + "," + binaryNanos);
            System.out.println("Search " + n + ": linear " + linearNanos + " ns, binary " + binaryNanos + " ns");
        }

        out.close();
    }

    private static void benchmarkTrees() throws IOException {
        PrintWriter out = new PrintWriter("results/trees.csv");
        out.println("tree,distribution,n,insertNanos,searchNanos,height");

        for (String distribution : DISTRIBUTIONS) {
            for (int n : SIZES) {
                int[] keys = makeInput(distribution, n);
                long[] times = new long[RUNS];

                BinarySearchTree bst = null;
                for (int run = 0; run < RUNS; run++) {
                    long start = System.nanoTime();
                    bst = new BinarySearchTree();
                    for (int key : keys) {
                        bst.insert(key);
                    }
                    times[run] = System.nanoTime() - start;
                }
                long bstInsert = median(times);

                for (int run = 0; run < RUNS; run++) {
                    long start = System.nanoTime();
                    for (int key : keys) {
                        if (bst.contains(key)) {
                            checksum++;
                        }
                    }
                    times[run] = System.nanoTime() - start;
                }
                long bstSearch = median(times);

                RedBlackTree rbt = null;
                for (int run = 0; run < RUNS; run++) {
                    long start = System.nanoTime();
                    rbt = new RedBlackTree();
                    for (int key : keys) {
                        rbt.insert(key);
                    }
                    times[run] = System.nanoTime() - start;
                }
                long rbtInsert = median(times);

                for (int run = 0; run < RUNS; run++) {
                    long start = System.nanoTime();
                    for (int key : keys) {
                        if (rbt.contains(key)) {
                            checksum++;
                        }
                    }
                    times[run] = System.nanoTime() - start;
                }
                long rbtSearch = median(times);

                out.println("BinarySearchTree," + distribution + "," + n + "," + bstInsert + "," + bstSearch + "," + bst.height());
                out.println("RedBlackTree," + distribution + "," + n + "," + rbtInsert + "," + rbtSearch + "," + rbt.height());
                System.out.println("Trees " + distribution + " " + n + ": BST height " + bst.height() + ", red-black height " + rbt.height());
            }
        }

        out.close();
    }
}