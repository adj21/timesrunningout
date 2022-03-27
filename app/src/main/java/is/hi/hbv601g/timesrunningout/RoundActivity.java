package is.hi.hbv601g.timesrunningout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.*;

public class RoundActivity extends AppCompatActivity {

    private SharedPreferences mSharedPref;
    private Game mGame;
    private List<String> mWords = Arrays.asList("Butterfly", "cup", "tea", "tree");
    private Button mStartRoundButton;//TODO TURN NOT ROUND


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        Context context = RoundActivity.this;
        mSharedPref = context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        Game mGame = new Game(mWords);

        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mGame); //change the Game object into a String
        prefsEditor.putString("Game", json); //put the String into the shared preferences
        prefsEditor.commit();

        mStartRoundButton = (Button) findViewById(R.id.StartRound_button);
        mStartRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent i = new Intent(RoundActivity.this, TurnActivity.class);TODO
                //i.putExtra("is.hi.hbv601g.geoquiz.answer_is_true", answerIsTrue);
               // startActivity(i);TODO
                // Toast.makeText(MainActivity.this, R.string.toast_intro, Toast.LENGTH_LONG).show();
            }
        });
    }
}