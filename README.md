# CSE 3318 Honors Project

An empirical study of the searching, sorting, and tree algorithms from CSE 3318.
Each algorithm is written from scratch in Java and timed on several kinds of input,
to compare measured performance with the Big-O predictions from lecture.

## What is implemented

- **Searching:** linear search, binary search
- **Sorting:** insertion sort, merge sort, heap sort, and quicksort with three pivot strategies (first element, random, median of three)
- **Trees:** binary search tree, red-black tree

## Project layout

```
src/main/java/search      the two searches
src/main/java/sort        the sorts
src/main/java/tree        the two trees
src/main/java/benchmark   input generator, dataset loader, and Main
src/test/java             unit tests
data/                     the real-world dataset
results/                  CSV files written by the benchmark
report/plots/             charts made from the CSV files
```

## Requirements

- Java 17 or newer
- Maven (IntelliJ includes it)

## Running the unit tests

In IntelliJ, right-click `src/test/java` and choose **Run 'All Tests'**.

From a terminal:

```
mvn test
```

## Running the benchmark

In IntelliJ, open `src/main/java/benchmark/Main.java` and run `main`.

The benchmark takes several minutes. It writes three files to `results/`:

| File | Contents |
|---|---|
| `sorts.csv` | time for each sort on each input type and size |
| `searches.csv` | average time per lookup for each search |
| `trees.csv` | insert time, search time, and height for each tree |

Every measurement is the median of 7 runs.

## Inputs

Sorts and trees are tested at sizes 1,000, 10,000, and 100,000 on six kinds of input:

| Name | Description |
|---|---|
| `random` | random integers |
| `nearlySorted` | sorted, then 1% of the elements swapped at random |
| `reverseSorted` | descending order |
| `manyDuplicates` | random values chosen from only 10 distinct numbers |
| `adversarial` | already sorted, the worst case for first-pivot quicksort and for the binary search tree |
| `realWorld` | the words of the Texas Constitution, each turned into a number |

Searches are tested on sorted arrays of sizes 10 to 100,000.

All random inputs use a fixed seed, so every run uses the same data.

## Dataset

The real-world input is the text of the Texas Constitution, saved as
`data/texas_constitution.txt`. The official text is available from the
Texas Legislature at https://statutes.capitol.texas.gov.