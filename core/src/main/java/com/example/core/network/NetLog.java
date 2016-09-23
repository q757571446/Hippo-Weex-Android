package com.example.core.network;

import android.content.Context;
import android.os.Environment;

import com.example.core.utils.CacheHelper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.internal.platform.Platform;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NetLog implements NetLogInterceptor.Logger {
        private final Context context;
        StringBuffer sb;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        public NetLog(Context context) {
            sb = new StringBuffer();
            this.context = context;
        }

        @Override
        public void log(String message) {
            Platform.get().log(Platform.INFO, message, null);
            sb.append(message + System.getProperty("line.separator"));
        }

        @Override
        public void startLog() {
            sb.delete(0, sb.length());
        }

        @Override
        public void endLog() {
             Observable.just(sb.toString())
                     .observeOn(Schedulers.io())
                     .subscribe(new Action1<String>() {
                         @Override
                         public void call(String log) {
                             if (Environment.getExternalStorageState().equals("mounted")) {
                                 File cacheFile = new File(context.getFilesDir(), Config.LOG_PATH);
                                 String logName = "net-" + formatter.format(new Date()) + "-" + System.currentTimeMillis() + "-log";
                                 File fileName = new File(cacheFile, logName);
                                 CacheHelper cacheHelper = CacheHelper.get(cacheFile, CacheHelper.MAX_SIZE, Config.LOG_CACHE_COUNT);

                                 cacheHelper.put(fileName, log);
                             }
                         }
                     });
        }
    }