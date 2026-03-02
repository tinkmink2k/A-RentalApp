package edu.re.estate.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.re.estate.R;

public class FloatingChatBotView extends FrameLayout {

    private float lastX, lastY;
    private float downX, downY;
    private boolean isDragging;

    private static final int CLICK_THRESHOLD = 10;
    private static final int SNAP_DURATION = 250;

    private OnBotClickListener onBotClickListener;

    public FloatingChatBotView(@NonNull Context context) {
        super(context);
        init();
    }

    public FloatingChatBotView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingChatBotView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.view_chatbot, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                lastX = getX();
                lastY = getY();
                isDragging = false;

                animate().scaleX(1.1f).scaleY(1.1f).setDuration(100).start();
                return true;

            case MotionEvent.ACTION_MOVE:
                float dx = event.getRawX() - downX;
                float dy = event.getRawY() - downY;

                if (Math.abs(dx) > CLICK_THRESHOLD || Math.abs(dy) > CLICK_THRESHOLD) {
                    isDragging = true;
                }

                setX(lastX + dx);
                setY(lastY + dy);
                return true;

            case MotionEvent.ACTION_UP:
                animate().scaleX(1f).scaleY(1f).setDuration(100).start();

                if (!isDragging) {
                    performClick();
                } else {
                    snapToEdge();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        if (onBotClickListener != null) {
            onBotClickListener.onClick();
        }
        return true;
    }

    private void snapToEdge() {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        float currentX = getX();
        float viewWidth = getWidth();

        float distanceToRight = screenWidth - (currentX + viewWidth);

        float targetX;

        if (currentX < distanceToRight) {
            targetX = 10;
        } else {
            targetX = screenWidth - viewWidth;
        }

        animate()
                .x(targetX)
                .setDuration(250)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public void setOnBotClickListener(OnBotClickListener listener) {
        this.onBotClickListener = listener;
    }

    public interface OnBotClickListener {
        void onClick();
    }
}
