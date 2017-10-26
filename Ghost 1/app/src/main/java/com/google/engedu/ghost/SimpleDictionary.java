package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private ArrayList<String> goodOdd;
    private ArrayList<String> goodEven;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        goodOdd = new ArrayList<>();
        goodEven = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    public boolean isNotWord(String word) {
        return !(words.contains(word));
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix.equals("") || prefix == null){
            Random rand = new Random();
            int randValue = rand.nextInt(words.size());
            return words.get(randValue);
        }
        boolean flag = true;
        int L, R, m;
        L = 0;
        R = words.size() - 1;
        m = (L+R)/2;

        while(flag){
            if(words.get(m).startsWith(prefix)){
                return words.get(m);
            } else if (words.get(m).compareTo(prefix) > 0){
                R = (m -1);
                m = (L+R)/2;
            } else if (words.get(m).compareTo(prefix) < 0){
                L = (m +1);
                m = (L+R)/2;
            }
            if(R == (L+1) || L >= R) flag = false;
        }

        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        if(prefix.equals("") || prefix == null){
            Random rand = new Random();
            int randValue = rand.nextInt(words.size());
            return words.get(randValue);
        }
        goodOdd.clear();
        goodEven.clear();
        boolean flag = true;
        boolean flag2 = true;
        int L, R, m;
        L = 0;
        R = words.size() - 1;
        m = (L+R)/2;

        while(flag){
            if(words.get(m).startsWith(prefix)){
                //we have found a word that starts with the prefix, so we must find all of the words that start with this prefix
                int searchIndexL = (m - 1);
                int searchIndexR = (m + 1);

                String wtf = words.get(m);

                if(words.get(m).length() % 2 == 0){ //word is of even length
                    goodEven.add(words.get(m));
                } else goodOdd.add(words.get(m)); //word is of odd length

                while(flag2){ //search to the left of the index (found word) and see which words also start with this prefix
                    if(words.get(searchIndexL).startsWith(prefix)){
                        if(words.get(searchIndexL).length() % 2 == 0){ //word is of even length
                            goodEven.add(words.get(searchIndexL));
                            Log.e("adding word to even", words.get(searchIndexL));
                        } else {
                            goodOdd.add(words.get(searchIndexL));
                            Log.e("adding word to odd", words.get(searchIndexL));
                        }
                    } else flag2 = false;

                    searchIndexL--;
                } //end of searching for words to the left of the originally found word

                flag2 = true;

                while(flag2) {
                    if (words.get(searchIndexR).startsWith(prefix)) {
                        if (words.get(searchIndexR).length() % 2 == 0) { //word is of even length
                            goodEven.add(words.get(searchIndexR));
                            Log.e("adding word to even", words.get(searchIndexR));
                        } else {
                            goodOdd.add(words.get(searchIndexR));
                            Log.e("adding word to odd", words.get(searchIndexR));
                        }
                    } else flag2 = false;

                    searchIndexR++;
                }

                Random rand = new Random();
                boolean randBool = rand.nextBoolean();

                if(randBool){
                    return goodOdd.get(rand.nextInt(goodOdd.size()));
                } else return goodEven.get(rand.nextInt(goodEven.size()));

            } else if (words.get(m).compareTo(prefix) > 0){
                R = (m -1);
                m = (L+R)/2;
            } else if (words.get(m).compareTo(prefix) < 0){
                L = (m +1);
                m = (L+R)/2;
            }
            if(R == (L+1) || L >= R) flag = false;
        }

        return null;
    }

    public String getGoodWordStartingWith(String prefix, boolean userFirst) { //overloaded method to determine which list to choose from (even/odd) based upon who went first
        if(prefix.equals("") || prefix == null){
            Random rand = new Random();
            int randValue = rand.nextInt(words.size());
            return words.get(randValue);
        }
        goodEven.clear();
        goodOdd.clear();
        boolean flag = true;
        boolean flag2 = true;
        int L, R, m;
        L = 0;
        R = words.size() - 1;
        m = (L+R)/2;

        while(flag){
            if(words.get(m).startsWith(prefix)){
                //we have found a word that starts with the prefix, so we must find all of the words that start with this prefix
                int searchIndexL = (m - 1);
                int searchIndexR = (m + 1);

               // String wtf = words.get(m); // for debugging purposes

                if(words.get(m).length() % 2 == 0){ //word is of even length
                    goodEven.add(words.get(m));
                } else goodOdd.add(words.get(m)); //word is of odd length

                while(flag2){ //search to the left of the index (found word) and see which words also start with this prefix
                    if(words.get(searchIndexL).startsWith(prefix)){
                        if(words.get(searchIndexL).length() % 2 == 0){ //word is of even length
                            goodEven.add(words.get(searchIndexL));
                        } else goodOdd.add(words.get(searchIndexL));
                    } else flag2 = false;

                    searchIndexL--;
                } //end of searching for words to the left of the originally found word

                flag2 = true;

                while(flag2) {
                    if (words.get(searchIndexR).startsWith(prefix)) {
                        if (words.get(searchIndexR).length() % 2 == 0) { //word is of even length
                            goodEven.add(words.get(searchIndexR));
                        } else goodOdd.add(words.get(searchIndexR));
                    } else flag2 = false;

                    searchIndexR++;
                }

                Random rand = new Random();

                if(userFirst){
                    return goodOdd.get(rand.nextInt(goodOdd.size()));
                } else return goodEven.get(rand.nextInt(goodEven.size()));

            } else if (words.get(m).compareTo(prefix) > 0){
                R = (m -1);
                m = (L+R)/2;
            } else if (words.get(m).compareTo(prefix) < 0){
                L = (m +1);
                m = (L+R)/2;
            }
            if(R == (L+1) || L >= R) flag = false;
        }

        return null;
    }
}
