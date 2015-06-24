package com.suren.touchzoomimageview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.suren.touchzoomimageview.R;
import com.suren.touchzoomimageview.util.AnimUtil;


/**
 * Created by OA on 2015/5/14.
 */
public class TouchZoomImageView extends ImageView {

    private Rect rect;
    private float mZoomNum = 1.2f;

    public TouchZoomImageView(Context context) {
        super(context);
        init();
    }

    public TouchZoomImageView(Context context, AttributeSet attrs){
        super(context, attrs);

        //get attrs
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TouchZoomImageView);
        mZoomNum = a.getFloat(R.styleable.TouchZoomImageView_zoomNum, 1.2f);
        a.recycle();

        init();
    }

    private void init(){
        this.setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                rect = new Rect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
                AnimUtil.animZomm(this, mZoomNum);
                break;

            case MotionEvent.ACTION_UP:
                AnimUtil.animZomm(this, 1.0f);
                break;

            default:
                this.getHitRect(rect);
                if(rect.contains(
                        Math.round(this.getX() + event.getX()),
                        Math.round(this.getY() + event.getY()))) {
                    // touch move inside
                } else {
                    // touch move inside
                    AnimUtil.animZomm(this, 1.0f);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
