package com.example.hippoweex.ui.widget.iconfont;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.example.core.manager.TypefaceManager;
import com.example.hippoweex.R;
public class PrintViewUtils {

    /**
     * Initialization of icon for print views.
     *
     * @param context    The Context the view is running in, through which it can access the current
     *                   theme, resources, etc.
     * @param attrs      The attributes of the XML tag that is inflating the view.
     * @param inEditMode Indicates whether this View is currently in edit mode.
     * @return The icon to display.
     */
    public static PrintDrawable initIcon(Context context, AttributeSet attrs, boolean inEditMode) {
        PrintDrawable.Builder iconBuilder = new PrintDrawable.Builder(context);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PrintView);

            if (a.hasValue(R.styleable.PrintView_iconText)) {
                String iconText = a.getString(R.styleable.PrintView_iconText);
                iconBuilder.iconText(iconText);
            }

            if (!inEditMode && a.hasValue(R.styleable.PrintView_iconFont)) {
                String iconFontPath = a.getString(R.styleable.PrintView_iconFont);
                iconBuilder.iconFont(TypefaceManager.load(context.getAssets(), iconFontPath));
            }

            if (a.hasValue(R.styleable.PrintView_iconColor)) {
                ColorStateList iconColor = a.getColorStateList(R.styleable.PrintView_iconColor);
                iconBuilder.iconColor(iconColor);
            }

            int iconSize = a.getDimensionPixelSize(R.styleable.PrintView_iconSize, 0);
            iconBuilder.iconSize(TypedValue.COMPLEX_UNIT_PX, iconSize);

            a.recycle();
        }

        return iconBuilder.build();
    }

    private PrintViewUtils() {
    }

}