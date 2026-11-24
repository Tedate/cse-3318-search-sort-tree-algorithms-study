package tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import benchmark.InputGenerator;
import org.junit.jupiter.api.Test;

public class TreeTest {
    private static final int SEED = 17;

    @Test
    void containsEveryInsertedKey() {
        int[] keys = InputGenerator.random(1000, SEED);
        BinarySearchTree bst = new BinarySearchTree();
        RedBlackTree rbt = new RedBlackTree();

        for (int key : keys) {
            bst.insert(key);
            rbt.insert(key);
        }

        for (int key : keys) {
            assertTrue(bst.contains(key));
            assertTrue(rbt.contains(key));
        }
    }

    @Test
    void doesNotContainMissingKeys() {
        BinarySearchTree bst = new BinarySearchTree();
        RedBlackTree rbt = new RedBlackTree();

        for (int key = 0; key < 100; key += 2) {
            bst.insert(key);
            rbt.insert(key);
        }

        for (int key = 1; key < 100; key += 2) {
            assertFalse(bst.contains(key));
            assertFalse(rbt.contains(key));
        }
    }

    @Test
    void ignoresDuplicateKeys() {
        int[] keys = InputGenerator.manyDuplicates(1000, 5, SEED);
        BinarySearchTree bst = new BinarySearchTree();
        RedBlackTree rbt = new RedBlackTree();

        for (int key : keys) {
            bst.insert(key);
            rbt.insert(key);
        }

        assertEquals(5, bst.size());
        assertEquals(5, rbt.size());
    }

    @Test
    void emptyTreeHasSizeAndHeightZero() {
        BinarySearchTree bst = new BinarySearchTree();
        RedBlackTree rbt = new RedBlackTree();

        assertEquals(0, bst.size());
        assertEquals(0, bst.height());
        assertFalse(bst.contains(1));

        assertEquals(0, rbt.size());
        assertEquals(0, rbt.height());
        assertFalse(rbt.contains(1));
    }

    @Test
    void sortedInputMakesBstTallButRedBlackTreeShort() {
        int n = 1000;
        BinarySearchTree bst = new BinarySearchTree();
        RedBlackTree rbt = new RedBlackTree();

        for (int key : InputGenerator.sorted(n)) {
            bst.insert(key);
            rbt.insert(key);
        }

        assertEquals(n, bst.height());

        double maxHeight = 2 * Math.log(n + 1) / Math.log(2);
        assertTrue(rbt.height() <= maxHeight);
    }
}