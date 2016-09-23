package com.example.hippoweex.weex;

import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dell on 2016/8/19.
 */
public class WeexIntercepter implements Interceptor{
    private final IWXHttpAdapter.OnHttpListener listener;

    public WeexIntercepter(IWXHttpAdapter.OnHttpListener listener){
        this.listener = listener;

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        WXResponse response = new WXResponse();

        try{
            if(listener != null){
                listener.onHttpStart();
            }
            Request originalRequest = chain.request();
            RequestBody requestBody = originalRequest.body();
            boolean hasRequestBody = requestBody != null;
//            if(hasRequestBody){
//                //包装请求体
//                RequestBody upload = new ProgressRequestBody(requestBody,  new ProgressListener() {
//                    @Override
//                    public void onProgress(long currentBytes, long contentLength, boolean done) {
//                        if(listener != null){
//                            long progress = (100 * currentBytes) / contentLength;
//                            listener.onHttpUploadProgress((int) progress);
//                        }
//                    }
//                });
//                originalRequest.newBuilder().method(originalRequest.method(), upload);
//            }
            Response originalResponse = chain.proceed(originalRequest);
            ResponseBody responseBody = originalResponse.body();

            Map<String,List<String>> headers = originalResponse.headers().toMultimap();
            int responseCode = originalResponse.code();
            if(listener != null){
                listener.onHeadersReceived(responseCode, headers);
            }
            response.statusCode = String.valueOf(responseCode);
            if (responseCode >= 200 && responseCode<=299) {
                response.originalData = responseBody.bytes();
            } else {
                response.errorMsg = responseBody.string();
            }

            if(listener != null){
                listener.onHttpFinish(response);
            }

//            ResponseBody download = new ProgressResponseBody(responseBody, new ProgressListener() {
//                @Override
//                public void onProgress(long currentBytes, long contentLength, boolean done) {
//                    if (listener != null) {
//                        long progress = (100 * currentBytes) / contentLength;
//                        listener.onHttpResponseProgress((int) progress);
//                    }
//                }
//            });
//            return originalResponse.newBuilder().body(download).build();
            return originalResponse;
        }catch (IOException|IllegalArgumentException e){
            e.printStackTrace();
            response.statusCode = "-1";
            response.errorCode="-1";
            response.errorMsg=e.getMessage();
            if(listener!=null){
                listener.onHttpFinish(response);
            }
            throw new IOException(e);
        }
    }
}
