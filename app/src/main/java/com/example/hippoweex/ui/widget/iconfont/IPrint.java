package com.example.hippoweex.ui.widget.iconfont;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

interface IPrint {

    /**
     * Sets the icon text from resources.
     *
     * @attr ref R.styleable#PrintView_iconText
     * @see #setIconText(CharSequence)
     * @see #getIconText()
     */
    void setIconText(@StringRes int resId);

    /**
     * Sets the icon text.
     *
     * @attr ref R.styleable#PrintView_iconText
     * @see #setIconText(int)
     * @see #getIconText()
     */
    void setIconText(CharSequence text);

    /**
     * Return the icon text, or null if icon text is not set.
     *
     * @attr ref R.styleable#PrintView_iconText
     * @see #setIconText(int)
     * @see #setIconText(CharSequence)
     */
    CharSequence getIconText();

    /**
     * Sets the icon color from resources.
     *
     * @attr ref R.styleable#PrintView_iconColor
     * @see #setIconColor(ColorStateList)
     * @see #getIconColor()
     */
    void setIconColor(@ColorRes int resId);

    /**
     * Sets the icon color.
     *
     * @attr ref R.styleable#PrintView_iconColor
     * @see #setIconColor(int)
     * @see #getIconColor()
     */
    void setIconColor(ColorStateList colors);

    /**
     * Return the icon colors for the different states (normal, selected, focused).
     *
     * @attr ref R.styleable#PrintView_iconColor
     * @see #setIconColor(ColorStateList)
     * @see #setIconColor(int)
     */
    ColorStateList getIconColor();

    /**
     * Sets the icon size from resources.
     *
     * @attr ref R.styleable#PrintView_iconSize
     * @see #setIconSize(int, float)
     * @see #getIconSize()
     */
    void setIconSize(@DimenRes int resId);

    /**
     * Sets the icon size to a given unit and value. See {@link android.util.TypedValue}
     * for the possible dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref R.styleable#PrintView_iconSize
     * @see #setIconSize(int)
     * @see #getIconSize()
     */
    void setIconSize(int unit, float size);

    /**
     * Return the icon size (in pixels).
     *
     * @attr ref R.styleable#PrintView_iconSize
     * @see #setIconSize(int)
     * @see #setIconSize(int, float)
     */
    int getIconSize();

    /**
     * Sets the iconic font from assets.
     *
     * @param path The file name of the font in the assets directory, e.g. "fonts/iconic-font.ttf".
     * @attr ref R.styleable#PrintView_iconFont
     * @see #setIconFont(Typeface)
     * @see #getIconFont()
     */
    void setIconFont(String path);

    /**
     * Sets the iconic font.
     *
     * @attr ref R.styleable#PrintView_iconFont
     * @see #setIconFont(String)
     * @see #getIconFont()
     */
    void setIconFont(Typeface font);

    /**
     * Return the iconic font.
     *
     * @attr ref R.styleable#PrintView_iconFont
     * @see #setIconFont(String)
     * @see #setIconFont(Typeface)
     */
    Typeface getIconFont();

}