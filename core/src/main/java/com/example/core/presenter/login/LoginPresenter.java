package com.example.core.presenter.login;

import android.content.Context;

import com.example.core.HttpResultFunc;
import com.example.core.HttpSubscriber;
import com.example.core.exception.ApiException;
import com.example.core.manager.UserManager;
import com.example.core.network.service.ApiService;
import com.example.core.presenter.RxPresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginPresenter extends RxPresenter<ILoginView> {
    public String tick;

    @Inject
    public LoginPresenter(Context context, ApiService requestApi) {
        super(context, requestApi);
    }

    public void getVericodeImage() {
        requestApi.receivePicCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JSONObject, String>() {
                    @Override
                    public String call(JSONObject jsonObject) {
                        tick = jsonObject.optString("tick");
                        return jsonObject.optString("captchaUrl");
                    }
                })
                .onErrorResumeNext(new HttpResultFunc<Observable<? extends String>>())
                .filter(new ViewFilterFunc<String>())
                .subscribe(new HttpSubscriber<String>() {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(String picUrl) {
                        getView().setVericodePic(picUrl);
                    }
                });
    }

    /**
     * 用户登录
     */
    public void login() {
        if (isViewAttached()) {
            final String mobilePhone = getView().getUsername();
            final String password = getView().getPassword();
            final String verifyCode = getView().getVerifyCode();

            Map<String,Object> map = new HashMap<>();
            map.put("mobile",mobilePhone);
            map.put("passwd",password);
            map.put("tick",tick);
            map.put("captcha",verifyCode);
            requestApi.login(map)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<JSONObject, JSONObject>() {
                        @Override
                        public JSONObject call(JSONObject jsonObject) {
                            UserManager.saveUserToken(context, jsonObject);
                            return jsonObject;
                        }
                    })
                    .filter(new ViewFilterFunc<JSONObject>())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            getView().navigateToMainActivity();
                        }
                    })
                    .subscribe();
        }
    }
}
