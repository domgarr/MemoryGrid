package touchthebox.android.domantior.com.touchthebox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by Domenic on 2017-05-12.
 */

public class DrawView extends View {
    private static final String TAG = "DrawView";

    Paint paint = new Paint();
    Box mBox ;

    public DrawView(Context context, Box box){
        super(context);
        mBox = box;

    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);


        for(int row = 0; row < mBox.getNumberOfRows() ; row++)
            for(int col = 0; col < mBox.getNumberOfColumns() ; col++) {
                if (mBox.getTouchArrayIndex(col, row)){
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.RED);
                }else{
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                }
                canvas.drawRect(col * mBox.getColWidth(), row * mBox.getRowWidth(),
                        (col * mBox.getColWidth()) + mBox.getColWidth(),
                        (row * mBox.getRowWidth()) + mBox.getRowWidth(),
                        paint);

                Log.d(TAG,"left: " + col * mBox.getColWidth());
                Log.d(TAG, "top: " + row * mBox.getRowWidth());
                Log.d(TAG, "right: " + ((col * mBox.getColWidth()) + mBox.getColWidth()));
                Log.d(TAG, "bottom: " + ((row * mBox.getRowWidth()) + mBox.getRowWidth()));


            }

    }

}
