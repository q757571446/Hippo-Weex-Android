package com.example.core.presenter;

import android.content.Context;

import com.example.core.HttpSubscriber;
import com.example.core.exception.ApiException;
import com.example.core.exception.ERROR;
import com.example.core.manager.UserManager;
import com.example.core.network.service.ApiService;


/**
 * Created by kevinhao on 16/7/26.
 * solve the expire token
 */
public class UserPresenter<V extends IUserView> extends RxPresenter<V> {
    protected UserManager.UserToken userToken;

    public UserPresenter(Context context, UserManager.UserToken userToken, ApiService service) {
        super(context,service);
        this.userToken = userToken;
    }

    /**
     * 分发处理一般的服务器返回异常,token过期异常
     * @param <T>
     */
    protected abstract class TokenSubscriber<T> extends HttpSubscriber<T> {
        @Override
        protected void onError(ApiException ex) {
            if (ex.getCode() == ERROR.TOKEN_EXPIRE) {
                onTokenExpire(ex);
            } else {
                onApiException(ex);
            }
        }

        @Override
        public void onCompleted() {
            //// TODO: 16/7/26 后期可以继续截取做一些界面的显示隐藏工作
            //这里可能会被过滤
        }

        protected void onTokenExpire(ApiException ex) {
            //如果token过期, 处理跳转到登录界面
            if (isViewAttached()) {
                getView().navigateToLoginPage();
            }
        }

        protected void onApiException(ApiException ex) {
            //TODO 如果其他异常, 以吐司形式打印, 后期可以处理成一个控件展示
            ex.printStackTrace();
            if (isViewAttached()) {
                getView().showToast(ex.getDisplayMessage());
            }
        }
    }
}
