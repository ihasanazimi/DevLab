/*
Copyright 2015 Alex Florescu
Copyright 2014 Yahoo Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ir.ha.meproject.utility.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;

/**
 * Util class for converting between dp, px and other magical pixel units
 */
public class PixelUtil {

    private PixelUtil() {
    }

    public static int dpToPx(float dp , Context context) {
        return dpToPx(context, dp);
    }
    public static int dpToPx(Context context, int dp) {
        return dpToPx(context, (float)dp);
    }
    public static int dpToPx(Context context, float dp) {
        int px = Math.round(dp * getPixelScaleFactor(context));
        return px;
    }
    public static int spToPx(float sp,@NonNull Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static float pxToDp(float px ,Context context) {
        return px / getPixelScaleFactor(context);
    }

    private static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int getWidth(@NonNull Context context){
        return getWidth(context);
    }
    public static int getWidth_2(Context context){

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        return (int)dpWidth ;
    }
    public static int getWidthPx(Context context){
        return getWidthPx(context);
    }
    public static int getWidthPx_2(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getHeight(@NonNull  Context context){
        return getHeight(context);
    }
    public static int getHeight_2(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        return (int)dpHeight ;
    }
    public static int getHeightPx(@NonNull Context context){
        return getHeightPx(context);
    }
    public static int getHeightPx_2(@NonNull Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
