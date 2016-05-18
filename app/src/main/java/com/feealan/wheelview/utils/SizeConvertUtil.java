package com.feealan.wheelview.utils;

import android.content.Context;

/**
 * 尺寸转换工具
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class SizeConvertUtil {

    private SizeConvertUtil() {
    }

    public static int dpTopx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int pxTodp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int pxTosp(Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    public static int spTopx(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}
