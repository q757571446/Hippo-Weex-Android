package com.example.hippoweex.ui.widget.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.hippoweex.R;


/**
 * Created by dell on 2016/8/17.
 */
public class UnderLineEditText extends EditText{

    private Paint paint;
    private Rect rect = new Rect();
    public final static int DEFAULT_LINE_WIDTH = 2;
    public final static int DEFAULT_LINE_COLOR = Color.parseColor("#3d3d3d");
    int lineWidth;
    int lineColor;


    public UnderLineEditText(Context context) {
        this(context, null);
    }

    public UnderLineEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UnderLineEditText);
        lineColor = ta.getColor(R.styleable.UnderLineEditText_line_color, DEFAULT_LINE_COLOR);
        lineWidth = ta.getDimensionPixelSize(R.styleable.UnderLineEditText_line_width, DEFAULT_LINE_WIDTH);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    public boolean onPreDraw() {
        if(rect.isEmpty()){
            getLocalVisibleRect(rect);
        }
        return super.onPreDraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(rect.left, rect.bottom - lineWidth, rect.right, rect.bottom -lineWidth, paint);
    }
}
