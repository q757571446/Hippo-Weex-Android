package com.example.hippoweex.ui.widget.iconfont;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * View for displaying icons from iconic fonts.
 *
 * @author Evgeny Shishkin
 */
public class PrintView extends ImageView implements IPrintView {

    public PrintView(Context context) {
        super(context);
        init(context, null);
    }

    public PrintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PrintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PrintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        PrintDrawable icon = PrintViewUtils.initIcon(context, attrs, isInEditMode());
        setImageDrawable(icon);
    }

    @Override
    public PrintDrawable getIcon() {
        return (PrintDrawable) getDrawable();
    }

    @Override
    public void setIconText(int resId) {
        getIcon().setIconText(resId);
    }

    @Override
    public void setIconText(CharSequence text) {
        getIcon().setIconText(text);
    }

    @Override
    public CharSequence getIconText() {
        return getIcon().getIconText();
    }

    @Override
    public void setIconColor(int resId) {
        getIcon().setIconColor(resId);
    }

    @Override
    public void setIconColor(ColorStateList colors) {
        getIcon().setIconColor(colors);
    }

    @Override
    public final ColorStateList getIconColor() {
        return getIcon().getIconColor();
    }

    @Override
    public void setIconSize(int resId) {
        getIcon().setIconSize(resId);
        // hack for calling resizeFromDrawable()
        setSelected(isSelected());
    }

    @Override
    public void setIconSize(int unit, float size) {
        getIcon().setIconSize(unit, size);
        // hack for calling resizeFromDrawable()
        setSelected(isSelected());
    }

    @Override
    public int getIconSize() {
        return getIcon().getIconSize();
    }

    @Override
    public void setIconFont(String path) {
        getIcon().setIconFont(path);
    }

    @Override
    public void setIconFont(Typeface font) {
        getIcon().setIconFont(font);
    }

    @Override
    public Typeface getIconFont() {
        return getIcon().getIconFont();
    }

}