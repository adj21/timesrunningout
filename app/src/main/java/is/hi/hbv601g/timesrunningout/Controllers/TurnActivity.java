package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class TurnActivity extends AppCompatActivity {

    private Button mSkipButton;
    private Button mValidateButton;
    private Button mNext;//need to code this to alternate playing team, invisible when timer running

    private TextView mWord;
    private TextView mTimerText;
    private TextView countdownText;

    private CountDownTimer mCountDownTimer;
    private long mTimeLeft = 30000; // 30 seconds

    private boolean mTimerRunning;


    private WordService mWordService = new WordService();


    private SharedPreferences mSharedPref;
    private Game mGame;
    int index;
    int mCurrentTeam = 1; //start with team 1 (TODO: this might need to be kept on the game object when adding an activity to review turn results)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn);
        startTimer();

        countdownText = findViewById(R.id.countdown_text);
        mWord = findViewById(R.id.mWord);
        mValidateButton = findViewById(R.id.countdown_button);
        mSkipButton = findViewById(R.id.countdown_button2);


        Context context = TurnActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mSharedPref.getString("Game", "");
        mGame = gson.fromJson(json, Game.class);
        index = mGame.getCurrentIndex();
        displayWord();

        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGame = mWordService.setGuessed(index, mGame);
                mGame.incrementTeamResults(mCurrentTeam);
                index +=1;
                // save the changed game?
                displayWord();
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index += 1;
                // save the changed game?
                displayWord();
            }
        });

        public void startTimer() {
            mCountDownTimer = new CountDownTimer(mTimeLeft, 1000) {
                @Override
                public void onTick(long l) {
                    mTimeLeft = l;
                    updateTimer();
                }

                @Override
                public void onFinish(){

                }
            }.start();

            mTimerRunning = true;
        }

        public void updateTimer() {
            int seconds = (int) mTimeLeft % 30000 / 1000;

            String mTimeLeftText = "";

            if (seconds < 10)  mTimeLeftText += "0";
            mTimeLeftText += seconds;

            countdownText.setText(mTimeLeftText);
        }

    }

    private void displayWord() {
        if(index < mGame.getWords().size()) {
            mWord.setText(mGame.getWord(index));
        }
        else {
            mGame.setCurrentRound(mGame.getCurrentRound() + 1);
            mGame.setCurrentIndex(index);
            //save Game object changes to Shared Preferences before leaving the activity
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(mGame); //change the Game object into a String
            prefsEditor.putString("Game", json); //put the String into the shared preferences
            prefsEditor.commit();

            Intent i = new Intent(TurnActivity.this, RoundActivity.class);
            startActivity(i);
        }
    }

}