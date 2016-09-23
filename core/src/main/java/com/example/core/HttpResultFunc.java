package com.example.core;

import com.example.core.exception.ErrorDeterminer;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by kevinhao on 16/7/21.
 * Http请求错误处理分发
 */
public class HttpResultFunc<R> implements Func1<Throwable, R> {
    @Override
    public R call(Throwable throwable) {
        return (R) Observable.error(ErrorDeterminer.handleException(throwable));
    }
}
