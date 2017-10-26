package com.example.rblock.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    public static int userScore, userTscore, aiScore, aiTscore;
    public boolean playerTurn = true;
    TextView playerScore, computerScore, turnScore, results;
    Button roButton, reButton, hoButton;


    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerScore = (TextView)findViewById(R.id.playerScore);
        computerScore = (TextView)findViewById(R.id.computerScore);
        turnScore = (TextView)findViewById(R.id.turnScore);
        results = (TextView)findViewById(R.id.gameResults);

        results.setVisibility(View.GONE);

        reButton = (Button)findViewById(R.id.resetButton);
        hoButton = (Button)findViewById(R.id.holdButton);
        roButton = (Button)findViewById(R.id.rollButton);



        roButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                rollDice();
            }
        });

        hoButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                clickHold();
            }
        });

        reButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                resetGame();
            }
        });

    }

    public void clickHold(){
        Log.e("log","Start of click hold");
        if(playerTurn){
            userScore += userTscore;
            userTscore = 0;
            roButton.setEnabled(false);
            hoButton.setEnabled(false);
            playerTurn = false;
            playerScore.setText(userScore +"");
            computerTurn();
            Log.e("playerTurn", "Player turn: "+ playerTurn);
        } else {
            aiScore += aiTscore;
            aiTscore = 0;
            roButton.setEnabled(true);
            hoButton.setEnabled(true);
            playerTurn = true;
            computerScore.setText(aiScore +"");
        }

        if(userScore > 100){
            results.setVisibility(View.VISIBLE);
            roButton.setEnabled(false);
            hoButton.setEnabled(false);
        } else if(aiScore > 100) {
            results.setVisibility(View.VISIBLE);
            results.setText("Computer Wins!");
            roButton.setEnabled(false);
            hoButton.setEnabled(false);
        }

    }

    public void computerTurn(){
        Log.e("log", "Start of computer turn");

        do{
            Log.e("log", "About to roll die for computer");
            rollDice();
        } while (aiTscore < 20 && !playerTurn);

        if(!playerTurn)
        clickHold();

    }

    public void resetGame() {
        userScore = userTscore = aiScore = aiTscore = 0;
        roButton.setEnabled(true);
        hoButton.setEnabled(true);
        playerTurn = true;
        playerScore.setText("0");
        computerScore.setText("0");
        turnScore.setText("0");
        results.setVisibility(View.GONE);
    }

    public void rollDice(){
        int roll = (rand.nextInt(6) + 1);

        ImageView diceImage = (ImageView)findViewById(R.id.imageView);

        Log.e("roll", "dice roll: " + roll);

        switch (roll) {
            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                if(playerTurn){
                    userTscore = 0;
                    //playerTurn = false;
                    roButton.setEnabled(false);
                    hoButton.setEnabled(false);

                } else {
                    aiTscore = 0;
                    //playerTurn = true;
                    roButton.setEnabled(true);
                    hoButton.setEnabled(true);
                }
                clickHold();
                break;
            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                if (playerTurn){
                    userTscore += 2;
                } else aiTscore += 2;
                break;
            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                if (playerTurn){
                    userTscore += 3;
                } else aiTscore += 3;
                break;
            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                if (playerTurn){
                    userTscore += 4;
                } else aiTscore += 4;
                break;
            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                if (playerTurn){
                    userTscore += 5;
                } else aiTscore += 5;
                break;
            case 6:
                diceImage.setImageResource(R.drawable.dice6);
                if (playerTurn){
                    userTscore += 6;
                } else aiTscore += 6;
                break;
            default:
                break;
        }

        if(playerTurn){
            turnScore.setText(userTscore + "");
        } else turnScore.setText(aiTscore + "");

    }

}
