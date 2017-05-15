package touchthebox.android.domantior.com.touchthebox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class FillGridActivity extends AppCompatActivity {
    private static final String TAG = "FillGridActivity";
    private static final String EXTRA_USER_ANSWER_ARRAY =
            "com.domantior.android.touchthebox.user_answer_array";

    private DrawView mDrawView;
    private Box mBox;

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, FillGridActivity.class);
        return i;
    }

    public static boolean[] getUserAnswerArray(Intent data){
        return data.getBooleanArrayExtra(EXTRA_USER_ANSWER_ARRAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
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

        mBox = new Box(5, 5);


        mDrawView = new DrawView(this, mBox);
        mDrawView.setBackgroundColor(Color.WHITE);
        setContentView(mDrawView);

        mDrawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                if (action == MotionEvent.ACTION_DOWN) {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();


                    Thread t = new Thread() {
                        public void run() {
                            whichBoxTouched(x, y);
                        }
                    };
                    t.start();
                }
                mDrawView.invalidate();
                return true;
            }
        });
    }

   @Override
   public void onBackPressed()
   {
       setUserAnswerArrayResult(mBox.getTouchArray());
       finish();
   }


    private void whichBoxTouched(int x, int y){


        for (int col = 0; col < mBox.getNumberOfColumns(); col++) {
            if ((col * mBox.getColWidth()) + mBox.getColWidth() > x) {
                x = col;
                break;
            }
        }

        for (int row = 0; row < mBox.getNumberOfRows(); row++) {
            if ((row * mBox.getRowWidth()) + mBox.getRowWidth() > y) {
                y = row;
                break;
            }
        }
        Log.d(TAG, String.valueOf(x));
        Log.d(TAG, String.valueOf(y));

        mBox.setTouchArrayIndexTrue(x,y);

        Log.d(TAG, "END");
    }

    private void setUserAnswerArrayResult(boolean[] userAnswerArray){
        Intent data = new Intent();
        data.putExtra(EXTRA_USER_ANSWER_ARRAY, userAnswerArray);
        setResult(RESULT_OK, data);

    }

}
