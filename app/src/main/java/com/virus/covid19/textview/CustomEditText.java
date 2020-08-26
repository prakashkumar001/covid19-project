package com.virus.covid19.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.virus.covid19.R;


public class CustomEditText extends AppCompatEditText {

    private int typefaceType;
    private TypeFactory mFontFactory;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    public CustomEditText(Context context) {
        super(context);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {


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

            default:
                return mFontFactory.montserrat_Regular;
        }
    }

    public interface Constants {
        int A_BOLD = 1,
                A_LIGHT = 2,
                A_REGULAR = 3,
                O_ITALIC = 4;
    }

}