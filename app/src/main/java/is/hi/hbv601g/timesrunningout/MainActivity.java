package is.hi.hbv601g.timesrunningout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SharedPreferences mSharedPref;
    private Game mGame;
    private List<String> mWords = Arrays.asList("Butterfly", "cup", "tea", "tree");
    private Button mStartRoundButton;
    private Button mPlayButton;
    //TODO: Button for custom game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Context context = MainActivity.this;
               mSharedPref = context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
               Game mGame = new Game(mWords);

               SharedPreferences.Editor prefsEditor = mSharedPref.edit();
               Gson gson = new Gson();
               String json = gson.toJson(mGame); //change the Game object into a String
               prefsEditor.putString("Game", json); //put the String into the shared preferences
               prefsEditor.commit();

               Intent i = new Intent(MainActivity.this, RoundActivity.class);
               //i.putExtra("is.hi.hbv601g.geoquiz.answer_is_true", answerIsTrue);
               startActivity(i);
               // Toast.makeText(MainActivity.this, R.string.toast_intro, Toast.LENGTH_LONG).show();
            }
        });
        //TODO: program button for custom game
    }
}