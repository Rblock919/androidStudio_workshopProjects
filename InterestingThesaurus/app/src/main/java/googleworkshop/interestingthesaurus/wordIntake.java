package googleworkshop.interestingthesaurus;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by rblock on 10/21/2016.
 */

public class wordIntake {


    int numLines = 30260;
    //ArrayList<Integer> usedLines = new ArrayList<Integer>();
    String line;
    String wrongWords = "";
    Random rand = new Random();

    public wordIntake(InputStream _wordsFile){
        BufferedReader reader = new BufferedReader(new InputStreamReader(_wordsFile));
        int n = 0;
        int wrongLinesCount = 0;
        String tempLine;
        int correctLine = rand.nextInt(numLines);
        try {
            while((tempLine = reader.readLine()) != null) {
                //reader.skip(rand.nextInt(numLines));
                //line = reader.readLine();
                ++n;
                if(rand.nextInt(n) == 0 && wrongLinesCount < 5 && n != correctLine){
                    //wrongWords.concat(tempLine);
                    wrongWords = wrongWords + tempLine;
                    Log.e("wrongWords:", wrongWords);
                    wrongLinesCount++;
                }
                else if(n == correctLine){
                    line = tempLine;
                }
            }
            _wordsFile.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            line = "";
        }


    }

    public String GetGoodLine(){
        return line;
    }

    public String GetBadLine(){
        return wrongWords;
    }
}


