package com.example.hippoweex.weex.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.core.HttpResultFunc;
import com.example.core.HttpSubscriber;
import com.example.core.exception.ApiException;
import com.example.core.exception.ERROR;
import com.example.core.manager.UserManager;
import com.example.core.network.retrofit.OkHttpUtils;
import com.example.core.network.retrofit.RetrofitUtils;
import com.example.core.utils.GsonTools;
import com.example.hippoweex.Navigator;
import com.example.hippoweex.ui.view.SimpleBackPage;
import com.example.hippoweex.weex.WeexIntercepter;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2016/8/19.
 */
public class RetrofitHttpAdapter implements IWXHttpAdapter {
    private Navigator navigator;
    private Context context;
    private OnHttpListener listener;

    public RetrofitHttpAdapter(Context context){
        this.context = context;
        this.navigator = new Navigator();
    }

    public WXHttpService getService(OnHttpListener listener) {
        OkHttpClient okHttpClient = OkHttpUtils.getDefaultBuild(context)
                .addInterceptor(new WeexIntercepter(listener))
                .build();
        return RetrofitUtils.getDefaultBuild(context).client(okHttpClient).build().create(WXHttpService.class);
    }

    interface WXHttpService{
        /**
         * Token
         *  @Header("x-token") 本地填写
         * Post ：以json形式传递参数
         *  @Headers({"Content-Type: application/json"})
         *  @Body Map<String, Object> params
         * Get
         *  @QueryMap Map<String,Object> querys
         */
        @POST
        Observable<String> postRequest(@Url String url, @HeaderMap Map<String, String> headers, @Body Map<String, Object> params);

        @GET
        Observable<String> getRequest(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);
    }
    @Override
    public void sendRequest(WXRequest request, OnHttpListener listener) {
        this.listener = listener;

        WXHttpService requestApi = getService(listener);
        //添加请求消息头
        Map<String,String>  headers = request.paramMap;

        //添加请求参数
        Map<String,Object> params = new HashMap<>();
        if(!TextUtils.isEmpty(request.body)){
            params = GsonTools.convertJsonToNestMap(request.body);
        }


        //默认所有接口都会添加token
        headers.put("x-token", UserManager.readUserToken(context).toString());
        //所有请求默认附加token,没有Token默认跳转首页
        if ("POST".equals(request.method) || "PUT".equals(request.method) || "PATCH".equals(request.method)) {
            //post请求分为表单数据提交和json数据提交两种方式
            requestApi.postRequest(request.url, headers, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(new HttpResultFunc<Observable<? extends String>>())
                    .subscribe(new WeexSubscriber());
        }else {
            //默认get
            requestApi.getRequest(request.url, headers, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(new HttpResultFunc<Observable<? extends String>>())
                    .subscribe(new WeexSubscriber());
        }
    }


    class WeexSubscriber extends HttpSubscriber<String> {
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

        }

        @Override
        public void onNext(String jsonObject) {
        }

        protected void onTokenExpire(ApiException ex) {
            //如果token过期, 处理跳转到登录界面
            navigator.pushSimpleBackPage(context, SimpleBackPage.LOGIN_FRAGMENT);
        }

        protected void onApiException(ApiException ex) {

        }
    }


    public Context getContext(){
        return this.context;
    }

    public OnHttpListener getListener(){
        return this.listener;
    }
}
