package is.hi.hbv601g.timesrunningout.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class SetupActivity extends AppCompatActivity {

    private SharedPreferences mSharedPref;

    private EditText mPlayerTextField;
    private EditText mWordTextField;

    private TextView mplayersLeft;
    private TextView mwordsLeft;

    private Button mNrPlayersButton;
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

        mNextWordButton.setVisibility(View.GONE);
        mWordTextField.setVisibility(View.GONE);

        mPlayerTextField = (EditText) findViewById(R.id.playerCount);
        mWordTextField = (EditText) findViewById(R.id.wordCount);

        mNrPlayersButton = (Button) findViewById(R.id.nrPlayers);
        mNrPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText playercountEditText = (EditText) findViewById(R.id.playerCount);
                mPlayerCount = Integer.parseInt(playercountEditText.getText().toString());


                mNrPlayersButton.setVisibility(View.GONE);
                mPlayerTextField.setVisibility(View.GONE);
                mplayersLeft.setVisibility(View.VISIBLE);
                mwordsLeft.setVisibility(View.VISIBLE);
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
