package com.example.hippoweex.ui;

public interface IActivityStatus {
    int DESTROY = 0;
    int STOP = 2;
    int PAUSE = 1;
    int RESUME = 3;

    void setRootView();

    void initData();

    void initWidget();



}
