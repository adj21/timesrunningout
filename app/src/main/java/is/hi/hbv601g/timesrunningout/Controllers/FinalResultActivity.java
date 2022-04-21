package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601g.timesrunningout.Networking.NetworkCallback;
import is.hi.hbv601g.timesrunningout.Networking.NetworkManager;
import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.Persistence.Word;
import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class FinalResultActivity extends AppCompatActivity {

    private TextView mTextResult;
    private SharedPreferences mSharedPref;
    private Game mGame;
    private Button mPlayAgainButton;
    private TextView mPostText;
    private Button mPostButton;
    private static final String TAG = "FinalResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_result);

        Context context = FinalResultActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mSharedPref.getString("Game", "");
        mGame = gson.fromJson(json, Game.class);
        int resultOne = mGame.getTeamResults().get(0);
        int resultTwo = mGame.getTeamResults().get(1);

        mTextResult = (TextView) findViewById(R.id.result_text);

        Resources res = getResources();
        String text = String.format(res.getString(R.string.results), resultOne, resultTwo);
        mTextResult.setText(text);

        mPostText = (TextView) findViewById(R.id.post_text);
        mPostText.setVisibility(View.GONE);
        mPostButton = (Button) findViewById(R.id.post_words_button);
        mPostButton.setVisibility(View.GONE);

        //if we are in a custom game, show the user the text and button to save words to backend
        if (mGame.getCustomGame()) {
            mPostText.setVisibility(View.VISIBLE);
            mPostButton.setVisibility(View.VISIBLE);
        }

        mPlayAgainButton = (Button) findViewById(R.id.play_again_button);
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinalResultActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkManager networkManager = NetworkManager.getInstance(FinalResultActivity.this);
                int i;
                for(i=0; i<mGame.getWords().size()-1;i++) {
                    networkManager.addWord(mGame.getWords().get(i), new NetworkCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                        }
                        @Override
                        public void onFailure(String errorString) {
                        }
                    });
                }
                networkManager.addWord(mGame.getWords().get(i), new NetworkCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Words successfully saved",
                                Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    @Override
                    public void onFailure(String errorString) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "there was an error while saving the words",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                mPostText.setVisibility(View.GONE);
                mPostButton.setVisibility(View.GONE);
            }
        });
    }
}