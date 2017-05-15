package touchthebox.android.domantior.com.touchthebox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GoalGridActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_ARRAY =
            "com.domnantior.android.touchthebox.answer_array";

    private Box mGoalBox;
    private DrawView mDrawView;


    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, GoalGridActivity.class);
        return i;
    }

    public static boolean[] getAnswerArray(Intent result){
        return result.getBooleanArrayExtra(EXTRA_ANSWER_ARRAY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mGoalBox = new Box(5,5);
        mGoalBox.addBoxesAtRandom(5);

        mDrawView = new DrawView(this, mGoalBox);
        mDrawView.setBackgroundColor(Color.WHITE);
        setContentView(mDrawView);

        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                setAnswerArrayResult(mGoalBox.getTouchArray());
                finish();
            }
        }.start();
    }

    private void  setAnswerArrayResult(boolean[] answerArray){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_ARRAY, answerArray);
        setResult(RESULT_OK, data);
    }

}
