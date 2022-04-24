package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601g.timesrunningout.Networking.NetworkCallback;
import is.hi.hbv601g.timesrunningout.Networking.NetworkManager;
import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.Persistence.Word;
import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SharedPreferences mSharedPref;
    private Button mPlayButton;
    private Button mPlayCustomButton;
    private WordService mWordService;
    private Game mGame;
    private NetworkManager mNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        mPlayCustomButton = (Button) findViewById(R.id.play_custom);
        mPlayCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCustomGame();
            }
        });
    }
    private void startGame() {
        mNetworkManager = NetworkManager.getInstance(MainActivity.this);
        mNetworkManager.getnWords(4, new NetworkCallback<List<Word>>() {
            @Override
            public void onSuccess(List<Word> result) {
                List<Word> words = result;
                Log.d(TAG, "Successfully fetched words.");
                List<String> wordsStrings = new ArrayList();
                for(Word word : words){
                    wordsStrings.add(word.getValue());
                }
                mWordService = new WordService(wordsStrings);
                mGame = new Game(mWordService.getWords());

                SharedPreferences.Editor prefsEditor = mSharedPref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(mGame); //change the Game object into a String
                prefsEditor.putString("Game", json); //put the String into the shared preferences
                prefsEditor.commit();
                Intent i = new Intent(MainActivity.this, RoundActivity.class);
                startActivity(i);
            }
            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get words: " + errorString);
            }
        });
    }

    private void startCustomGame() {
        Context context = MainActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Game mGame = new Game();//create empty game

        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mGame); //change the Game object into a String
        prefsEditor.putString("Game", json); //put the String into the shared preferences
        prefsEditor.commit();

        Intent i = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(i);
    }
}