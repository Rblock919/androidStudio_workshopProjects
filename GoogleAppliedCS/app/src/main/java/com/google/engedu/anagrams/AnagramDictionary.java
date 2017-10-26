package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet wordSet;
    private HashMap lettersToWord;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordList = new ArrayList<String>();
        HashSet wordSet = new HashSet<>();
        HashMap lettersToWord = new HashMap<>();

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        return true;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        String comparatorWord = sortLetters(targetWord);

        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).length() == comparatorWord.length()) {
                char[] chars = wordList.get(i).toCharArray();
                Arrays.sort(chars);
                String sorted = new String(chars);

                if (comparatorWord.equals(sorted)) {
                    result.add(wordList.get(i));
                }
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> addedLetterWordList = new ArrayList<String>();

        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String comparatorWord = sortLetters(word);

        for (int i = 0; i < alphabet.length; i++) {
            String addedLetterWord = comparatorWord.concat(alphabet[i]);

            for (int k = 0; k < wordList.size(); k++) {
                if (wordList.get(k).length() == addedLetterWord.length()) {
                    char[] chars = wordList.get(k).toCharArray();
                    Arrays.sort(chars);
                    String sorted = new String(chars);

                    if (addedLetterWord.equals(sorted)) {
                        if (wordSet.contains(wordList.get(k))) {    //  if valid
                            addedLetterWordList.add(wordList.get(k));
                            lettersToWord.put(addedLetterWord, addedLetterWordList);
                        }
                    }
                }
            }
        }

        if ()
        return result;
    }

    public String pickGoodStarterWord() {
        return "dog";
    }

    private String sortLetters (String s) {
        String sorted;

        char[] char1 = s.toCharArray();
        Arrays.sort(char1);
        sorted = new String(char1);

        return sorted;
    }
}
