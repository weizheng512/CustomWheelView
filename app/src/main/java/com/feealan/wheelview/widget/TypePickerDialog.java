package com.feealan.wheelview.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.feealan.wheelview.R;
import com.feealan.wheelview.utils.TypeUtils;

/**
 * 运动类型选择器
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class TypePickerDialog extends BaseDialog {

    private TextView        cancel;
    private TextView        title;
    private TextView        ok;
    private WheelView       wheel_p;
    private WheelView       wheel_c;
    private String          data;
    private OnClickCallback callback;
    private String          fristType;
    private String          sencondType;
    private int             f;
    private int             s;

    public TypePickerDialog(Context context) {
        super(context);
        final TypeUtils typeUtils = new TypeUtils(mContext);
        dialog.setContentView(R.layout.dialog_sp_type_picker);
        cancel = (TextView) dialog.findViewById(R.id.cancel);
        title = (TextView) dialog.findViewById(R.id.title);
        ok = (TextView) dialog.findViewById(R.id.ok);
        title.setText("选择活动类型");
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        fristType = typeUtils.createFrist().get(f);
        sencondType = typeUtils.createSecond(f).get(s);

        wheel_p = (WheelView) dialog.findViewById(R.id.wheel_p);
        wheel_c = (WheelView) dialog.findViewById(R.id.wheel_c);
        wheel_p.setData(typeUtils.createFrist());
        wheel_c.setData(typeUtils.createSecond());
        wheel_p.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                fristType = text;
                f = index;
                wheel_c.setWheelItemList(typeUtils.createSecond(index));
                sencondType = typeUtils.createSecond(f).get(s);
            }
        });
        wheel_c.setOnSelectListener(new WheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                sencondType = text;
                s = index;
            }
        });
        setDialogLocation(mContext, dialog);

    }

    public void setCallback(OnClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cancel:
                if (callback != null) {
                    callback.onCancel();
                }
                break;
            case R.id.ok:
                if (callback != null) {
                    callback.onSure(f, s, fristType, sencondType);
                }
                break;
        }
    }

    public interface OnClickCallback {
        void onCancel();

        /**
         *前面连个int 值是用于给服务器传值  后面两个String 用于界面显示
         * @param f
         * @param s
         * @param fristType
         * @param sencondType
         */
        void onSure(int f, int s, String fristType, String sencondType);
    }
}
