package com.feealan.wheelview.utils;

import android.content.Context;
import android.util.Log;

import com.feealan.wheelview.bean.CityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 城市数据提供公工具类
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class CityUtils {

    private Context        mContext;
    private List<CityBean> mList;
    /**
     * 默认的城市
     */
    private List<String> defCity  = new ArrayList<>();
    /**
     * 城市集合
     */
    private List<String> cityList = new ArrayList<>();
    /**
     * 默认的县 区
     */
    private List<String> defX     = new ArrayList<>();
    /**
     * 县 区集合
     */
    private List<String> xList    = new ArrayList<>();

    public CityUtils(Context mContext) {
        this.mContext = mContext;
        String json = init(this.mContext);
        mList = parseProvince(json);
        defCity.add("东城区");
        defCity.add("西城区");
        defCity.add("朝阳区");
        defX.add(" ");
        defX.add(" ");
        defX.add(" ");
    }

    /**
     * 从assets中获取数据
     *
     * @param context
     * @return
     */
    private String init(Context context) {
        InputStream  inputStream;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStream = context.getResources().getAssets().open("city.json");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bfr = new BufferedReader(isr);
            String in;
            while ((in = bfr.readLine()) != null) {
                stringBuffer.append(in);
            }
            inputStream.close();
            isr.close();
            bfr.close();
            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<CityBean> parseProvince(String json) {
        if (!json.equals("") && !"".equals(json)) {
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<List<CityBean>>() {
            }.getType());
        } else {
            return null;
        }
    }

    /**
     * 创建省份数据
     */
    public List<String> createProvince() {
        List<String> list = new ArrayList<>();
        for (CityBean bean : mList) {
            list.add(bean.getName());
        }
//        Log.d("aa", list.toString());
        return list;
    }

    public List<String> createCity() {
        return defCity;
    }

    /**
     * 根据省份编号获取城市
     *
     * @param pIndex
     * @return
     */
    public List<String> createCity(int pIndex) {
        cityList.clear();
        for (int i = 0; i < mList.size(); i++) {
            if (i == pIndex) {
                for (CityBean bean : mList.get(i).getCities()) {
                    cityList.add(bean.getName());
                }
            }
        }
        return cityList;
    }

    /**
     * 根据省份和城市编号获取县 或者区
     *
     * @param pIndex
     * @param cIndex
     * @return
     */
    public List<String> createdX(int pIndex, int cIndex) {

        List<CityBean> city_list = mList.get(pIndex).getCities();
        Log.d("aa", "pIndex>>>" + pIndex + "cIndex>>" + cIndex + "city_list>>>>" + city_list.size() + ">>>" + city_list.toString());
        for (int i = 0; i < city_list.size(); i++) {
            if (i == cIndex && city_list.get(i).getCities().size() != 0) {
                xList.clear();
                for (int j = 0; j < city_list.get(i).getCities().size(); j++) {
                    xList.add(city_list.get(i).getCities().get(j).getName());
                }
                Log.d("aa", "xianqu" + xList.toString());
                return xList;
            }
        }
        return defX;
    }

    public List<String> createdX() {
        return defX;
    }
}
