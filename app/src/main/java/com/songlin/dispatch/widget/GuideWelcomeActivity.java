package com.songlin.dispatch.widget;


import com.songlin.dispatch.R;
import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

public class GuideWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.CustomWelcomeScreenTheme)
                .titlePage(R.drawable.ic_launcher, "移动派工", R.color.orange_background)
                .basicPage(R.drawable.ic_launcher, "移动派工", "打造家电维修服务行业\n全新的O2O服务平台", R.color.red_background)
                .parallaxPage(R.layout.parallax_example, "运营模式", "线上下单预约，线下上门服务\n快速准确解决用户困扰", R.color.purple_background, 0.2f, 2f)
                .basicPage(R.drawable.ic_launcher, "展望未来", "努力在成为国内最大的家电维修平台路上", R.color.blue_background)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }


    public static String welcomeKey() {
        return "派工简介";
    }

}