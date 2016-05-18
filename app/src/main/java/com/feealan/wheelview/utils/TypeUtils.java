package com.feealan.wheelview.utils;

import android.content.Context;
import android.util.Log;

import com.feealan.wheelview.bean.TypeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动类型数据提供工具类
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class TypeUtils {
    private Context        mContext;
    private List<TypeBean> mList;

    /*存放第二个wheelview值*/
    private List<String> SList       = new ArrayList<>();
    private List<String> defaultList = new ArrayList<>();


    public TypeUtils(Context context) {
        this.mContext = context;
        mList = initData(mContext);
        defaultList.add("爱跑步");
        defaultList.add("马拉松");
        defaultList.add("健康徒步");
    }

    private List<TypeBean> initData(Context context) {
        InputStream  inputStream;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStream = context.getResources().getAssets().open("type.json");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bfr = new BufferedReader(isr);
            String in;
            while ((in = bfr.readLine()) != null) {
                stringBuffer.append(in);
            }
            inputStream.close();
            isr.close();
            bfr.close();
            return praseP(stringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TypeBean> praseP(String json) {
        Gson gson = new Gson();
        List<TypeBean> list = gson.fromJson(json, new TypeToken<List<TypeBean>>() {
        }.getType());
        List<TypeBean.Cbean> cbean = gson.fromJson(json, new TypeToken<List<TypeBean.Cbean>>() {
        }.getType());
        for (TypeBean bean : list) {
            Log.d("aa", bean.toString());
        }
        return list;
    }

    public List<String> createFrist() {
        /*存放第一个wheelview值*/
        List<String> fList = new ArrayList<>();
        for (TypeBean bean : mList) {
            fList.add(bean.getP());
        }
        Log.d("aa", fList.toString());
        return fList;

    }

    public List<String> createSecond() {
        return defaultList;
    }

    public List<String> createSecond(int index) {
        //防止之前添加到集合的值还存在于集合内
        if (mList != null) {
            SList.clear();
            for (int i = 0; i < mList.size(); i++) {
                if (index == i) {
                    for (TypeBean.Cbean b : mList.get(i).getC()) {
                        SList.add(b.getContent());
                    }
                }
            }
            Log.d("aa", SList.toString());
            return SList;
        } else {
            return defaultList;
        }
    }
}
