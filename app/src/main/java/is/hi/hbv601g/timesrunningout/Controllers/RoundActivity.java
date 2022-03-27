package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;

import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.R;

public class RoundActivity extends AppCompatActivity {

    private static final String TAG = "RoundActivity";

    private SharedPreferences mSharedPref;
    private Game mGame;

    private TextView mTextViewRound;

    private Button mStartTurnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = RoundActivity.this;
        mSharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        Gson gson = new Gson();
        String json = mSharedPref.getString("Game", "");
        mGame = gson.fromJson(json, Game.class);

        mTextViewRound = (TextView) findViewById(R.id.round_text);

        setRoundText(mGame.getCurrentRound());

        int result = mGame.getTeamResults().get(0);
        Log.d("RoundActivity", "team 1:"+ Integer.toString(result));
        Log.d("RoundActivity", "team 2:"+ Integer.toString(mGame.getTeamResults().get(1)));

        mStartTurnButton = (Button) findViewById(R.id.StartTurn_button);
        mStartTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoundActivity.this, TurnActivity.class);
                //i.putExtra("is.hi.hbv601g.geoquiz.answer_is_true", answerIsTrue);
                startActivity(i);
                // Toast.makeText(MainActivity.this, R.string.toast_intro, Toast.LENGTH_LONG).show();

            }
        });
    }




    private void setRoundText(int i) {
        switch(i) {
            case 1:
                mTextViewRound.setText(R.string.round_1);
                break;
            case 2:
                mTextViewRound.setText(R.string.round_2);
                break;
            case 3:
                mTextViewRound.setText(R.string.round_3);
                break;
        }
    }
}