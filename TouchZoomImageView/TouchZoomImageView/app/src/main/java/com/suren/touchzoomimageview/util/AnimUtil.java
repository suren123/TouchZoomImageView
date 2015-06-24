package com.suren.touchzoomimageview.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by OA on 2015/4/20.
 */
public class AnimUtil {

    public static void animBackgroundColor(final View view, int toColor){

        int beforeColor = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            beforeColor = ((ColorDrawable) background).getColor();

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), beforeColor, toColor);
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (Integer) animator.getAnimatedValue();
                view.setBackgroundColor(color);
            }
        });
        colorAnimation.start();
    }

    public static void animActionBarToColor(final ActionBar actionBar, int fromColor, int toColor){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (Integer) animator.getAnimatedValue();
                Drawable colorDrawable = new ColorDrawable(color);
                actionBar.setBackgroundDrawable(colorDrawable);
            }
        });
        colorAnimation.start();
    }

    public static void animTextViewNum(final TextView tv, int toNum){
        //初始化初始值
        Integer fromNum;
        String fromNumStr = tv.getText().toString();
        if(TextUtils.isEmpty(fromNumStr)){
            fromNum = 0;
        }else{
            fromNum = Integer.parseInt(fromNumStr);
        }

        //播放动画
        ValueAnimator animator = ValueAnimator.ofInt(fromNum, toNum);
        animator.setTarget(tv);
        //加速度
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                String num = animation.getAnimatedValue().toString();
                tv.setText(num);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void rotateViewStart(final View view){
        //初始化初始值
//        Integer fromNum;
//        float startDegree = view.getRotation();
//
//        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(view, "rotation", 0F,  360.0F);
//        rotationAnim.setInterpolator(new LinearInterpolator());
//        rotationAnim.setRepeatCount(ValueAnimator.INFINITE);
//        rotationAnim.setStartDelay(720);
//        rotationAnim.start();

//        view.setTag(rotationAnim);

        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.setInterpolator(new LinearInterpolator());
        viewPropertyAnimator.rotationBy(0.0f);
        viewPropertyAnimator.rotation(360.0f);
        viewPropertyAnimator.setDuration(720l);
        viewPropertyAnimator.withEndAction(new Runnable() {
            @Override
            public void run() {
                view.setRotation(0.0f);
                rotateViewStart(view);
            }
        });
        viewPropertyAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void rotateViewStotp(final View view){
        //初始化初始角度
        float startDegree = view.getRotation();
        if(startDegree == 0.0F){
            return;
        }

        long time = (long) (360.0F - startDegree)*2L;

        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.setInterpolator(new LinearInterpolator());
        viewPropertyAnimator.rotation(360.0f);
        viewPropertyAnimator.setDuration(time);
        viewPropertyAnimator.withEndAction(new Runnable() {
            @Override
            public void run() {
                view.setRotation(0.0f);
            }
        });
        viewPropertyAnimator.start();

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void animFadeIn(final View view){
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.alpha(1f);
        viewPropertyAnimator.setDuration(300l);
        view.setVisibility(View.VISIBLE);
        viewPropertyAnimator.start();

        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void animFadeOut(final View view){
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.alpha(0f);
        viewPropertyAnimator.setDuration(300l);
        viewPropertyAnimator.start();

        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void animZomm(final View view, float scale){
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.scaleX(scale);
        viewPropertyAnimator.scaleY(scale);
        viewPropertyAnimator.setDuration(100l);
        viewPropertyAnimator.start();
    }

}
