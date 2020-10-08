package com.virus.covid19.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.virus.covid19.R;


public class CustomTextView extends AppCompatTextView {

    public int typefaceType;
    public TypeFactory mFontFactory;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    public CustomTextView(Context context) {
        super(context);
    }

    public void applyCustomFont(Context context, AttributeSet attrs) {


        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView,
                0, 0);
        try {
            typefaceType = array.getInteger(R.styleable.CustomTextView_font_name, 0);
        } finally {
            array.recycle();
        }
        if (!isInEditMode()) {
            setTypeface(getTypeFace(typefaceType));
        }

    }

    public Typeface getTypeFace(int type) {
        if (mFontFactory == null)
            mFontFactory = new TypeFactory(getContext());

        switch (type) {
            case Constants.A_BOLD:
                return mFontFactory.montserrat_Bold;

            case Constants.A_LIGHT:
                return mFontFactory.montserrat_Light;

            case Constants.A_REGULAR:
                return mFontFactory.montserrat_Regular;

            case Constants.O_ITALIC:
                return mFontFactory.montserrat_Light;
            case Constants.A_MEDUIM:
                return mFontFactory.montserrat_Bold;
            default:
                return mFontFactory.montserrat_Regular;
        }
    }

    public interface Constants {
        int A_BOLD = 1,
                A_LIGHT = 2,
                A_REGULAR = 3,
                A_MEDUIM = 4,
        O_ITALIC=5;
    }


}