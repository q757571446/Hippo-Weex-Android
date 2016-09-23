package com.example.hippoweex.ui.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.hippoweex.AppContext;
import com.example.hippoweex.R;
import com.example.hippoweex.ioc.dagger.AppComponent;
import com.example.hippoweex.ui.view.IDialogView;
import com.example.hippoweex.ui.view.IToastView;
import com.example.hippoweex.ui.view.fragment.WeexFragment;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 *  1. 设置布局
 *  2. 绑定ButterKnife
 *  3. Dagger注入视图
 *  4. 初始化标题栏
 *  5. 提供展示吐司方法
 *  6. 提供展示Dialog方法
 */
public abstract class BaseActivity extends SupportActivity implements IToastView,IDialogView {
    protected CommonTitleBuilder mTitleBar;

    private Unbinder unbinder;

    /**
     * activity通过这个方法设置布局
     * @return 布局文件id
     */
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        if (hasTitleBar()) {
            mTitleBar = initTitleBuilder();
            initializeTitleBar(mTitleBar);
        }
    }


    public CommonTitleBuilder getTitleBar() {
        return mTitleBar;
    }

    protected CommonTitleBuilder initTitleBuilder() {
        return new CommonTitleBuilder(this);
    }

    protected boolean hasTitleBar(){
        return findViewById(R.id.rl_title_bar)!=null;
    }

    /**
     * activity通过这个方法注入依赖
     */
    protected void injectDependencies() {

    }

    protected void initializeTitleBar(CommonTitleBuilder builder) {
    }

    public static String getFragmentPath(Uri uri){
        String scheme = uri.getScheme();
        String classPath;
        if (scheme.startsWith("android.class")){
            //类文件
            String packageName = uri.getEncodedAuthority();
            List<String> pathSegments = uri.getPathSegments();
            StringBuilder path = new StringBuilder(packageName);

            //本地类文件
            for (String segment : pathSegments){
                path.append(".");
                //去除class文件名后缀
                if(segment.contains(".")){
                    segment = segment.substring(0, segment.indexOf("."));
                }
                path.append(segment);
            }
            classPath = path.toString();

        }else if(scheme.startsWith("http") || scheme.startsWith("file")){
            classPath = WeexFragment.class.getName();
        }else{
            throw new RuntimeException("cannot find scheme: "+scheme);
        }
        return classPath;
    }


    @Override
    public void setRootView() {
        setContentView(getLayoutId());
    }

    @Override
    public void onContentChanged() {
        unbinder = ButterKnife.bind(this);
        super.onContentChanged();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public AppComponent getAppComponent() {
        return AppContext.getContext().getAppComponent();
    }

    @Override
    public void showToast(int msgId) {
        showToast(getString(msgId));
    }

    @Override
    public void showToast(int msgId, int duration) {
        showToast(getString(msgId),duration);
    }

    @Override
    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showToast(String msg, int duration) {
        Toast toast = Toast.makeText(this, msg, duration);
        toast.show();
    }
}
