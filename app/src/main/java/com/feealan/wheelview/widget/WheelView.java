package com.feealan.wheelview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.feealan.wheelview.R;
import com.feealan.wheelview.utils.SizeConvertUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义WheelView
 */
public class WheelView extends View {
    public static final String TAG = "WheelView";
    private Context      mContext;
    /*要显示的内容*/
    private List<String> itemLsit;
    /*选中的画笔*/
    private Paint        selectPaint;
    /*未选中的画笔*/
    private Paint        unSelectPaint;
    /*画背景图中单独的画笔*/
    private Paint        centerLinePaint;
    /*item的高度*/
    private             int itemHeight = 50;
    /*除选中item外，上下各需要显示的备选项数目*/
    public static final int SHOW_SIZE  = 1;
    private float centerY;
    private float centerX;
    /* 滑动的距离*/
    private float   mMoveLen = 0;
    private boolean isInit   = false;
    /* 选中的位置，这个位置是mDataList的中心位置，一直不变*/
    private int currentItem;
    private int itemCount;
    /* 自动回滚到中间的速度*/
    public static final float SPEED = 2;
    private SelectListener mSelectListener;
    private Timer          timer;
    private MyTimerTask    mTask;
    private float mLastDownY;
    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(mMoveLen) < SPEED) {
                // 如果偏移量少于最少偏移量
                mMoveLen = 0;
                if (null != timer) {
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
                if (mTask != null) {
                    mTask.cancel();
                    mTask = null;
                    performSelect();
                }
            } else {
                // 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
                mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
            }
            invalidate();
        }

    };
    private void resetCurrentSelect() {
        if (currentItem < 0) {
            currentItem = 0;
        }
        while (currentItem >= itemCount) {
            currentItem--;
        }
        if (currentItem >= 0 && currentItem < itemCount) {
            invalidate();
        } else {
            Log.i(TAG, "current item is invalid");
        }
    }
    public int getItemCount() {
        return itemCount;
    }
    public int getCurrentItem() {

        return currentItem;
    }

    public void setCurrentItem(int selected) {
        currentItem = selected;
        resetCurrentSelect();
    }

    public void setOnSelectListener(SelectListener listener) {
        mSelectListener = listener;
    }
    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(mContext);
    }

    /**
     * 初始化数据
     * @param list
     */
    public void setData(List<String> list) {
        itemLsit=list;
        if (itemLsit != null) {
            itemCount = itemLsit.size();
            resetCurrentSelect();
            invalidate();
        } else {
            Log.i(TAG, "item is null");
        }

    }

    /**
     * 做二级联动是设置二级wheelview的数据
     * @param list
     */
    public void setWheelItemList(List<String> list) {
        this.itemLsit = list;
        if (itemLsit != null) {
            itemCount = itemLsit.size();
            resetCurrentSelect();
        } else {
            Log.i(TAG, "item is null");
        }
    }
    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        //初始化选中画笔 设置位图抗锯齿
        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔 填充
        selectPaint.setStyle(Paint.Style.FILL);
        //文字显示位置 居中
        selectPaint.setTextAlign(Paint.Align.CENTER);
        //设置画笔颜色
        selectPaint.setColor(ContextCompat.getColor(mContext, android.R.color.white));
        //设置文字大小
        selectPaint.setTextSize(SizeConvertUtil.spTopx(mContext, 18));

        //初始化未选中画笔 设置位图抗锯齿
        unSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔 填充
        unSelectPaint.setStyle(Paint.Style.FILL);
        //文字显示位置 居中
        unSelectPaint.setTextAlign(Paint.Align.CENTER);
        //设置画笔颜色
        unSelectPaint.setColor(ContextCompat.getColor(mContext, R.color.wheel_unselect_text));
        //设置文字大小
        unSelectPaint.setTextSize(SizeConvertUtil.spTopx(mContext, 16));

        //初始化选中行 设置位图抗锯齿
        centerLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔 填充
        centerLinePaint.setStyle(Paint.Style.FILL);
        //文字显示位置 居中
        centerLinePaint.setTextAlign(Paint.Align.CENTER);
        //设置画笔颜色
        centerLinePaint.setColor(ContextCompat.getColor(mContext, R.color.wheel_select_text_bg));

        setBackground(null);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //view的宽高
        int mViewHeight = getMeasuredHeight();
        int mViewWidth = getMeasuredWidth();

        centerY = (float) (mViewHeight / 2.0);
        centerX = (float) (mViewWidth / 2.0);

        isInit = true;
        //对View重绘
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInit) {
            drawData(canvas);
        }
    }

    private void drawData(Canvas canvas) {
        if (!itemLsit.isEmpty()) {
            // 绘制中间data
            drawCenterText(canvas);
            // 绘制上方data
            for (int i = 1; i < SHOW_SIZE + 1; i++) {
                drawOtherText(canvas, i, -1);
            }
            // 绘制下方data
            for (int i = 1; i < SHOW_SIZE + 1; i++) {
                drawOtherText(canvas, i, 1);
            }
        }
    }
    /**
     * @param canvas   画布
     * @param position 距离mCurrentSelected的差值
     * @param type     1表示向下绘制，-1表示向上绘制
     */
    private void drawOtherText(Canvas canvas, int position, int type) {
        int index = currentItem + type * position;
        if (index >= itemCount) {
            index = index - itemCount;
        }
        if (index < 0) {
            index = index + itemCount;
        }
        String text = itemLsit.get(index);

        int itemHeight = getHeight() / (SHOW_SIZE * 2 + 1);
        float d = itemHeight * position + type * mMoveLen;
        float y = centerY + type * d;

        Paint.FontMetricsInt fmi = unSelectPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(text, centerX, baseline, unSelectPaint);
    }

    /**
     *
     * 绘制选中的文字
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        float y = centerY + mMoveLen;
        Paint.FontMetricsInt fmi = selectPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(itemLsit.get(currentItem), centerX, baseline, selectPaint);
    }


    @Override
    public void setBackground(Drawable background) {
        background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                //计算出每个Item的高度
                itemHeight = getHeight()/(SHOW_SIZE*2+1);
                //显示的宽度
                int width = getWidth();

                Rect centerRect = new Rect(0,itemHeight,width,itemHeight*2);
                canvas.drawRect(centerRect,centerLinePaint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        super.setBackground(background);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event);
                break;
            case MotionEvent.ACTION_UP:
                doUp();
                break;
            default:
                break;
        }
        return true;
    }
    private void doDown(MotionEvent event) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mLastDownY = event.getY();
    }
    private void doMove(MotionEvent event) {

        mMoveLen += (event.getY() - mLastDownY);

        if (mMoveLen > itemHeight / 2) {
            // 往下滑超过离开距离
            mMoveLen = mMoveLen - itemHeight;
            currentItem--;
            if (currentItem < 0) {
                currentItem = itemCount - 1;
            }
        } else if (mMoveLen < -itemHeight / 2) {
            // 往上滑超过离开距离
            mMoveLen = mMoveLen + itemHeight;
            currentItem++;
            if (currentItem >= itemCount) {
                currentItem = 0;
            }
        }

        mLastDownY = event.getY();
        invalidate();
    }
    private void doUp() {
        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
        if (Math.abs(mMoveLen) < 0.0001) {
            mMoveLen = 0;
            return;
        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (null == timer) {
            timer = new Timer();
        }
        mTask = new MyTimerTask(updateHandler);
        timer.schedule(mTask, 0, 10);
    }
    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    private void performSelect() {
        if (mSelectListener != null) {
            mSelectListener.onSelect(currentItem, itemLsit.get(currentItem));
        } else {
            Log.i(TAG, "null listener");
        }
    }

    public interface SelectListener {
        void onSelect(int index, String text);
    }
}
