package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.lang.*;
import java.lang.String;
import static java.lang.Character.isLetter;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    static final String STATE_TEXT = "gameText";
    static final String STATE_STATUS = "gameStatus";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    public boolean userFirst = false;
    private Random random = new Random();
    private TextView gText;
    private TextView gStatus;
    private Button challengeB;
    private Button restartB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        gText = (TextView)findViewById(R.id.ghostText);
        gStatus = (TextView)findViewById(R.id.gameStatus);
        challengeB = (Button)findViewById(R.id.challengeButton);
        restartB = (Button)findViewById(R.id.RestartButton);
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        challengeB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                challenge();
            }
        });

        restartB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                restart();
            }
        });
/*
        if(savedInstanceState != null){
            gText.setText(savedInstanceState.getString(STATE_TEXT));
            gStatus.setText(savedInstanceState.getString(STATE_STATUS));
        }
*/
        onStart(null);
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(STATE_TEXT, gText.getText().toString());
        savedInstanceState.putString(STATE_STATUS, gStatus.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        gText.setText(savedInstanceState.getString(STATE_TEXT));
        gStatus.setText(savedInstanceState.getString(STATE_STATUS));
    }

    public void restart(){
        gStatus.setText("Starting new game..");
        gText.setText("");
        userTurn = random.nextBoolean();
        if (userTurn) {
            gStatus.setText("Player's Turn");
        } else {
            gStatus.setText("Computers Turn");
            computerTurn();
        }
    }

    public void challenge(){
        if(gText.getText().toString().length() > 3 && dictionary.isWord(gText.getText().toString())){
            gStatus.setText("Player wins");
        } else {
            if (dictionary.getAnyWordStartingWith(gText.getText().toString()) != null) {
                //assemble and display the possible word that could be formed and declare computer the winner
                String possibleWord = dictionary.getAnyWordStartingWith(gText.getText().toString());
                gStatus.setText("Computer Wins. Possible word: " + possibleWord);
            } else {
                gStatus.setText("Word cannot be formed. Player wins");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        if(userTurn){
            userFirst = true;
        } else userFirst = false;
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            gStatus.setText("Player's Turn");
        } else {
            gStatus.setText("Computers Turn");
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        String currentWord = gText.getText().toString();
        if(currentWord.length() > 3 && dictionary.isWord(currentWord)){
            gStatus.setText("Computer Wins!");
            userTurn = true;
            return;
        } /*else {
            if(dictionary.getAnyWordStartingWith(gText.getText().toString()) != null){
                String wordWithNextLetter = dictionary.getAnyWordStartingWith(gText.getText().toString());
                gText.setText(gText.getText().toString() + wordWithNextLetter.substring(gText.getText().toString().length(), gText.getText().toString().length()+1));

            } else {
                gStatus.setText("Computer wins, cannot complete this word");
                userTurn = true;
                return;
            }
        } */
        else {
            Log.e("gText", gText.getText().toString());
            if(dictionary.getAnyWordStartingWith(gText.getText().toString()) != null){
                String wordWithNextLetter = dictionary.getGoodWordStartingWith(gText.getText().toString());
                Log.e("word with next letter", wordWithNextLetter);
                gText.setText(gText.getText().toString() + wordWithNextLetter.substring(gText.getText().toString().length(), gText.getText().toString().length()+1));
                Log.e("gText", gText.getText().toString());

            } else {
                gStatus.setText("Computer wins, cannot complete this word");
                userTurn = true;
                return;
            }
        }



        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        gStatus.setText(USER_TURN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {

        TextView label = (TextView) findViewById(R.id.gameStatus);

        Log.e("Keycode is currently:", (char)keyEvent.getUnicodeChar() + "");

        char pressedKey = (char) keyEvent.getUnicodeChar();

        //efficent way is to compare keycode to KeyEvent.KEYCODE_a and KeyEvent.KEYCODE_z
        //also have to do the same for capital letters

        if(isLetter(pressedKey)){
            gText.setText(gText.getText().toString() + pressedKey);
            String currentWord = gText.getText().toString();

            if(dictionary.isNotWord(currentWord)){
                Log.e("Current word", " is not word");
                gStatus.setText("This is not a valid word");
            }

            if(dictionary.isWord(currentWord)){
                Log.e("is word", currentWord);
                gStatus.setText("This is a valid word");
            }


        } else {

            label.setText("Computer's Turn");
            userTurn = false;
            computerTurn();
            return super.onKeyUp(keyCode, keyEvent);
        }



        label.setText("Computer's Turn");
        userTurn = false;
        computerTurn();
        return true;
    }
}