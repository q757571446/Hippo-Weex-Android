package com.example.hippoweex.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.hippoweex.MainActivity;
import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.activity.SimpleBackActivity;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 原生界面的Fragment
 *  1. 初始化标题栏
 *  2. 绑定ButterKnife
 */
public abstract class NativeFragment extends BaseFragment{

    private Unbinder unbinder;
    private CommonTitleBuilder mTitleBar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        mTitleBar = initTitleBuilder(view);
        initializeTitleBar(mTitleBar);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 子类通过这个方法设置标题栏内容
     * @param builder 标题栏建造者
     */
    protected void initializeTitleBar(CommonTitleBuilder builder) {

    }

    /**
     * 当前根布局是否有标题栏
     * @param view
     * @return
     */
    private boolean hasTitle(View view) {
        return view.findViewById(R.id.rl_title_bar)!=null;
    }

    private void hideTitleBar(){
        if ((getActivity() instanceof SimpleBackActivity)) {
            ((SimpleBackActivity) getActivity()).getTitleBar().build().setVisibility(View.GONE);
        }else if((getActivity() instanceof MainActivity)){//MainActivity中的标题
            ((MainActivity) getActivity()).getTitleBar().build().setVisibility(View.GONE);
        }
    }
    
    protected CommonTitleBuilder initTitleBuilder(View view) {
        //优先采用自身布局中标题栏
        if(hasTitle(view)){
            //隐藏其他条件下的布局
            hideTitleBar();
            return new CommonTitleBuilder(view);
        }else if ((getActivity() instanceof SimpleBackActivity)) {
            return ((SimpleBackActivity) getActivity()).getTitleBar();
        }else if((getActivity() instanceof MainActivity)){//MainActivity中的标题
            return ((MainActivity) getActivity()).getTitleBar();
        }else{
            throw new IllegalArgumentException("cannot find the title bar");
        }
    }

    public CommonTitleBuilder getTitleBar() {
        return this.mTitleBar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
