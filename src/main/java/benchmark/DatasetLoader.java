package benchmark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatasetLoader {
    public static int[] load(String fileName, int n) throws IOException {
        String text = Files.readString(Path.of(fileName)).toLowerCase();
        String[] words = text.split("[^a-z]+");

        int[] result = new int[n];
        int count = 0;

        for (String word : words) {
            if (count == n) {
                break;
            }
            if (!word.isEmpty()) {
                result[count] = word.hashCode();
                count++;
            }
        }

        return result;
    }
}
