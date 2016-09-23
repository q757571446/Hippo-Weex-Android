package com.example.core;


import com.example.core.exception.ApiException;
import com.example.core.exception.ERROR;

import rx.Subscriber;


/**
 * 截取异常分发
 * @param <T>
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ERROR.UNKNOWN));
        }
    }

    protected abstract void onError(ApiException ex);

}
