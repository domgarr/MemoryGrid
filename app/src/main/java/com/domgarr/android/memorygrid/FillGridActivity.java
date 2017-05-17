package com.domgarr.android.memorygrid;

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

    private DrawGrid mDrawView;
    private Grid mBox;

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

        mBox = new Grid(5, 5);

        mDrawView = new DrawGrid(this, mBox);
        mDrawView.setBackgroundColor(Color.WHITE);
        setContentView(mDrawView);

        mDrawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                int pointerCount = event.getPointerCount();
                int pointerIndex = event.getActionIndex();
                int pointerId = event.getPointerId(pointerIndex);

                //Cleaned up using a switch statement
                //Now handles multiple touches at once.
                //http://www.vogella.com/tutorials/AndroidTouch/article.html
                switch(action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int x = (int) event.getX(pointerId);
                        final int y = (int) event.getY(pointerId);

                        Thread t = new Thread() {
                            public void run() {
                                whichBoxTouched(x, y);
                            }
                        };
                        t.start();
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_DOWN: {
                       //TODO: Spamming more then one finger presses can cause a pointerIndex Exception.
                        if(pointerCount < 2)
                            break;

                        final int x = (int) event.getX(pointerId);
                        final int y = (int) event.getY(pointerId);

                        Thread t = new Thread() {
                            public void run() {
                                whichBoxTouched(x, y);
                            }
                        };
                        t.start();
                        break;
                        }
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
            if ((col * mBox.getCellWidth()) + mBox.getCellWidth() > x) {
                x = col;
                break;
            }
        }

        for (int row = 0; row < mBox.getNumberOfRows(); row++) {
            if ((row * mBox.getCellHeight()) + mBox.getCellHeight() > y) {
                y = row;
                break;
            }
        }
        Log.d(TAG, String.valueOf(x));
        Log.d(TAG, String.valueOf(y));

        mBox.setTouchArrayAtIndexTrue(x,y);

        Log.d(TAG, "END");
    }

    private void setUserAnswerArrayResult(boolean[] userAnswerArray){
        Intent data = new Intent();
        data.putExtra(EXTRA_USER_ANSWER_ARRAY, userAnswerArray);
        setResult(RESULT_OK, data);

    }

}
