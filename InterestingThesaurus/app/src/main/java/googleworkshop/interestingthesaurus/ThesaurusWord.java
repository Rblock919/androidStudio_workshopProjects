package googleworkshop.interestingthesaurus;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by david.cothron on 10/19/2016.
 */

public class ThesaurusWord {

    ArrayList<String> synonyms = new ArrayList<>();
    ArrayList<String> usedGoodWords = new ArrayList<>();
    ArrayList<String> wrongWords = new ArrayList<>();
    ArrayList<String> usedBadWords = new ArrayList<>();
    String rootWord;
    StringTokenizer tokenizer;
    StringTokenizer wrongTokenizer;
    Random rand = new Random();

    public ThesaurusWord(String line, String wrongLine){
        tokenizer = new StringTokenizer(line, ",");
        wrongTokenizer = new StringTokenizer(wrongLine, ",");

        rootWord = tokenizer.nextToken(); //Get root word
        while(tokenizer.hasMoreTokens()){
            synonyms.add(tokenizer.nextToken());
        }

        while(wrongTokenizer.hasMoreTokens()){
            wrongWords.add(wrongTokenizer.nextToken());
        }
    }

    public String GetGoodWord(){
        String goodWord = synonyms.get(rand.nextInt(synonyms.size()));

        while(usedGoodWords.contains(goodWord)){
            goodWord = synonyms.get(rand.nextInt(synonyms.size()));
        }

        usedGoodWords.add(goodWord);
        return goodWord;
    }

    public String GetBadWord(){
        String badWord = wrongWords.get(rand.nextInt(wrongWords.size()));

        while(usedBadWords.contains(badWord)){
            badWord = wrongWords.get(rand.nextInt(wrongWords.size()));
        }

        usedBadWords.add(badWord);
        return badWord;
    }

    public String getRootWord(){
        return rootWord;
    }

    public void Reset(){
        usedGoodWords = new ArrayList<>();
        usedBadWords = new ArrayList<>();
    }

    public boolean isGoodWord(String inWord){
        return synonyms.contains(inWord);
    }

    public boolean isBadWord(String inWord){
        return wrongWords.contains(inWord);
    }

}
