package com.android.news.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.android.news.R;


/**
 * Created by Diiyo on 12/21/15.
 */
public class FontTextView extends android.support.v7.widget.AppCompatTextView {

    public FontTextView(Context context) {
        super(context);
        init(null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if (attrs != null){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontTextView);
            String fontName = a.getString(R.styleable.FontTextView_fontName);

            if (fontName != null){
                Typeface fontFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(fontFace);
            }

            a.recycle();
        }

    }

    public void setFontStyle(String fontStyle){

        if (fontStyle != null){
            Typeface fontFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontStyle);
            setTypeface(fontFace);
        }
    }
}
