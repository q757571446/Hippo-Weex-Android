package com.example.hippoweex.ui.widget.iconfont;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.core.manager.TypefaceManager;


public class PrintConfig {

    private static PrintConfig sInstance;

    /**
     * Define the default iconic font.
     *
     * @param assets          The application's asset manager.
     * @param defaultFontPath The file name of the font in the assets directory,
     *                        e.g. "fonts/iconic-font.ttf".
     * @see #initDefault(Typeface)
     */
    public static void initDefault(AssetManager assets, String defaultFontPath) {
        Typeface defaultFont = TypefaceManager.load(assets, defaultFontPath);
        initDefault(defaultFont);
    }

    /**
     * Define the default iconic font.
     *
     * @see #initDefault(AssetManager, String)
     */
    public static void initDefault(Typeface defaultFont) {
        sInstance = new PrintConfig(defaultFont);
    }

    static PrintConfig get() {
        if (sInstance == null)
            sInstance = new PrintConfig();
        return sInstance;
    }


    private final Typeface mFont;
    private final boolean mIsFontSet;

    private PrintConfig() {
        this(null);
    }

    private PrintConfig(Typeface defaultFont) {
        mFont = defaultFont;
        mIsFontSet = defaultFont != null;
    }

    /**
     * @return the default font.
     */
    Typeface getFont() {
        return mFont;
    }

    /**
     * @return true if default font is set.
     */
    boolean isFontSet() {
        return mIsFontSet;
    }

}