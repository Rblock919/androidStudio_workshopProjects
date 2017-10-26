package com.google.engedu.ghost;

import java.util.HashMap;
import java.lang.*;
import java.lang.String;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static java.lang.Character.isLetter;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

        if(s.length() == 1){
            isWord = true;
            return;
        }

        if(children.get(Character.toString(s.charAt(0))) == null){
            children.put(Character.toString(s.charAt(0)), new TrieNode());
        }

        if(s.length() > 1){
            children.get(Character.toString(s.charAt(0))).add(s.substring(1, s.length()));
        }
        else {
            children.get(Character.toString(s.charAt(0))).add(s);
        }



        return;
    }

    public boolean isWord(String s) {

        TrieNode ref = this;

        for(int i = 0; i < s.length(); i++){
            if(ref.children.get(Character.toString(s.charAt(i))) != null){
                ref = ref.children.get(Character.toString(s.charAt(i)));
            }
        }

        return ref.isWord;
    /*
        if(isWord){
            return true;
        }
        else if(children.get(Character.toString(s.charAt(0))) == null){
            return false;
        }
        else {
            if(s.length() > 1){
                children.get(Character.toString(s.charAt(0))).isWord(s.substring(1, s.length()));
            }
            else {
                children.get(Character.toString(s.charAt(0))).isWord(s);
            }

        }
*/
    }

    public String getAnyWordStartingWith(String s) {
        return getGoodWordStartingWith(s);
    }

    public String getGoodWordStartingWith(String s) {

        HashMap<String, TrieNode> hashRef = children;

        for(int i = 0; i < s.length(); i++){
            if(hashRef.get(Character.toString(s.charAt(i))) != null){
                hashRef = hashRef.get(Character.toString(s.charAt(i))).children;
            }
        }
        Random rand = new Random();

        boolean flag = true;



        while(flag) {
            String[] keys = hashRef.keySet().toArray(new String [0]);
            int randKey = rand.nextInt(keys.length);

            if (hashRef.get(keys[randKey]).isWord) {
                flag = false;
            }
            else {
                s = s.concat(keys[randKey]);
                hashRef = hashRef.get(Character.toString(s.charAt(s.length() - 1))).children;
            }
        }



        return s;
    }
}
