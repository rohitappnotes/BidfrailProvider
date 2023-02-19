package com.library.utilities.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import static java.lang.Math.round;
import com.library.utilities.R;

public class AlphabetAvatar extends AppCompatImageView {

    private int DEFAULT_BACKGOUND_COLOR = Color.RED;
    private int DEFAULT_BORDER_COLOR = Color.LTGRAY;
    private float DEFAULT_BORDER_WIDTH = 5F;
    private int DEFAULT_TEXT_COLOR = Color.LTGRAY;

    private int backgroundColor;
    private int borderColor;
    private float borderWidth;
    private int textColor;
    private String name;

    private Paint backgroundPaint;
    private Paint borderPaint;
    private Paint textPaint;

    private int width;
    private int height;

    private RectF bounds;
    private RectF borderBounds;
    private Rect textBounds;

    public AlphabetAvatar(Context context) {
        super(context);

        init();
    }

    public AlphabetAvatar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public AlphabetAvatar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init() {
        backgroundColor = DEFAULT_BACKGOUND_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
        borderWidth = DEFAULT_BORDER_WIDTH;
        textColor = DEFAULT_TEXT_COLOR;

        bounds = new RectF();
        borderBounds = new RectF();
        textBounds = new Rect();

        backgroundPaint = new Paint();
        borderPaint = new Paint();
        textPaint = new Paint();
    }

    private void init(Context context, AttributeSet attrs) {
        init();

        if (attrs != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphabetAvatar);
            if (typedArray != null) {
                backgroundColor = typedArray.getColor(R.styleable.AlphabetAvatar_background_color, DEFAULT_BACKGOUND_COLOR);
                borderColor = typedArray.getColor(R.styleable.AlphabetAvatar_border_color, DEFAULT_BORDER_COLOR);
                borderWidth = typedArray.getDimension(R.styleable.AlphabetAvatar_border_width, DEFAULT_BORDER_WIDTH);
                textColor = typedArray.getColor(R.styleable.AlphabetAvatar_text_color, DEFAULT_TEXT_COLOR);
                name = typedArray.getNonResourceString(R.styleable.AlphabetAvatar_text);

                typedArray.recycle();
            }
        }
    }

    private void prepareBackgroundPaint() {
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(backgroundColor);
    }

    private void prepareStrokePaint() {
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
    }

    private void prepareTextPaint() {
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(50F);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(textColor);
    }

    private void drawUI() {
        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        prepareBackgroundPaint();
        prepareTextPaint();
        prepareStrokePaint();

        invalidate();
    }

    public void setBorderColor(int borderColor) {
        borderPaint.setColor(borderColor);
        this.borderColor = borderColor;

        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        borderPaint.setStrokeWidth(borderWidth);
        this.borderWidth = borderWidth;

        invalidate();
    }

    public void setBGColor(int backgroundColor) {
        backgroundPaint.setColor(backgroundColor);
        this.backgroundColor = backgroundColor;

        invalidate();
    }

    public void setTextColor(int textColor) {
        textPaint.setColor(textColor);
        this.textColor = textColor;

        invalidate();
    }

    public void setText(String name) {
        this.name = name;

        drawUI();
    }

    private String getInitial() {
        return name != null ? String.valueOf(name.charAt(0)) : "";
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        drawUI();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);

        drawUI();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);

        drawUI();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        this.height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    public void onDraw(Canvas canvas) {
        bounds.set(0F, 0F, width, height);
        canvas.drawOval(bounds, backgroundPaint);

        super.onDraw(canvas);

        int strokeWidth = (int) (borderPaint.getStrokeWidth() / 2);
        borderBounds.set(
                strokeWidth,
                strokeWidth,
                width - strokeWidth,
                height - strokeWidth
        );
        canvas.drawOval(borderBounds, borderPaint);

        String initial = getInitial();
        textPaint.getTextBounds(initial, 0, initial.length(), textBounds);

        int textBottom = round((bounds.height() / 2F) + (textBounds.height() / 2F));
        canvas.drawText(initial, width / 2F, textBottom, textPaint);
    }

}
