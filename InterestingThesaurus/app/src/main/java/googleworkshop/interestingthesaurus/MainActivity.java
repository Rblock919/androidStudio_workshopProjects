package googleworkshop.interestingthesaurus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.AssetManager;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.lang.*;
import java.lang.String;
import static java.lang.Character.isLetter;

public class MainActivity extends AppCompatActivity {


    static final String STATE_TEXT = "gameText";
    static final String STATE_STATUS = "gameStatus";
    private String wrongCount = "";
    private String question = "";
    private Random rand = new Random();
    private TextView gQuestion;
    private TextView gWrong;
    private TextView gScore;
    private TextView gWord;
    private Button resetB;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private int counter = 0;
    private int selector;
    private int score = 0;
    private int wrong = 0;
    private wordIntake words;
    private ThesaurusWord getWords;
    private int numQuestions = 6;
    LinkedList<ThesaurusWord> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gQuestion = (TextView)findViewById(R.id.question);
        gWrong = (TextView)findViewById(R.id.wrong);
        gScore = (TextView)findViewById(R.id.score);
        gWord = (TextView)findViewById(R.id.word);

        resetB = (Button)findViewById(R.id.reset);
        answer1 = (Button)findViewById(R.id.answer1);
        answer2 = (Button)findViewById(R.id.answer2);
        answer3 = (Button)findViewById(R.id.answer3);
        answer4 = (Button)findViewById(R.id.answer4);

        GetNewWordLists();

        resetB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                reset();
            }
        });

        answer1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                submitA1();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                submitA2();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                submitA3();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                submitA4();
            }
        });

        GenerateQuestionList();

        gWord.setText(questionList.peekFirst().getRootWord());
        gScore.setText("Score: " + score);
        gWrong.setText("Wrong: " + wrong);
        setAnswers();

    }

        public void GetNewWordLists(){
            AssetManager assetManager = getAssets();

            try {
                InputStream inputStream = assetManager.open("thesaurus.txt");
                words = new wordIntake(inputStream);

            } catch (IOException e) {
                Toast toast = Toast.makeText(this, "Could not load word data.", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public void GenerateQuestionList(){
            questionList = new LinkedList<>();

            int questionCounter = 0;

            while(questionCounter < numQuestions) {
                GetNewWordLists();
                String goodLine = words.GetGoodLine();
                String badLine = words.GetBadLine();
                getWords = new ThesaurusWord(goodLine, badLine);

                questionList.add(getWords);
                Log.e("Logging:", "We made this here");
                questionCounter++;
            }
        }

        public void setAnswers(){
            selector = rand.nextInt(4);
            if(selector == 0){
                answer1.setText(questionList.peekFirst().GetGoodWord());
                answer2.setText(questionList.peekFirst().GetBadWord());
                answer3.setText(questionList.peekFirst().GetBadWord());
                answer4.setText(questionList.peekFirst().GetBadWord());
            } else if (selector == 1){
                answer1.setText(questionList.peekFirst().GetBadWord());
                answer2.setText(questionList.peekFirst().GetGoodWord());
                answer3.setText(questionList.peekFirst().GetBadWord());
                answer4.setText(questionList.peekFirst().GetBadWord());
            } else if (selector == 2){
                answer1.setText(questionList.peekFirst().GetBadWord());
                answer2.setText(questionList.peekFirst().GetBadWord());
                answer3.setText(questionList.peekFirst().GetGoodWord());
                answer4.setText(questionList.peekFirst().GetBadWord());
            } else if (selector == 3){
                answer1.setText(questionList.peekFirst().GetBadWord());
                answer2.setText(questionList.peekFirst().GetBadWord());
                answer3.setText(questionList.peekFirst().GetBadWord());
                answer4.setText(questionList.peekFirst().GetGoodWord());
            }
            counter = 0;
        }

        public void reset(){
            numQuestions = 10;
            GenerateQuestionList();

            wrong = 0;
            score = 0;

            gWord.setText(questionList.peekFirst().getRootWord());
            gScore.setText("Score: " + score);
            gWrong.setText("Wrong: " + wrong);
            gQuestion.setText("Pick a synonm for the word below:");

            answer1.setEnabled(true);
            answer2.setEnabled(true);
            answer3.setEnabled(true);
            answer4.setEnabled(true);

            setAnswers();


        }

        public void newQuestion() {
            numQuestions--;
            if(!questionList.isEmpty() || numQuestions > 0){
                try {
                    questionList.remove();
                }
                catch(Exception e){
                    Toast toast = Toast.makeText(this, "Errored Removing from questionList", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            gScore.setText("Score: " + score);
            gWrong.setText("Wrong: " + wrong);
            gQuestion.setText("Pick a synonm for the word below:");
            if(numQuestions > 0 || !questionList.isEmpty()) {
                gWord.setText(questionList.peekFirst().getRootWord());
                setAnswers();
            }


        }

        public void submitA1(){
            String selectedWord = answer1.getText().toString();
            if(questionList.peekFirst().isGoodWord(selectedWord)){
                score++;
            } else {
                wrong++;
            }
            newQuestion();
            checkScore();
        }

        public void submitA2(){
            String selectedWord = answer2.getText().toString();
            if(questionList.peekFirst().isGoodWord(selectedWord)){
                score++;
            } else {
                wrong++;
            }
            newQuestion();
            checkScore();
        }

        public void submitA3(){
            String selectedWord = answer3.getText().toString();
            if(questionList.peekFirst().isGoodWord(selectedWord)){
                score++;
            } else {
                wrong++;
            }
            newQuestion();
            checkScore();
        }

        public void submitA4(){
            String selectedWord = answer4.getText().toString();
            if(questionList.peekFirst().isGoodWord(selectedWord)){
                score++;
            } else {
                wrong++;
            }
            newQuestion();
            checkScore();
        }

        public void checkScore(){

            gScore.setText("Score: " + score);
            gWrong.setText("Wrong: " + wrong);

            if(wrong == 3){
                gQuestion.setText("You have reached the max wrong answers, you lose.");
                gWord.setText("");
                answer1.setEnabled(false);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);
            }

            if(numQuestions <= 0){
                gQuestion.setText("You have finished the Quiz!");
                gWord.setText("");
                answer1.setEnabled(false);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);
            }
        }

}
