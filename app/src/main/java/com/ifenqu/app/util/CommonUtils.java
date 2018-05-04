package com.ifenqu.app.util;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.ifenqu.app.app.IfenquApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CommonUtils {

    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);
    }

    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("dd");
        return dateFm.format(date);
    }


    public static Drawable getDrawable(int resid) {
        return getResoure().getDrawable(resid);
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }

    public static Resources getResoure() {
        return IfenquApplication.getInstance().getResources();
    }

    public static String[] getStringArray(int resid) {
        return getResoure().getStringArray(resid);
    }

    public static String getString(int resid) {
        return getResoure().getString(resid);
    }

    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

    public static void removeSelfFromParent(View child) {

        if (child != null) {

            ViewParent parent = child.getParent();

            if (parent instanceof ViewGroup) {

                ViewGroup group = (ViewGroup) parent;

                group.removeView(child);
            }
        }
    }

}
