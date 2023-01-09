package com.example.preferencetest2;

import android.app.Application;

public class Common extends Application {
// sample    private boolean IsOptionEnable = false;
    private MainActivity mainActivity = null;

    public WebApiHeatmap webApiHeatmap;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    // MainActivity context
    public void setMainActivity( MainActivity context) {
        mainActivity = context;
        webApiHeatmap = new WebApiHeatmap(context);
    }
    MainActivity getMainActivity() { return mainActivity; }

    /* sample
    boolean getIsOptionEnable() { return IsOptionEnable; }
    void setIsOptionEnable(boolean value){ IsOptionEnable = value; }
    */
}
