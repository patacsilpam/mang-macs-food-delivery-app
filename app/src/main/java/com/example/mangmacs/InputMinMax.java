package com.example.mangmacs;

import android.text.InputFilter;
import android.text.Spanned;

public class InputMinMax implements InputFilter {
    private int mIntMin,mIntMax;
    public InputMinMax(int minVal, int maxValue){
        this.mIntMin = minVal;
        this.mIntMax = maxValue;
    }
    public InputMinMax(String minVal,String maxVal){
        this.mIntMin = Integer.parseInt(minVal);
        this.mIntMax = Integer.parseInt(maxVal);
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int dstart, int dend) {
        try {
            int input = Integer.parseInt(source.toString() + spanned.toString());
            if(isInRange(mIntMin,mIntMax,input)){
                return null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(int mIntMin, int mIntMax, int input) {
        return mIntMax > mIntMin ? input >= mIntMin && input <= mIntMax : input >= mIntMax && input <= mIntMin ;
    }
}
