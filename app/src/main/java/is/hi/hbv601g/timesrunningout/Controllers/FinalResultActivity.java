package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.*;

import com.google.gson.Gson;

import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.R;

public class FinalResultActivity extends AppCompatActivity {

    private TextView mTextResult;
    private SharedPreferences mSharedPref;
    private Game mGame;

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
    }
}