package com.domgarr.android.memorygrid;

import android.util.Log;

import java.util.Random;

/**
 * Created by Domenic on 2017-05-12.
 */

//Grid class: contains a grid of size row * col and adjusts the width and height of each cell
    //according to the ScreenDimensions of your phone(check ScreenDimensions class) such that each cell
    //is equal in width and height.

public class Grid {
    private static final String TAG = "Grid";

    private boolean[] mTouchArray; //Keeps track of Cells activated by user.

    private int mNumberOfRows;
    private int mNumberOfColumns;
    private int mCellWidth;
    private int mCellHeight;


    public Grid(int numberOfRows, int numberOfColumns){
        ScreenDimensions screenDimensions = new ScreenDimensions();

        mNumberOfRows = numberOfRows;
        mNumberOfColumns = numberOfColumns;

        mTouchArray = new boolean[numberOfRows * numberOfColumns];

        //Rename to cell widht and height?
        mCellWidth = screenDimensions.getWidth() / numberOfRows;
        mCellHeight = screenDimensions.getHeight() / numberOfColumns;

        Log.d(TAG, "Col Width: " + mCellHeight);
        Log.d(TAG, "Row Width: " + mCellWidth);
    }



    public int getNumberOfRows() {
        return mNumberOfRows;
    }

    public int getNumberOfColumns() {
        return mNumberOfColumns;
    }

    public int getCellHeight() {
        return mCellHeight;
    }

    public int getCellWidth() {
        return mCellWidth;
    }

    public boolean[] getTouchArray(){
        return mTouchArray;
    }

    public boolean getTouchArrayAtIndex(int x, int y) {
        return mTouchArray[y * getNumberOfRows() + x];
    }

    public void setTouchArrayAtIndexTrue(int x, int y) {
        mTouchArray[y * getNumberOfRows() + x] = !mTouchArray[y * getNumberOfRows() + x];
    }

    //Randomly generate the a Grid with activated cells of quantity n
    public void addBoxesAtRandom(int n){
        Random gen = new Random();
        for(int i = 0; i < n; i++)
        {
            int randomNumber = gen.nextInt(mTouchArray.length);
            //If the same random cell is chosen (which is already activated) choose another.
            //Do this by decrementing the loop.
            if(mTouchArray[randomNumber] == true)
                i--;
            else
                mTouchArray[randomNumber] = true;


        }
    }
}
