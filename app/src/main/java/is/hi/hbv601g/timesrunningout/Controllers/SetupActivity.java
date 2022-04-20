package is.hi.hbv601g.timesrunningout.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import com.google.gson.Gson;

import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class SetupActivity extends AppCompatActivity {

    private SharedPreferences mSharedPref;

    private TextInputLayout mPlayerTextField;
    private TextInputLayout mWordTextField;

    private Button mNextPlayerButton;
    private Button mNextWordButton;

    private int mPlayerCount;
    private int mWordCount;
    private WordService mWordService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = SetupActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        Gson gson = new Gson();
        String json = mSharedPref.getString("Game", "");

        mPlayerTextField = (TextInputLayout) findViewById(R.id.playerCount);
        mWordTextField = (TextInputLayout) findViewById(R.id.wordCount);

        mNextPlayerButton = (Button) findViewById(R.id.nextPlayer);
        mNextPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO make it work
            }
        });

        mNextWordButton = (Button) findViewById(R.id.nextWord);
        mNextWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO make it work
            }
        });
    }

    private void savePlayerCount() {
        //TODO: make it work
    }

    private void savePlayerWord() {
        //TODO: make it work
    }

    private void next() {
        //TODO: make it work
    }
}
