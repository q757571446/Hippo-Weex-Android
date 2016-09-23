package com.example.hippoweex.ui;

import android.app.Activity;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by dell on 2016/9/20.
 */
public class ActivityStack {
    private static Stack<IActivityStatus> activityStack;
    private static final ActivityStack instance = new ActivityStack();

    private ActivityStack() {
    }

    public static ActivityStack create() {
        return instance;
    }

    public int getCount() {
        return activityStack.size();
    }

    public void addActivity(IActivityStatus activity) {
        if(activityStack == null) {
            activityStack = new Stack();
        }

        activityStack.add(activity);
    }

    public Activity topActivity() {
        if(activityStack == null) {
            throw new NullPointerException("Activity stack is Null,your Activity must extend KJActivity");
        } else if(activityStack.isEmpty()) {
            return null;
        } else {
            IActivityStatus activity = (IActivityStatus)activityStack.lastElement();
            return (Activity)activity;
        }
    }

    public Activity findActivity(Class<?> cls) {
        IActivityStatus activity = null;
        Iterator var4 = activityStack.iterator();

        while(var4.hasNext()) {
            IActivityStatus aty = (IActivityStatus)var4.next();
            if(aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }

        return (Activity)activity;
    }

    public void finishActivity() {
        IActivityStatus activity = (IActivityStatus)activityStack.lastElement();
        this.finishActivity((Activity)activity);
    }

    public void finishActivity(Activity activity) {
        if(activity != null) {
            activityStack.remove(activity);
            activity = null;
        }

    }

    public void finishActivity(Class<?> cls) {
        Iterator var3 = activityStack.iterator();

        while(var3.hasNext()) {
            IActivityStatus activity = (IActivityStatus)var3.next();
            if(activity.getClass().equals(cls)) {
                this.finishActivity((Activity)activity);
            }
        }

    }

    public void finishOthersActivity(Class<?> cls) {
        Iterator var3 = activityStack.iterator();

        while(var3.hasNext()) {
            IActivityStatus activity = (IActivityStatus)var3.next();
            if(!activity.getClass().equals(cls)) {
                this.finishActivity((Activity)activity);
            }
        }

    }

    public void finishAllActivity() {
        int i = 0;

        for(int size = activityStack.size(); i < size; ++i) {
            if(activityStack.get(i) != null) {
                ((Activity)activityStack.get(i)).finish();
            }
        }

        activityStack.clear();
    }

    /** @deprecated */
    @Deprecated
    public void AppExit(Context cxt) {
        this.appExit(cxt);
    }

    public void appExit(Context context) {
        try {
            this.finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception var3) {
            Runtime.getRuntime().exit(-1);
        }

    }
}
