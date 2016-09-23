package com.example.hippoweex.ui.view.fragment.login;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.core.presenter.login.ILoginView;
import com.example.core.presenter.login.LoginPresenter;
import com.example.hippoweex.MainActivity;
import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.fragment.SimpleMvpFragment;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;
import com.example.hippoweex.utils.DensityUtils;
import com.example.hippoweex.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kevin on 16-7-31.
 */
public class LoginFragment extends SimpleMvpFragment<ILoginView, LoginPresenter>
        implements ILoginView {


    @BindView(R.id.edt_input_username)
    EditText edtInputUsername;

    @BindView(R.id.edt_input_password)
    EditText edtInputPassword;

    @BindView(R.id.edt_input_code)
    EditText edtInputCode;

    @BindView(R.id.text_find_password)
    TextView text_find_password;

    @BindView(R.id.text_register)
    TextView textRegister;

    @BindView(R.id.btn_confirm_login)
    Button btnConfirmLogin;

    @BindView(R.id.img_vericode)
    ImageView imgVericode;

    @BindView(R.id.img_logo)
    ImageView imgLogo;


    @BindView(R.id.group_edit)
    LinearLayout groupEdit;

    @BindView(R.id.group_animate)
    LinearLayout groupAnimate;

    @Inject
    Picasso picasso;

    @Inject
    LoginPresenter _presenter;

    @Override
    protected void initializeTitleBar(CommonTitleBuilder builder) {
        super.initializeTitleBar(builder);
    }

    @Override
    protected void initData() {
        presenter.getVericodeImage();
    }

    @OnClick(R.id.img_vericode)
    void onChangeVerifyCode(){
        presenter.getVericodeImage();
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);

        textRegister.setText(Html.fromHtml("<u>"+getString(R.string.fragment_login_text_register)+"</u>"));

        ScreenUtil.addOnSoftKeyBoardVisibleListener(mContext, new ScreenUtil.OnSoftKeyBoardVisibleListener() {
            @Override
            public void onSoftKeyBoardVisible(boolean visible) {
                if(visible){
                    beginAnimation(ScreenUtil.getKeyBoardHeight(mContext));
                }else{
                    resetAnimation();
                }
            }
        });
    }

    @Override
    public String getVerifyCode() {
        return edtInputCode.getText().toString().trim();
    }

    private void resetAnimation() {
        imgLogo.animate().scaleX(1).scaleY(1).alpha(1).translationY(0).setDuration(400).start();
        groupAnimate.animate().translationY(0).setDuration(400).start();
    }

    private void beginAnimation(int keyboardHeight) {
        int desY = DensityUtils.getScreenH(mContext) - keyboardHeight - btnConfirmLogin.getMeasuredHeight();
        int[] ints = new int[2];
        btnConfirmLogin.getLocationInWindow(ints);
        int transY = ints[1] - desY;
        groupAnimate.animate().translationY(-transY).setDuration(400).start();

        int[] ints2 = new int[2];
        imgLogo.getLocationInWindow(ints);
        int title = getTitleBar().build().getMeasuredHeight();
        int i = ints2[1] - title - ScreenUtil.getStatusBarHeight(mContext);
        imgLogo.animate().scaleX(0.5f).scaleY(0.5f).translationY(i).setDuration(400).start();
    }


    @Override
    protected void injectDependencies() {
        super.injectDependencies();

        getSimpleMvpComponent().otherComponent().inject(this);
    }

    @OnClick(R.id.btn_confirm_login)
    void onLoginButtonClick() {
        presenter.login();
    }

    @OnClick(R.id.text_find_password)
    void onFindPasswordClick() {

    }

    @OnClick(R.id.text_register)
    void onRegisterButtonClick() {
        navigateToRegisterActivity();
    }

    @OnClick(R.id.img_wechat)
    void onWechatImageClick(){
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    public String getUsername() {
        return edtInputUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edtInputPassword.getText().toString().trim();
    }

    @Override
    public void setPassword(String password) {
        edtInputPassword.setText(password);
    }

    @Override
    public void showToast(String msg) {
        mContext.showToast(msg);
    }

    @Override
    public void showToast(int msg) {
        mContext.showToast(getString(msg));
    }

    @Override
    public void setLoginBtnEnable(boolean enable) {
        btnConfirmLogin.setEnabled(enable);
    }

    @Override
    public void navigateToMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
        onBackPressed();
    }

    @Override
    public void setVericodePic(String picUrl) {
        picasso.load(picUrl)
                .into(imgVericode);
    }

    public void navigateToRegisterActivity() {
//        navigator.pushSimpleBackPage(mContext, SimpleBackPage.REGISTER_FRAGMENT);
    }

    @Override
    public LoginPresenter createPresenter() {
        return _presenter;
    }
}