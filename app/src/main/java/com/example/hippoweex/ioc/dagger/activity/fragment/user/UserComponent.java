package com.example.hippoweex.ioc.dagger.activity.fragment.user;


import dagger.Subcomponent;

/**
 * Created by kevinhao on 16/7/27.
 */
@UserScope
@Subcomponent(
        modules = {UserModule.class}
)
public interface UserComponent {
}
