package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.TurnActivity;

public class RoundActivity extends AppCompatActivity {

    private SharedPreferences mSharedPref;
    private Game mGame;

    private Button mStartTurnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

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
}