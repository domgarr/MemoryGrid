package com.domgarr.android.memorygrid;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by Domenic on 2017-05-12.
 */

//Outputs a Grid onto the screen given the grid class that's given in the constructor.


public class DrawGrid extends View {
    private static final String TAG = "DrawView";

    private Paint paint;
    private Grid mGrid;

    public DrawGrid(Context context, Grid grid){
        super(context);
        mGrid = grid;
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        //Iterate through row * col grid.
        for(int row = 0; row < mGrid.getNumberOfRows() ; row++)
            for(int col = 0; col < mGrid.getNumberOfColumns() ; col++) {
                //getTouchArray is an array of type boolean stored in the Grid class.

                //If the value at the index is true, The player has pressed inside that Cell,
                //highlight it red to indicate that.
                if (mGrid.getTouchArrayAtIndex(col, row)){
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.RED);
                }else{
                    //If false, user didn't activate this cell. Draw default rectangle.
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                }

                //https://developer.android.com/reference/android/graphics/Canvas.html
                //Draws coordinates in order: left, top, right, bottom
                canvas.drawRect(col * mGrid.getCellWidth(), row * mGrid.getCellHeight(),
                        (col * mGrid.getCellWidth()) + mGrid.getCellWidth(),
                        (row * mGrid.getCellHeight()) + mGrid.getCellHeight(),
                        paint);


                //Debug stuff.
                //TAG is class name. On Android Monitor in top right you can choose filters.
                //Make a nwe filter by opening the tab and choosing "Edit Filter Configuration"
                //Use the TAG name in the config, and command line will only show debugging
                //statements concerning this class. Very useful!
                Log.d(TAG,"left: " + col * mGrid.getCellWidth());
                Log.d(TAG, "top: " + row * mGrid.getCellHeight());
                Log.d(TAG, "right: " + ((col * mGrid.getCellWidth()) + mGrid.getCellWidth()));
                Log.d(TAG, "bottom: " + ((row * mGrid.getCellHeight()) + mGrid.getCellHeight()));


            }

    }

}
