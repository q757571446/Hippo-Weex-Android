package com.example.hippoweex.ioc.dagger.activity.fragment;


import com.example.hippoweex.ioc.dagger.activity.fragment.other.OtherComponent;
import com.example.hippoweex.ioc.dagger.activity.fragment.user.UserComponent;
import com.example.hippoweex.ioc.dagger.activity.fragment.user.UserModule;

import dagger.Subcomponent;

/**
 * Created by kevin on 16-7-31.
 */
@PerFragment
@Subcomponent(
        modules = {SimpleMvpModule.class}
)
public interface SimpleMvpComponent {

    OtherComponent otherComponent();

    UserComponent userComponent(UserModule userModule);//contain the instance of user
}
