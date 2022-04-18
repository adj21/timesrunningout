package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class TurnActivity extends AppCompatActivity {

    private static final String TAG = "TurnActivity";

    private Button mSkipButton;
    private Button mValidateButton;
    private Button mNextButton;

    private TextView mWord;
    private TextView mCountdownText;
    private TextView mTeamText;

    private CountDownTimer mCountDownTimer;
    private long mTimeLeft = 30000; // 30 seconds

    //private boolean mTimerRunning;
    private boolean mTimerFinished = false;

    private WordService mWordService = new WordService();

    private SharedPreferences mSharedPref;
    private Game mGame;
    private int index;
    private boolean mCurrentTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn);
        startTimer();

        mCountdownText = findViewById(R.id.countdown_text);
        mWord = findViewById(R.id.mWord);
        mValidateButton = findViewById(R.id.countdown_button);
        mSkipButton = findViewById(R.id.countdown_button2);
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setVisibility(View.GONE);

        Context context = TurnActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mSharedPref.getString("Game", "");
        mGame = gson.fromJson(json, Game.class);
        index = mGame.getCurrentIndex();
        mCurrentTeam = mGame.getCurrentTeam();

        mTeamText = findViewById(R.id.team_text);
        setTeamText();

        displayWord();

        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGame = mWordService.setGuessed(index, mGame);
                int team;
                if (!mCurrentTeam) team = 1;
                else team = 2;
                mGame.incrementTeamResults(team);
                index += 1;
                // save the changed game?
                displayWord();
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index += 1;
                Log.d("TurnActivity", "I got here index = " + Integer.toString(index));
                // save the changed game?
                displayWord();
                Log.d("TurnActivity", "I got here index = " + Integer.toString(index));
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TurnActivity", "next: team = " + mCurrentTeam);
                mNextButton.setVisibility(View.GONE);
                mSkipButton.setVisibility(View.VISIBLE);
                mValidateButton.setVisibility(View.VISIBLE);
                mCountdownText.setVisibility(View.VISIBLE);
                mWord.setVisibility(View.VISIBLE);
                mCountDownTimer.cancel();
                startTimer();
                displayWord();
            }
        });

    }
        public void startTimer() {
            mTimerFinished = false;
            mTimeLeft = 30000;
            mCountDownTimer = new CountDownTimer(mTimeLeft, 1000) {
                @Override
                public void onTick(long l) {
                    mTimeLeft = l;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    mNextButton.setVisibility(View.VISIBLE);
                    mSkipButton.setVisibility(View.GONE);
                    mValidateButton.setVisibility(View.GONE);
                    mWord.setVisibility(View.GONE);
                    mCountdownText.setVisibility(View.GONE);
                    mTimerFinished = true;
                    mCountDownTimer.cancel();

                    mCurrentTeam = !mCurrentTeam; //change current team
                    setTeamText();//change team displayed
                }
            }.start();

            //mTimerRunning = true;
        }

        public void updateTimer () {
            int seconds = (int) mTimeLeft / 1000;

            String mTimeLeftText = "";

            if (seconds < 10) mTimeLeftText += "";
            mTimeLeftText += seconds;

            mCountdownText.setText(mTimeLeftText);
        }

    private void displayWord() {
        //displayGuessed();
        if (index < mGame.getWords().size()) {
            while (mGame.getGuessed(index)) {//skip words already guessed
                index++;
            }
        }
        if (((index >= mGame.getWords().size()) || mTimerFinished) && (!mWordService.isAllGuessed(mGame))) { // if the user has gone trough all words or the timer is finished, but the words are not yet all guessed
            Log.d("TurnActivity", "I got here into the >4 index = " + Integer.toString(index));
            mNextButton.setVisibility(View.VISIBLE);
            mSkipButton.setVisibility(View.GONE);
            mValidateButton.setVisibility(View.GONE);
            mWord.setVisibility(View.GONE);
            mCountdownText.setVisibility(View.GONE);
            //displayGuessed();

            index = 0;//restart index for next player
            while (mGame.getGuessed(index)) {//skip words already guessed
                index++;
            }
            mCurrentTeam = !mCurrentTeam; //change current team
            setTeamText();//change team displayed
        }
        else if((index < mGame.getWords().size()) && (!mWordService.isAllGuessed(mGame)) && !mTimerFinished) { //if the words are not finished and not all guessed and the timer is still ticking
            mWord.setText(mGame.getWord(index)); //display new word
        }
        else if (mWordService.isAllGuessed(mGame)) {//if the words are all guessed

            mGame.setCurrentRound(mGame.getCurrentRound() + 1);//set next round number
            mCurrentTeam = !mCurrentTeam; //change current team
            mGame.setCurrentTeam(mCurrentTeam);//save next playing team
            mWordService.setAllUnguessed(mGame);//set all unguessed for next round

            //save Game object changes to Shared Preferences before leaving the activity
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(mGame); //change the Game object into a String
            prefsEditor.putString("Game", json); //put the String into the shared preferences
            prefsEditor.commit();

            if (mGame.getCurrentRound() > 3) { // if all 3 rounds are finished, show results
                Intent i = new Intent(TurnActivity.this, FinalResultActivity.class);
                startActivity(i);
            }
            //otherwise go to next round
            else {
                Intent i = new Intent(TurnActivity.this, RoundActivity.class);
                startActivity(i);
            }
        }
    }

    private void setTeamText() {
        if (!mCurrentTeam) mTeamText.setText(R.string.team_1);
        else mTeamText.setText(R.string.team_2);
        Log.d("TurnActivity", "team = " + mCurrentTeam);
    }

    private void displayGuessed() {
        String guessed = "";
        for(int j = 0; j<mGame.getGuessed().size();j++) {
            guessed = guessed + Boolean.toString(mGame.getGuessed(j)) + " ";
        }
        Log.d("TurnActivity", guessed);
    }

}