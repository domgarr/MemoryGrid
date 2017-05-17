/* MemoryGrid is a memory game where the player has to remember the locations of highlighted cells
in a grid, and in empty grid produce the same layout.
I decided to make this game to practice what i've learned form Android Programming: The Big Nerd Ranch
Guide. The material covered is from Chapters 1-6. It's important to not only read and copy code from
textbooks, but to think of applications you can make with the knowledge you've amassed so far.
 */

//Package name uses a reverse DNS(Domain Name System) convention
//The domain name is reversed, and suffixes are added as further identifiers.
//The reason is to keep package names unique and distinguishable from other applications.
//In Android Studio, the company domain is reversed. So type it as is, Android Studio will reverse it.
package com.domgarr.android.memorygrid;

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
import android.widget.Toast;

import java.util.Arrays;

/*An activity is defined as a single, focused action a user can perform.
AppCombatActivty extends Activity. It allows compatibility for older versions of Android.
An important method every Activity implements is OnCreate(), it is the first method called
when the ActivityManager launches the activity.
See https://developer.android.com/reference/android/app/Activity.html
It is important to learn about an Activities lifecycle.
 */
public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    //Used in onActivityResult, depending on which Activity finishes
    //data is gathered and certain commands are executed.
    private static final int REQUEST_GENERATED_ANSWER_ARRAY = 0;
    private static final int REQUEST_USER_ANSWER_ARRAY = 1;

    private Button mPlayButton;
    private TextView mCorrectnessTextView;

    private boolean[] mAnswerArray;
    private boolean[] mUserAnswerArray;

    private boolean isCorrect = false;
    private boolean roundComplete = false;

    //Timing variables
    //http://stackoverflow.com/questions/15248891/how-to-measure-elapsed-time
    long tStart;
    long tEnd;
    long tDelta;



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
                    playRound();
                }
            });

        mCorrectnessTextView = (TextView) findViewById(R.id.correctness_textview);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (roundComplete && isCorrect) {
            playRound();
            //We started a new round. The round is not yet complete.
            roundComplete = false;
        }

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
        //GoalGridActivity will create and show a goal grid, which the user needs to replicate.
        //When the activity finishes, this code is executed.
        else if(requestCode == REQUEST_GENERATED_ANSWER_ARRAY) {
            if (data == null)
                return;
            //The data returned is the pattern of highlighted cells.
            //Need later for comparison.
            mAnswerArray = GoalGridActivity.getAnswerArray(data);

            //Start a new Activity, asking for a result of what user's answer
            Intent i = FillGridActivity.newIntent(GameActivity.this);
            startActivityForResult(i, REQUEST_USER_ANSWER_ARRAY);
            //Get starting time of round.
            tStart = System.currentTimeMillis();


        }else if(requestCode == REQUEST_USER_ANSWER_ARRAY){
            if (data == null)
                return;
            //Save user's answer
            mUserAnswerArray = FillGridActivity.getUserAnswerArray(data);

            //Compare GoalGrid with the User's Grid.
            checkCorrectness();
            roundComplete = true;
        }


    }

    //Compares the two instances of boolean arrays contains GoalGrid and UserGrid
    private void checkCorrectness() {
        //Important to use equals, since we want to check for identical information at each index.
        if (Arrays.equals(mUserAnswerArray, mAnswerArray)) {
            mCorrectnessTextView.setText(R.string.correct);
            isCorrect = true;

            //Calculate how much time it took to correctly enter the pattern.
            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000.0;
            Toast.makeText(this, "Time Elapsed: " + elapsedSeconds, Toast.LENGTH_SHORT ).show();
        } else{
            mCorrectnessTextView.setText(R.string.incorrect);
            isCorrect = false;
        }

    }

    //Executing this method will play one round of the game.
    //That is show user randomized grid, and allow user to recreate grid.
    private void playRound(){
        /* Intent - an Object used to communicate with the Android OS.
                    Intent's can be used to start activities.
                    We use newIntent method and pass our context, this is because the constructor
                    requires this package's context.
                    If the Activity wanted data, that implementation would be done in that Activity.
                    This is because we might not know the implementation of the activity were running.
                    Note: We can't just pass this at context, we're in an anonymous class.
                     */

        Intent i = GoalGridActivity.newIntent(GameActivity.this);
        //The RequestCode (2nd Parameter) is used to identify which Activity is handing us
        //a result.
        startActivityForResult(i, REQUEST_GENERATED_ANSWER_ARRAY);
    }
}
