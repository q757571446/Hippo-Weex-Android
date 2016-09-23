package com.example.hippoweex.weex.module;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.core.manager.ClassManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.WXImageStrategy;

import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 16-8-1.
 * 难道native界面只能做最后一级？？？，一旦界面跳转进native, 当前需求全存储在本地，如果只能更新时候热修复native相关界面
 * weex>>>控制中转在weex，可以通过调用module开放出来的方法跳weex/native
 * native>>>控制中转在native，
 * weex >>> weex >>> weex：始终启动的都是同一个native界面
 * weex >>> native >>> native
 *
 *
 * 界面IOC容器
 *  一级页面: MainTab保存
 *  简单返回多级页面：SimpleBackPage保存
 *  复杂标题多级页面：ComplexTitlePage保存
 * scheme：
 * android.resource:资源文件
 * android.class:类文件
 *      weex>>>native:
 *          android.class://com.example.kevin/demo.view.fragment?title="native页面"#TabFragment
 *          标题栏: Activity取得相应信息设置
 *          文件信息: Activity取得 Fragment文件，反射创建替换
 *      weex>>>weex:
 *          http://www.baidu.com:8080/build/component/index.js?title="weex页面" || file://sdcard/com.example.kevin/data/index.js?title="weex页面"
 *          标题栏: Activity取得相应信息设置
 *          文件信息：默认已经知道 WeexFragment文件地址
 *          内嵌weex地址: url
 *      native>>>native
 *          标题栏：
 *          文件信息：
 *      native>>>weex: http://www.baidu.com:8080/build/component/index.js?title="weex页面" || file://sdcard/com.example.kevin/data/index.js?title="weex页面"
 *          标题栏：取得相应信息设置
 *          文件信息已获得
 *          内嵌weex地址:url
 *     更复杂标题栏的建造，需要专门定义module
 *
 * 命名规范：[scheme:][//authority:port][path][?query][#fragment]
 * 1. SimpleBackActivity: 例如:android.class://com.eample.kevin/demo/view/TabFragment.class?title="native界面"
 * <Intent-Filter>
 *     <action android:name="Intent.ACTION_VIEW"/>
 *     <category android:name="com.taobao.android.intent.category.WEEX"/>
 *     <data
 *          android:scheme="http"
 *          android:scheme="https"
 *          android:scheme="file"
 *          android:scheme="android.class"
 *          host:"com.example.kevin"/>
 * </Intent-Filter>
 * Weex界面通过使用@mipmap/icon_left, @drawable/icon_right, @layout/main_layout,@class/fragment
 * 2. MainActivity: 可选择制定入口文件
 *
 * http:网络地址
 * file:文件地址
 * weex >>> weex:
 *  1. 标题栏在native维护:
 *      一般的页面，具有左边事件点击，标题栏。 通过uri传递：
 *          a. 标题栏信息
 *          b. 指定Fragment信息
 *          c. url信息
 *  2. 标题栏在weex维护
 */
public class ResourceModule {
    public static Uri getResourceUri(Context packageContext, int res) {
        try {
            Resources resources = packageContext.getResources();
            return getResourceUri(resources, packageContext.getPackageName(),
                    res);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    private static Uri getResourceUri(Resources resources, String appPkg,
                                      int res) throws Resources.NotFoundException {
        String resPkg = resources.getResourcePackageName(res);
        String type = resources.getResourceTypeName(res);
        String name = resources.getResourceEntryName(res);
        System.out.println("resPkg="+resPkg+",type="+type+",name="+name);

        return makeResourceUri(appPkg, resPkg, type, name);
    }

    public static Uri getClassUri(Context context, Class clazz, Map<String, String> params){
        String packageName = context.getPackageName();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("android.class");
        builder.encodedAuthority(packageName);
        String clazzPath = clazz.getName();
        String[] segments = clazzPath.split("\\.");
        for (String seg : segments){
            if(packageName.contains(seg)){
                continue;
            }
            builder.appendEncodedPath(seg);
        }

        if(params != null){
            for (Map.Entry<String,String> entry : params.entrySet()){
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private static Uri makeResourceUri(String appPkg, String resPkg,
                                       String type, String name) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
        uriBuilder.encodedAuthority(appPkg);
        uriBuilder.appendEncodedPath(type);
        if (!appPkg.equals(resPkg)) {
            uriBuilder.appendEncodedPath(resPkg + ":" + name);
        } else {
            uriBuilder.appendEncodedPath(name);
        }
        return uriBuilder.build();
    }



    public static int getResource(Uri uri){
        String scheme = uri.getScheme();
        if(scheme.startsWith("http") || scheme.startsWith("file")){
            return -1;
        }

        String host = uri.getEncodedAuthority();
        List<String> pathSegments = uri.getPathSegments();

        String typeName = pathSegments.get(0);
        String resName = pathSegments.get(1);
       return getResourceField(host, typeName, resName);
    }

    private static int getResourceField(String packageName, String typeName, String resName){
        int resId = -1;
        try {
            Class<?> clazz = ClassManager.getClazz(packageName + ".R$" + typeName);
            resId = clazz.getField(resName).getInt(null);
        } catch (Exception e) {
//            ErrorDeterminer.handleException(e);
//            if(Config.DEBUG){
//                LoggerFactory.getLogger("Resource").error("cannot find resource in package: {}, name: {}",packageName, resName);
//            }
        }
        return resId;
    }

    //@mipmap/icon_wechat
    public static void loadImage(String url, ImageView view, WXImageStrategy strategy){
        String authority = Uri.parse(url).getEncodedAuthority();
        if(TextUtils.isEmpty(authority)){
            return ;
        }

        if(!TextUtils.isEmpty(strategy.placeHolder)){
            Picasso.Builder builder=new Picasso.Builder(WXEnvironment.getApplication());
            Picasso picasso=builder.build();
            picasso.load(Uri.parse(strategy.placeHolder)).into(view);

            view.setTag(strategy.placeHolder.hashCode(),picasso);
        }
        if(url.startsWith("app")){
            //加载打包进apk的资源>>> app://icon_logo
            loadResourceInApplication(authority, view, strategy);
        }else if(url.startsWith("file")){
            //file://icon_logo
            //加载更新到文件的资源
            loadResourceInFile(authority, view, strategy);
        }else if(url.startsWith("http")){
            //http://xxxx
            //加载网络资源
            loadResourceInNet(url, view, strategy);
        }
    }

    public static void loadResourceInNet(final String url, final ImageView view, final WXImageStrategy strategy) {
        Picasso.with(WXEnvironment.getApplication())
                .load(url)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        if(strategy.getImageListener()!=null){
                            strategy.getImageListener().onImageFinish(url,view,true,null);
                        }

                        if(!TextUtils.isEmpty(strategy.placeHolder)){
                            ((Picasso) view.getTag(strategy.placeHolder.hashCode())).cancelRequest(view);
                        }
                    }

                    @Override
                    public void onError() {
                        if(strategy.getImageListener()!=null){
                            strategy.getImageListener().onImageFinish(url,view,false,null);
                        }
                    }
                });
    }

    public static String getResourceInNet(String url){
        return url;
    }

    public static void loadResourceInFile(String resName, ImageView view, WXImageStrategy strategy) {
        //TODO 本地资源文件
    }

    public static String getResourceInFile(String resName){
        //TODO
        return null;
    }

    public static void loadResourceInApplication(String resName, ImageView view, WXImageStrategy strategy) {
        int resId = getResourceInApplication(resName);
        Picasso.with(WXEnvironment.getApplication())
        .load(resId)
        .into(view);
    }

    public static int getResourceInApplication(String resName){
        Context context = WXEnvironment.getApplication();
        return getResourceField(context.getPackageName(), "mipmap", resName);
    }
}
