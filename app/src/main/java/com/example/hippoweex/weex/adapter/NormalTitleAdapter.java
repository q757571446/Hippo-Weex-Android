package com.example.hippoweex.weex.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.example.hippoweex.R;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;
import com.example.hippoweex.weex.module.ResourceModule;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 16-8-5.
 *
 */
//标题栏参数
//params: {
//        'title': '标题栏',
//        'url': '下一个界面地址 + 标题栏内容',
//        'animate': 'true', 标题栏是否存在动画
//        'left-item-title': '左边文字',
//        'left-item-color' : '#ffffff',
//        'left-item-image' : 'android.resource://com.example.kevinhao.demo/mipmap/icon_book',
//        'right-item-title': '右边文字',
//        'right-item-color': '#676767',
//        'right-item-image': 'android.resource://com.example.kevinhao.demo/mipmap/icon_setting'
//        },
public class NormalTitleAdapter implements IActivityNavBarSetter {
    private Context context;
    private CommonTitleBuilder builder;

    public NormalTitleAdapter(Context context) {
        this.context = context;
    }

    public NormalTitleAdapter setTitleBuilder(CommonTitleBuilder builder){
        this.builder = builder;
        return this;
    }

    @Override
    public boolean push(String param) {
//        try {
//            JSONObject jsonObject = new JSONObject(param);
//            String url = jsonObject.optString("url");
//            if(TextUtils.isEmpty(url)){
//                return true;
//            }
//        } catch (JSONException e) {
//            return true;
//        }
        return false;
    }

    @Override
    public boolean pop(String param) {
        return clearNavBarMoreItem(param);
    }

    @Override
    public boolean setNavBarRightItem(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            String rightTitle = jsonObject.optString("right-item-title");
            String rightTitleColor = jsonObject.optString("right-item-color");
            String rightImage = jsonObject.optString("right-item-image");
            if(!TextUtils.isEmpty(rightTitle)){
                builder.setRightText(rightTitle);
                if(!TextUtils.isEmpty(rightTitleColor)){
                    builder.getRightItem().setTextColor(Color.parseColor(rightTitleColor));
                }
            }
            if(!TextUtils.isEmpty(rightImage)){
                loadTitleImage(rightImage, new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        builder.setRightImage(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }

        } catch (JSONException e) {
            return true;
        }
        return false;
    }

    @Override
    public boolean clearNavBarRightItem(String param) {
        builder.setRightText("");
        builder.setRightImage(-1);
        return false;
    }

    @Override
    public boolean setNavBarLeftItem(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            String leftTitle = jsonObject.optString("left-item-title");
            String leftTitleColor = jsonObject.optString("left-item-color");
            String leftImage = jsonObject.optString("left-item-image");
            if(!TextUtils.isEmpty(leftTitle)){
                builder.setLeftText(leftTitle);
                if(!TextUtils.isEmpty(leftTitleColor)){
                    builder.getLeftItem().setTextColor(Color.parseColor(leftTitleColor));
                }
            }
            if(!TextUtils.isEmpty(leftImage)){
                loadTitleImage(leftImage, new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        builder.setLeftImage(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }

        } catch (JSONException e) {
            return true;
        }
        return false;
    }

    @Override
    public boolean clearNavBarLeftItem(String param) {
        builder.setLeftText("");
        builder.setLeftImage(-1);
        return false;
    }

    @Override
    public boolean setNavBarMoreItem(String param) {
        boolean b = setNavBarTitle(param);
        boolean b1 = setNavBarLeftItem(param);
        boolean b2 = setNavBarRightItem(param);
        if(b || b1 || b2){
            return true;
        }
        return false;
    }

    @Override
    public boolean clearNavBarMoreItem(String param) {
        clearNavBarLeftItem(param);
        clearNavBarRightItem(param);
        return false;
    }

    @Override
    public boolean setNavBarTitle(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            String title = jsonObject.optString("title");
            if (TextUtils.isEmpty(title)) {
                builder.build().setVisibility(View.GONE);
            }else {
                builder.build().setVisibility(View.VISIBLE);
                builder.setTitleText(title);
            }
        } catch (JSONException e) {
            return true;
        }
        return false;
    }

    public void setNavBarLeftItemClickListener(View.OnClickListener listener){
        builder.setLeftItemClickListener(listener);
    }

    public void setNavBarRightItemClickListener(View.OnClickListener listener){
        builder.setRightItemClickListener(listener);
    }

    public void loadTitleImage(String imageUrl, Target target){
        String authority = Uri.parse(imageUrl).getEncodedAuthority();
        if(TextUtils.isEmpty(authority)){
            return ;
        }
        if(imageUrl.startsWith("app")){
            //加载打包进apk的资源>>> app://icon_logo
            int resId = ResourceModule.getResourceInApplication(authority);
            Picasso.with(context)
                    .load(resId)
                    .resizeDimen(R.dimen.commen_title_item_title,R.dimen.commen_title_item_title)
                    .into(target);
        }else if(imageUrl.startsWith("file")){
            //file://icon_logo
            //加载更新到文件的资源
            String resourceInFile = ResourceModule.getResourceInFile(authority);
            Picasso.with(context)
                    .load(resourceInFile)
                    .resizeDimen(R.dimen.commen_title_item_title,R.dimen.commen_title_item_title)
                    .into(target);
        }else if(imageUrl.startsWith("http")){
            //网络文件
            Picasso.with(context)
                    .load(imageUrl)
                    .resizeDimen(R.dimen.commen_title_item_title,R.dimen.commen_title_item_title)
                    .into(target);
        }
    }

}
