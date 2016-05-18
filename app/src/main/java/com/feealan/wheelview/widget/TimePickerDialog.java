package com.feealan.wheelview.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.feealan.wheelview.R;
import com.feealan.wheelview.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class TimePickerDialog extends BaseDialog {
    private TextView  cancel;
    private TextView  title;
    private TextView  ok;
    private WheelView wheel_y;
    private WheelView wheel_m;
    private WheelView wheel_d;
    private WheelView wheel_h;
    private WheelView wheel_mi;
    private int  year   = 0;
    private int  month  = 0;
    private int  day    = 0;
    private int  hour   = 0;
    private int  minter = 0;
    private long time   = 0;
    private OnClickCallback callback;

    public TimePickerDialog(Context context) {
        super(context);
        dialog.setContentView(R.layout.dialog_time_picker);
        cancel = (TextView) dialog.findViewById(R.id.cancel);
        title = (TextView) dialog.findViewById(R.id.title);
        ok = (TextView) dialog.findViewById(R.id.ok);
        title.setText("请选择时间");
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        wheel_y = (WheelView) dialog.findViewById(R.id.wheel_y);
        wheel_m = (WheelView) dialog.findViewById(R.id.wheel_m);
        wheel_d = (WheelView) dialog.findViewById(R.id.wheel_d);
        wheel_h = (WheelView) dialog.findViewById(R.id.wheel_h);
        wheel_mi = (WheelView) dialog.findViewById(R.id.wheel_mi);
        wheel_y.setData(TimeUtils.getItemList(TimeUtils.TYPE_YEAR));
        wheel_y.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                year = index + TimeUtils.MIN_YEAR;

            }
        });
        wheel_m.setData(TimeUtils.getItemList(TimeUtils.TYPE_MONTH));
        wheel_m.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                month = index + 1;
                wheel_d.setData(TimeUtils.createdDay(year, month));
            }
        });
        wheel_d.setData(TimeUtils.getItemList(TimeUtils.TYPE_DAY));
        wheel_h.setData(TimeUtils.getItemList(TimeUtils.TYPE_HOUR));
        wheel_mi.setData(TimeUtils.getItemList(TimeUtils.TYPE_MINUTE));

        Date date = new Date(System.currentTimeMillis());
        Log.d("aa", date.getYear() + ">>" + date.getMonth() + ">>" + date.getDate() + ">>" + date.getHours() + ">>" + date.getMinutes());
        wheel_y.setCurrentItem(date.getYear() - 70);
        wheel_m.setCurrentItem(date.getMonth());
        wheel_d.setCurrentItem(date.getDate() - 1);
        wheel_h.setCurrentItem(date.getHours());
        wheel_mi.setCurrentItem(date.getMinutes());

        setDialogLocation(mContext, dialog);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ok:
                if (callback != null) {
                    int year = wheel_y.getCurrentItem();
                    int month = wheel_m.getCurrentItem();
                    int day = wheel_d.getCurrentItem();
                    int daySize = wheel_d.getItemCount();
                    if (day > daySize) {
                        day = day - daySize;
                    }
                    int hour = wheel_h.getCurrentItem() - 12;
                    int min = wheel_mi.getCurrentItem();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year + 1900 + 70);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DATE, day + 1);
                    calendar.set(Calendar.HOUR, hour);
                    calendar.set(Calendar.MINUTE, min);

                    long setTime = calendar.getTimeInMillis();
                    callback.onSure(year, month, day, hour, minter, setTime);
                }
                break;
            case R.id.cancel:
                if (callback != null) {
                    callback.onCancel();
                }
                break;
        }
    }

    public void setCallback(OnClickCallback callback) {
        this.callback = callback;
    }

    public interface OnClickCallback {
        void onCancel();

        /**
         *
         * @param year 用于界面显示  下同
         * @param month
         * @param day
         * @param hour
         * @param minter
         * @param time 用于给服务器传值
         */
        void onSure(int year, int month, int day, int hour, int minter, long time);
    }
}
