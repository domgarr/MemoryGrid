package touchthebox.android.domantior.com.touchthebox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private static final int REQUEST_GENERATED_ANSWER_ARRAY = 0;
    private static final int REQUEST_USER_ANSWER_ARRAY = 1;

    private Button mPlayButton;
    private TextView mCorrectnessTextView;

    private boolean[] mAnswerArray;
    private boolean[] mUserAnswerArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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

            mPlayButton = (Button) findViewById(R.id.play_button);
            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = GoalGridActivity.newIntent(GameActivity.this);
                    startActivityForResult(i, REQUEST_GENERATED_ANSWER_ARRAY);
                }
            });

        mCorrectnessTextView = (TextView) findViewById(R.id.correctness_textview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(TAG, "Request Code: " + requestCode);
        Log.d(TAG, "ResultCode: " + resultCode);
        Log.d(TAG, "RESULT_OK: " + RESULT_OK);

        if(resultCode != RESULT_OK)
            return;
        else if(requestCode == REQUEST_GENERATED_ANSWER_ARRAY) {
            if (data == null)
                return;
            mAnswerArray = GoalGridActivity.getAnswerArray(data);
            Intent i = FillGridActivity.newIntent(GameActivity.this);
            startActivityForResult(i, REQUEST_USER_ANSWER_ARRAY);
        }else if(requestCode == REQUEST_USER_ANSWER_ARRAY){
            Log.d(TAG, "Just before data check");
            if (data == null)
                return;

            Log.d(TAG, "In Activity Result");
            mUserAnswerArray = FillGridActivity.getUserAnswerArray(data);

            checkCorrectness();
        }


    }

    private void checkCorrectness(){
        Log.d(TAG, "ARE YOU HERE?");
        if(Arrays.equals(mUserAnswerArray, mAnswerArray))
            mCorrectnessTextView.setText(R.string.correct);
         else
            mCorrectnessTextView.setText(R.string.incorrect);
    }
}
