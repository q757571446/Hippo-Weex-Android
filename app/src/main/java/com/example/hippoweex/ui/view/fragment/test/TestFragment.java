package com.example.hippoweex.ui.view.fragment.test;

import android.view.View;
import android.widget.TextView;

import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.fragment.NativeFragment;

import butterknife.BindView;

/**
 * Created by dell on 2016/9/22.
 */
public class TestFragment extends NativeFragment{
    @BindView(R.id.text_data)
    TextView mTxtData;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        String name = getArguments().getString("name");
        String country = getArguments().getString("country");
        mTxtData.setText("name:>>>" + name + "; country>>>" + country);
    }
}
