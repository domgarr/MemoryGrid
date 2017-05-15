package touchthebox.android.domantior.com.touchthebox;

import android.os.Debug;
import android.util.Log;

import java.util.Random;

/**
 * Created by Domenic on 2017-05-12.
 */

public class Box {
    private static final String TAG = "Box";

    private boolean[] mTouchArray;

    private int mNumberOfRows;
    private int mNumberOfColumns;
    private int mRowWidth;
    private int mColWidth;


    public Box (int numberOfRows, int numberOfColumns){
        ScreenDimensions screenDimensions = new ScreenDimensions();

        mNumberOfRows = numberOfRows;
        mNumberOfColumns = numberOfColumns;

        mTouchArray = new boolean[numberOfRows * numberOfColumns];

        //Rename to cell widht and height?
        mColWidth = screenDimensions.getWidth() / numberOfRows;
        mRowWidth = screenDimensions.getHeight() / numberOfColumns;

        Log.d(TAG, "Col Width: " + mRowWidth);
        Log.d(TAG, "Row Width: " + mColWidth);
    }



    public int getNumberOfRows() {
        return mNumberOfRows;
    }

    public int getNumberOfColumns() {
        return mNumberOfColumns;
    }

    public int getRowWidth() {
        return mRowWidth;
    }

    public int getColWidth() {
        return mColWidth;
    }

    public boolean[] getTouchArray(){
        return mTouchArray;
    }

    public boolean getTouchArrayIndex(int x, int y) {
        return mTouchArray[y * getNumberOfRows() + x];
    }

    public void setTouchArrayIndexTrue(int x, int y) {
        mTouchArray[y * getNumberOfRows() + x] = !mTouchArray[y * getNumberOfRows() + x];
    }

    public void addBoxesAtRandom(int number){
        Random gen = new Random();
        for(int i = 0; i < number; i++)
        {
            int randomNumber = gen.nextInt(mTouchArray.length);

            if(mTouchArray[randomNumber] != true)
                mTouchArray[randomNumber] = true;
            else
                i--;
        }
    }
}
