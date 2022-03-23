package is.hi.hbv601g.timesrunningout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.*;

public class RoundActivity extends AppCompatActivity {

    SharedPreferences mSharedPref;
    Game mGame;
    private List<String> mWords = Arrays.asList("Butterfly", "cup", "tea", "tree");

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


    }
}