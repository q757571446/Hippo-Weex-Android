package com.example.hippoweex.ioc.dagger.activity.fragment.other;


import com.example.hippoweex.ioc.dagger.activity.fragment.user.UserScope;
import com.example.hippoweex.ui.view.fragment.login.LoginFragment;

import dagger.Subcomponent;

/**
 * Created by kevinhao on 16/7/27.
 */
@UserScope
@Subcomponent
public interface OtherComponent {
    void inject(LoginFragment loginFragment);
}
