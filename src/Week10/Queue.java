package Week10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Queue {
    public static void main(String[] args) {
        java.util.Queue<String> sentenceQueue = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("sentences.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sentenceQueue.offer(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        int lineNumber = 1;
        while (!sentenceQueue.isEmpty()) {
            String sentence = sentenceQueue.poll();
            int wordCount = countWords(sentence);
            System.out.println("Line " + lineNumber + ": " + wordCount + " " + (wordCount == 1 ? "word" : "words"));
            lineNumber++;
        }
    }

    private static int countWords(String line) {
        if (line == null || line.trim().isEmpty()) {
            return 0;
        }
        String[] words = line.trim().split("\\s+");
        return words.length;
    }
}

