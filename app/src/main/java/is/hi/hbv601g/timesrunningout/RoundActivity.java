package is.hi.hbv601g.timesrunningout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class RoundActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    Game game;
    private List<String> mWords = new Array("butterfly","tea","cup","flower")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        Context context = RoundActivity.this
        sharedPref = context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        game = new Game()
    }
}