package is.hi.hbv601g.timesrunningout.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class SetupActivity extends AppCompatActivity {

    private SharedPreferences mSharedPref;

    private EditText mPlayerTextField;
    private EditText mWordTextField;

    private TextView mPlayersLeft;
    private TextView mWordsLeft;

    private Button mNrPlayersButton;
    private Button mNextWordButton;

    private int mPlayerCount;
    private int mWordCount;
    private List<String> mWords = new ArrayList<String>();
    private WordService mWordService;
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = SetupActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        mWordCount = 5;

        mNextWordButton = (Button) findViewById(R.id.nextWord);
        mWordTextField = (EditText) findViewById(R.id.wordCount);

        mNextWordButton.setVisibility(View.GONE);
        mWordTextField.setVisibility(View.GONE);

        mPlayerTextField = (EditText) findViewById(R.id.playerCount);
        mPlayersLeft = (TextView)  findViewById(R.id.playersLeft);

        mWordsLeft = (TextView)  findViewById(R.id.wordsLeft);

        mNrPlayersButton = (Button) findViewById(R.id.nrPlayers);
        mNrPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlayerCount();
            }
        });


        mNextWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlayerWord();
            }
        });
    }

    private void savePlayerCount() {
        mPlayerCount = Integer.parseInt(mPlayerTextField.getText().toString());
        mNrPlayersButton.setVisibility(View.GONE);
        mPlayerTextField.setVisibility(View.GONE);
        String text = String.format(getResources().getString(R.string.playersleft), mPlayerCount);
        mPlayersLeft.setText(text);
        mPlayersLeft.setVisibility(View.VISIBLE);
        mWordsLeft.setVisibility(View.VISIBLE);

        mNextWordButton.setVisibility(View.VISIBLE);

        String text2 = String.format(getResources().getString(R.string.wordsleft), mWordCount);
        mWordsLeft.setText(text2);
        mWordTextField.setVisibility(View.VISIBLE);
    }

    private void savePlayerWord() {
        mWordCount--;
        if(mWordCount > 0 && mPlayerCount > 0) {
            String text2 = String.format(getResources().getString(R.string.wordsleft), mWordCount);
            mWordsLeft.setText(text2);
            String word = mWordTextField.getText().toString();

            //save word
            mWords.add(word);
            mWordTextField.setText("");
        }
        else if(mWordCount == 0 && mPlayerCount > 1) {
            //save word
            String word = mWordTextField.getText().toString();
            mWords.add(word);

            Toast toast = Toast.makeText(SetupActivity.this, R.string.toast_passDevice, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            mPlayerCount --;
            String text = String.format(getResources().getString(R.string.playersleft), mPlayerCount);
            mPlayersLeft.setText(text);
            mWordCount = 5;
            String text2 = String.format(getResources().getString(R.string.wordsleft), mWordCount);
            mWordsLeft.setText(text2);
            mWordTextField.setText("");
        }
        else {
            //save word
            String word = mWordTextField.getText().toString();
            mWords.add(word);
            next();
        }
    }

    private void next() {
        Context context = SetupActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //retrieve game
        Gson gson = new Gson();
        String json = mSharedPref.getString("Game", "");
        mGame = gson.fromJson(json, Game.class);
        //add words and guessed to game
        mGame.setWords(mWords);
        mWordService = new WordService(mWords);;
        mWordService.setAllUnguessed(mGame);

        //save game to shared preferences
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        json = gson.toJson(mGame); //change the new Game object into a String
        prefsEditor.putString("Game", json); //put the String into the shared preferences
        prefsEditor.commit();

        Intent i = new Intent(SetupActivity.this, RoundActivity.class);
        startActivity(i);
    }

}
