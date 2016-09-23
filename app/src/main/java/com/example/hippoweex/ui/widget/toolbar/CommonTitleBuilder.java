package com.example.hippoweex.ui.widget.toolbar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.hippoweex.R;

public class CommonTitleBuilder {


    TextView textTitleLeft;
    TextView textTitleCenter;
    TextView textTitleRight;
    View rlTitleBar;


    /**
     * Activity中的标题栏
     *
     * @param activity
     */
    public CommonTitleBuilder(Activity activity) {
        this.rlTitleBar = activity.findViewById(R.id.rl_title_bar);
        if (rlTitleBar == null) {
            throw new RuntimeException("Cannot find title bar!");
        }
        this.textTitleCenter = ((TextView) this.rlTitleBar
                .findViewById(R.id.text_title_center));
        this.textTitleLeft = ((TextView) this.rlTitleBar
                .findViewById(R.id.text_title_left));
        this.textTitleRight = ((TextView) this.rlTitleBar
                .findViewById(R.id.text_title_right));
    }

    /**
     * Fragment中的标题栏
     *
     * @param view
     */
    public CommonTitleBuilder(View view) {
        this.rlTitleBar = view.findViewById(R.id.rl_title_bar);
        if (rlTitleBar == null) {
            throw new RuntimeException("Cannot find title bar!");
        }
        this.textTitleCenter = ((TextView) this.rlTitleBar
                .findViewById(R.id.text_title_center));

        this.textTitleLeft = ((TextView) this.rlTitleBar
                .findViewById(R.id.text_title_left));
        this.textTitleRight = ((TextView) this.rlTitleBar
                .findViewById(R.id.text_title_right));
    }

    public View build() {
        return this.rlTitleBar;
    }

    public TextView getLeftItem(){
        return this.textTitleLeft;
    }

    public TextView getRightItem(){
        return this.textTitleRight;
    }

    public TextView getTitle(){
        return this.textTitleCenter;
    }

    public CommonTitleBuilder setLeftImage(int leftImage) {
        TextView localItemLeft = this.textTitleLeft;
        localItemLeft.setVisibility(View.VISIBLE);

        if (leftImage > 0) {
            localItemLeft.setCompoundDrawablesWithIntrinsicBounds(leftImage, 0,0,0);
        } else {
            localItemLeft.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        return this;
    }

    public CommonTitleBuilder setLeftImage(Bitmap leftImage) {
        TextView localItemLeft = this.textTitleLeft;
        localItemLeft.setVisibility(View.VISIBLE);

        if (leftImage !=null) {
            localItemLeft.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(leftImage), null,null,null);
        } else {
            localItemLeft.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
        return this;
    }

    public CommonTitleBuilder setRightImage(int rightImage) {
        TextView localItemRight = this.textTitleRight;
        localItemRight.setVisibility(View.VISIBLE);

        if (rightImage > 0) {
            localItemRight.setCompoundDrawablesWithIntrinsicBounds(0,0,rightImage,0);
        } else {
            localItemRight.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        return this;
    }

    public CommonTitleBuilder setRightImage(Bitmap rightImage) {
        TextView localItemRight = this.textTitleRight;
        localItemRight.setVisibility(View.VISIBLE);

        if (rightImage != null) {
            localItemRight.setCompoundDrawablesWithIntrinsicBounds(null, null,new BitmapDrawable(rightImage),null);
        } else {
            localItemRight.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
        return this;
    }

    public CommonTitleBuilder setLeftText(String leftText){
        TextView localItemLeft = this.textTitleLeft;
        localItemLeft.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(leftText)) {
            localItemLeft.setText(leftText);
        } else {
            localItemLeft.setText("");
        }
        return this;
    }

    public CommonTitleBuilder setRightText(String rightText){
        TextView localItemRight = this.textTitleRight;
        localItemRight.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(rightText)) {
            localItemRight.setText(rightText);
        } else {
            localItemRight.setText("");
        }
        return this;
    }

    public CommonTitleBuilder setLeftItemClickListener(
            View.OnClickListener listener) {
        TextView localItemLeft = this.textTitleLeft;
        localItemLeft.setVisibility(View.VISIBLE);
        localItemLeft.setOnClickListener(listener);
        return this;
    }

    public CommonTitleBuilder setRightItemClickListener(
            View.OnClickListener listener) {
        TextView localItemRight = this.textTitleRight;
        localItemRight.setVisibility(View.VISIBLE);
        localItemRight.setOnClickListener(listener);
        return this;
    }

    public CommonTitleBuilder setTitleText(String title){
        TextView localItemCenter = this.textTitleCenter;
        if (!TextUtils.isEmpty(title)) {
            localItemCenter.setVisibility(View.VISIBLE);
            localItemCenter.setText(title);
        } else {
            localItemCenter.setVisibility(View.GONE);
            localItemCenter.setText("");
        }
        return this;
    }
}