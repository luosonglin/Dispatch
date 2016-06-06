package com.songlin.chengyu.widget;


import com.songlin.dispatch.R;
import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

public class GuideWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.CustomWelcomeScreenTheme)
                .titlePage(R.drawable.ic_launcher, "成语", R.color.blue)
                .parallaxPage(R.layout.parallax_example, "成语猜猜看", "成语大全\n快速帮助小朋友们记忆成语", R.color.yellow, 0.2f, 2f)
                .basicPage(R.drawable.ic_launcher, "展望以后", "改进改进再改进", R.color.green)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }


    public static String welcomeKey() {
        return "陈琦成语";
    }

}