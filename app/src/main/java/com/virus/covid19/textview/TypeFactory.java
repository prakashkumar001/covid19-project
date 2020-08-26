package com.virus.covid19.textview;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFactory {

    private String A_BOLD= "Montserrat-Bold.otf";
    private String A_LIGHT="Montserrat-Light.otf";
    private String A_REGULAR= "Montserrat-Regular.otf";
    private String O_ITALIC= "Montserrat-Italic.otf";
    private String A_MEDIUM= "Montserrat-Medium.otf";

    Typeface montserrat_Bold;
    Typeface montserrat_Light;
    Typeface montserrat_Regular;
    Typeface montserrat_Italic;
    Typeface montserrat_Medium;


    public TypeFactory(Context context){
        montserrat_Bold = Typeface.createFromAsset(context.getAssets(),"fonts/"+A_BOLD);
        montserrat_Light = Typeface.createFromAsset(context.getAssets(),"fonts/"+A_LIGHT);
        montserrat_Regular = Typeface.createFromAsset(context.getAssets(),"fonts/"+A_REGULAR);
        montserrat_Italic = Typeface.createFromAsset(context.getAssets(),"fonts/"+O_ITALIC);
        montserrat_Medium = Typeface.createFromAsset(context.getAssets(),"fonts/"+A_MEDIUM);


    }

}
