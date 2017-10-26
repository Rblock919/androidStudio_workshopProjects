package com.google.engedu.wordstack;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.lang.String;


public class MainActivity extends AppCompatActivity {

    private static final int WORD_LENGTH = 3;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();
    private StackedLayout stackedLayout;
    public static Stack<LetterTile> placedTiles;
    private String word1, word2;
    private LinearLayout linearlay1, linearlay2;


    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        stackedLayout = new StackedLayout(getBaseContext());
        placedTiles = new Stack<LetterTile>();
        linearlay1 = (LinearLayout)findViewById(R.id.word1);
        linearlay2 = (LinearLayout)findViewById(R.id.word2);
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();

                if(word.length() == WORD_LENGTH){
                   words.add(word);
                }

            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.vertical_layout);
        stackedLayout = new StackedLayout(this);
        verticalLayout.addView(stackedLayout, 3);

        View word1LinearLayout = findViewById(R.id.word1);
        word1LinearLayout.setOnTouchListener(new TouchListener());
        //word1LinearLayout.setOnDragListener(new DragListener());
        View word2LinearLayout = findViewById(R.id.word2);
        word2LinearLayout.setOnTouchListener(new TouchListener());
        //word2LinearLayout.setOnDragListener(new DragListener());
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                LetterTile tile = (LetterTile) stackedLayout.peek();
                tile.moveToViewGroup((ViewGroup) v);
                if (stackedLayout.empty()) {
                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    messageBox.setText(word1 + " " + word2);
                }
                placedTiles.push(tile);
                return true;
            }
            return false;
        }
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();
                    tile.moveToViewGroup((ViewGroup) v);
                    if (stackedLayout.empty()) {
                        TextView messageBox = (TextView) findViewById(R.id.message_box);
                        messageBox.setText(word1 + " " + word2);
                    }
                    /**
                     **
                     **  YOUR CODE GOES HERE
                     **
                     **/
                    return true;
            }
            return false;
        }
    }

    protected boolean onStartGame(View view) {
        stackedLayout.clear();
        linearlay1.removeAllViews();
        linearlay2.removeAllViews();


        TextView messageBox = (TextView) findViewById(R.id.message_box);
        messageBox.setText("Game started");

        int randWord1 = rand.nextInt(words.size());
        word1 = words.get(randWord1);
        int randWord2 = rand.nextInt(words.size());
        word2 = words.get(randWord2);

        int counter0, counter1, counter2;
        counter0 = counter1 = counter2 = 0;
        String scramWord = "";
        Log.e("word1", "word1: " + word1);
        Log.e("word2", "word2: " + word2);
        do{
            int wordSelector = rand.nextInt(2);
            String newLetter;
            if(wordSelector == 0 && counter1 < 3){
                Log.e("word1", "word1: " + word1);
                newLetter = Character.toString(word1.charAt(counter1));
                Log.e("word1", "word1: " + word1.charAt(counter1));
                scramWord = scramWord + newLetter;
                Log.e("scramWord", "scramWord: " + scramWord);
                    counter1++;
            } else if (wordSelector == 1 && counter2 < 3) {
                Log.e("word2", "word2: " + word2);

                newLetter = Character.toString(word2.charAt(counter2));
                scramWord = scramWord + newLetter;

                Log.e("scramWord", "scramWord: " + scramWord);
                //scramWord.concat(newLetter);
                    counter2++;
            }
        }while(scramWord.length() < 6);

        Log.e("scramWord", "scramWord: " + scramWord);
        LetterTile letterG;
        for(int i = (scramWord.length() - 1);i >= 0;i--){
           letterG = new LetterTile(MainActivity.this, scramWord.charAt(i));
            stackedLayout.push(letterG);
        }

        messageBox.setText(scramWord);

        return true;
    }

    protected boolean onUndo(View view) {
        if(placedTiles.isEmpty()){
            return true;
        }
        LetterTile temp = placedTiles.pop();
        temp.moveToViewGroup(stackedLayout);
        return true;
    }
}
