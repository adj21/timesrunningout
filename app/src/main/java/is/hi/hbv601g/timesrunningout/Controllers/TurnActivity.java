package is.hi.hbv601g.timesrunningout.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import is.hi.hbv601g.timesrunningout.R;
import is.hi.hbv601g.timesrunningout.Services.WordService;

public class TurnActivity extends AppCompatActivity {

    private Button mSkipButton;
    private Button mValidateButton;
    private TextView mWord;
    private CountDownTimer mTime;
    private long mTimeLeft = 30000; // 30 seconds
    private TextView mTimerLeft;
    private TextView mTimerText;
    private boolean mTimerRunning;
    private Button mNextButton;
    private WordService mWordService;
    private TextView countdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn);

        countdownText = findViewById(R.id.mTime);
        mValidateButton = findViewById(R.id.countdown_button);
        mSkipButton = findViewById(R.id.countdown_button2);

        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: implement validate button
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: implement skip button
            }
        });


    }

}