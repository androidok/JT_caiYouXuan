/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dou361.ijkplayer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ScreenUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.ISurfaceTextureHolder;
/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/7/22 16:38
 * <p>
 * 描 述：这个是4.0以上的一个播放器显示view
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TextureRenderView extends TextureView implements IRenderView {
    private String TAG = this.getClass().getSimpleName();


    /**
     * 记录是拖拉照片模式还是放大缩小照片模式
     */
    private int mode = 0;// 初始状态
    /**
     * 拖拉照片模式
     */
    private static final int MODE_DRAG = 1;
    /**
     * 放大缩小照片模式
     */
    private static final int MODE_ZOOM = 2;

    private static float touchSlop = 0;

    private int screenWidth = 0;//屏幕的宽度

    public GestureDetector mGestureDetector = null;

    public ScaleGestureDetector scaleGestureDetector = null;
    private int fatherView_W;//surfaceview父控件的宽度

    private int fatherView_H;//surfaceview父控件的高度
    private int start_Top = -1, start_Right = -1, start_Left = -1, start_Bottom = -1;
    private int initViewWidth = 0;  //surfaceview控件的宽度

    private int initViewHeight = 0;//surfaceview控件的高度
    private int View_Width = 0;//surfaceview的宽度

    private int View_Height = 0;//surfaceview的高度
    private float ratio = 0.3f;


    int distanceX = 0;

    int distanceY = 0;

    private int  current_x, current_y;

    private boolean isControl_Vertical = false;//是否是竖屏

    private boolean isControl_Horizal = false;//是否是横屏
    private View view;

    private boolean isFullScreen;

    private MeasureHelper mMeasureHelper;
    private SimpleGestureListener simpleGestureListener;


    public TextureRenderView(Context context,boolean isFullScreen) {
        super(context);
        this.isFullScreen = isFullScreen;
        initView(context);
    }
//
//    public void  setFullScreenStatus(boolean isFullScreen){
//        this.isFullScreen = isFullScreen;
//    }


    public TextureRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextureRenderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        mMeasureHelper = new MeasureHelper(this);
        mSurfaceCallback = new SurfaceCallback(this);
        setSurfaceTextureListener(mSurfaceCallback);

        touchSlop = ViewConfiguration.getTouchSlop();
        screenWidth = ScreenUtils.getInstance(getContext()).getScreenWidth();
        simpleGestureListener = new SimpleGestureListener();
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new SimpleScaleGestueListener());
        mGestureDetector = new GestureDetector(simpleGestureListener);
        view = TextureRenderView.this;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //        if (isInPlaybackState() && mMediaController != null) {
        //            toggleMediaControlsVisiblity();
        //        }
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                onPointerDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
            default:
                break;
        }
        return scaleGestureDetector.onTouchEvent(event);
    }

    /**
     * 滑动事件分发
     *
     * @param event
     */
    private void onTouchMove(MotionEvent event) {
        if (!isFullScreen) {
            return ;
        }
        int left = 0, top = 0, right = 0, bottom = 0;
        if (mode == MODE_DRAG) {
            left = getLeft();
            right = getRight();
            top = getTop();
            bottom = getBottom();
            distanceX = (int) (event.getRawX() - current_x);
            distanceY = (int) (event.getRawY() - current_y);
            //            if (icallBack != null) {
            //                icallBack.getAngle((int) getX(), this.getWidth());
            //            }
            if (touchSlop <= getDistance(distanceX, distanceY)) {
                left = left + distanceX;
                right = right + distanceX;
                bottom = bottom + distanceY;
                top = top + distanceY;
                // 水平判断
                if (isControl_Horizal) {
                    if (left >= 0) {
                        left = 0;
                        right = this.getWidth();
                    }
                    if (right <= screenWidth) {
                        left = screenWidth - this.getWidth();
                        right = screenWidth;
                    }
                } else {
                    left = getLeft();
                    right = getRight();
                }
                // 垂直判断
                if (isControl_Vertical) {
                    if (top > 0) {
                        top = 0;
                        bottom = this.getHeight();
                    }
                    if (bottom <= start_Bottom) {
                        bottom = start_Bottom;
                        top = fatherView_H - this.getWidth();
                    }
                } else {
                    top = this.getTop();
                    bottom = this.getBottom();
                }
                if (isControl_Horizal || isControl_Vertical) {
                    this.setPosition(left, top, right, bottom);
                }
                current_x = (int) event.getRawX();
                current_y = (int) event.getRawY();
            }
        }
    }

    /**
     * 多点触控时
     *
     * @param event
     */
    private void onPointerDown(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            mode = MODE_ZOOM;
        }
    }

    /**
     * 按下时的事件
     *
     * @param event
     */

    private void onTouchDown(MotionEvent event) {
        mode = MODE_DRAG;
        current_x = (int) event.getRawX();
        current_y = (int) event.getRawY();
        View_Width = getWidth();
        View_Height = getHeight();
        //        mVideoWidth = View_Width;
        //        mVideoHeight = View_Height;
        if (View_Height > fatherView_H) {
            isControl_Vertical = true;
        } else {
            isControl_Vertical = false;
        }
        if (View_Width > fatherView_W) {
            isControl_Horizal = true;
        } else {
            isControl_Horizal = false;
        }
    }
    /**
     * 获取两点的距离
     **/
    private float getDistance(float distanceX, float distanceY) {
        return (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }














    /**
     * 缩放手势的监听事件
     *
     * @author Administrator
     */
    private class SimpleScaleGestueListener implements ScaleGestureDetector.OnScaleGestureListener {
        // 用到的放大缩小的方法
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (!isFullScreen) {
                return false;
            }
            int left = 0, right = 0, top = 0, bottom = 0;
            float length = 0;
            if (mode == MODE_ZOOM) {
                float ratio = detector.getScaleFactor();
                left = getLeft();
                top = getTop();
                bottom = getBottom();
                right = getRight();
                if (ratio > 1) { // 放大撞状态
                    length = (int) ((getHeight() * (ratio - 1)) / 7.0);
                    left = (int) (left - length / 2);
                    right = (int) (right + length / 2);
                    bottom = (int) (bottom + length / 2);
                    top = (int) (top - length / 2);
                    if (getWidth() <= (screenWidth * 3) && getHeight() <= (fatherView_H * 3)) {
                        setPosition(left, top, right, bottom);
                    }
                } else {
                    length = (int) ((getHeight() * (1 - ratio)) / 7.0);
                    left = (int) (left + length / 2);
                    right = (int) (right - length / 2);
                    bottom = (int) (bottom - length / 2);
                    top = (int) (top + length / 2);
                    if (left >= 0) {
                        left = 0;
                    }
                    if (right <= screenWidth) {
                        right = screenWidth;
                    }
                    if (top >= 0) {
                        top = 0;
                    }
                    if (bottom <= fatherView_H) {
                        bottom = fatherView_H;
                    }
                    Log.e("getWidth():", String.valueOf(getWidth()) + " getHeight():" + getHeight());
                    if (getWidth() > initViewWidth && getHeight() > fatherView_H) {
                        setPosition(left, top, right, bottom);
                    } else {
                        Log.e("start_Left:",
                                String.valueOf(start_Left) + " start_Top:" + start_Top + " start_Right:" + start_Right + " start_Bottom:" + start_Bottom);
                        setPosition(start_Left, start_Top, start_Right, start_Bottom);
                    }
                }
            }
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }

    }

    /**
     * 双击事件的处理
     *
     * @author Administrator
     */
    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {


        // 用到的双击的方法
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (!isFullScreen) {
                return false;
            }
            Log.i(TAG, "双击屏幕");
            // 双击屏幕
            int left = 0, top = 0, right = 0, bottom = 0;
            int length = 0;
            left = getLeft();
            top = getTop();
            bottom = getBottom();
            right = getRight();
            if (getHeight() > fatherView_H) {
                // 缩小模式
                Log.i(TAG, "缩小模式");
                while (getHeight() > fatherView_H) {
                    length = (int) ((getHeight() * ratio) / 5.0);
                    left = (int) (getLeft() + length / 2);
                    right = (int) (getRight() - length / 2);
                    bottom = (int) (getBottom() - length / 2);
                    top = (int) (getTop() + length / 2);
                    if (left >= 0) {
                        left = 0;
                    }
                    if (right <= screenWidth) {
                        right = screenWidth;
                    }
                    if (top >= 0) {
                        top = 0;
                    }
                    if (bottom <= fatherView_H) {
                        bottom = fatherView_H;
                    }
                    if (getWidth() > initViewWidth && getHeight() > fatherView_H) {
                        setPosition(left, top, right, bottom);
                    } else {
                        setPosition(start_Left, start_Top, start_Right, start_Bottom);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                // 放大模式
                Log.i(TAG, "放大模式");
                if (getHeight() <= fatherView_H) {
                    while (getHeight() < initViewHeight * 2) {
                        length = (int) ((getHeight() * ratio) / 5.0);
                        left = (int) (getLeft() - length / 2);
                        right = (int) (getRight() + length / 2);
                        bottom = (int) (getBottom() + length / 2);
                        top = (int) (getTop() - length / 2);
                        if (getWidth() <= (screenWidth * 3) && getHeight() <= (fatherView_H * 3)) {
                            setPosition(left, top, right, bottom);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (MotionEvent.ACTION_UP==e.getAction()) {
                EventManager.getEventBus().post(PubUtil.ONE_CLICK_EVENT);
            }
            return true;
        }
    }

    /**
     * 实现拖动的处理
     */
    private void setPosition(int left, int top, int right, int bottom) {
        this.layout(left, top, right, bottom);

    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (start_Top == -1) {
            start_Top = top;
            start_Left = left;
            start_Right = right;
            start_Bottom = bottom;
            initViewWidth = view.getWidth();
            initViewHeight = view.getHeight();
        }
    }



    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean shouldWaitForResize() {
        return false;
    }

    //--------------------
    // Layout & Measure
    //--------------------
    @Override
    public void setVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth > 0 && videoHeight > 0) {
            mMeasureHelper.setVideoSize(videoWidth, videoHeight);
            requestLayout();
        }
    }

    @Override
    public void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen) {
        if (videoSarNum > 0 && videoSarDen > 0) {
            mMeasureHelper.setVideoSampleAspectRatio(videoSarNum, videoSarDen);
            requestLayout();
        }
    }

    @Override
    public void setVideoRotation(int degree) {
        mMeasureHelper.setVideoRotation(degree);
        setRotation(degree);
    }

    @Override
    public void setAspectRatio(int aspectRatio) {
        mMeasureHelper.setAspectRatio(aspectRatio);
        requestLayout();
    }

    /**
     * surfaceView父控件的宽高
     *
     * @param fatherView_Width
     * @param fatherView_Height
     */
    @Override
    public void setFather(int fatherView_Width, int fatherView_Height) {
        this.fatherView_W = fatherView_Width;
        this.fatherView_H = fatherView_Height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mMeasureHelper.getMeasuredWidth(), mMeasureHelper.getMeasuredHeight());
    }

    //--------------------
    // TextureViewHolder
    //--------------------

    public ISurfaceHolder getSurfaceHolder() {
        return new InternalSurfaceHolder(this, mSurfaceCallback.mSurfaceTexture);
    }

    private static final class InternalSurfaceHolder implements ISurfaceHolder {
        private TextureRenderView mTextureView;
        private SurfaceTexture mSurfaceTexture;

        public InternalSurfaceHolder(@NonNull TextureRenderView textureView,
                                     @Nullable SurfaceTexture surfaceTexture) {
            mTextureView = textureView;
            mSurfaceTexture = surfaceTexture;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void bindToMediaPlayer(IMediaPlayer mp) {
            if (mp == null)
                return;

            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) &&
                    (mp instanceof ISurfaceTextureHolder)) {
                ISurfaceTextureHolder textureHolder = (ISurfaceTextureHolder) mp;
                mTextureView.mSurfaceCallback.setOwnSurfaceTecture(false);

                SurfaceTexture surfaceTexture = textureHolder.getSurfaceTexture();
                if (surfaceTexture != null) {
                    mTextureView.setSurfaceTexture(surfaceTexture);
                } else {
                    textureHolder.setSurfaceTexture(mSurfaceTexture);
                }
            } else {
                mp.setSurface(openSurface());
            }
        }

        @NonNull
        @Override
        public IRenderView getRenderView() {
            return mTextureView;
        }

        @Nullable
        @Override
        public SurfaceHolder getSurfaceHolder() {
            return null;
        }

        @Nullable
        @Override
        public SurfaceTexture getSurfaceTexture() {
            return mSurfaceTexture;
        }

        @Nullable
        @Override
        public Surface openSurface() {
            if (mSurfaceTexture == null)
                return null;
            return new Surface(mSurfaceTexture);
        }
    }

    //-------------------------
    // SurfaceHolder.Callback
    //-------------------------

    @Override
    public void addRenderCallback(IRenderCallback callback) {
        mSurfaceCallback.addRenderCallback(callback);
    }

    @Override
    public void removeRenderCallback(IRenderCallback callback) {
        mSurfaceCallback.removeRenderCallback(callback);
    }

    private SurfaceCallback mSurfaceCallback;

    private static final class SurfaceCallback implements SurfaceTextureListener {
        private SurfaceTexture mSurfaceTexture;
        private boolean mIsFormatChanged;
        private int mWidth;
        private int mHeight;

        private boolean mOwnSurfaceTecture = true;

        private WeakReference<TextureRenderView> mWeakRenderView;
        private Map<IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap<IRenderCallback, Object>();

        public SurfaceCallback(@NonNull TextureRenderView renderView) {
            mWeakRenderView = new WeakReference<TextureRenderView>(renderView);
        }

        public void setOwnSurfaceTecture(boolean ownSurfaceTecture) {
            mOwnSurfaceTecture = ownSurfaceTecture;
        }

        public void addRenderCallback(@NonNull IRenderCallback callback) {
            mRenderCallbackMap.put(callback, callback);

            ISurfaceHolder surfaceHolder = null;
            if (mSurfaceTexture != null) {
                if (surfaceHolder == null)
                    surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), mSurfaceTexture);
                callback.onSurfaceCreated(surfaceHolder, mWidth, mHeight);
            }

            if (mIsFormatChanged) {
                if (surfaceHolder == null)
                    surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), mSurfaceTexture);
                callback.onSurfaceChanged(surfaceHolder, 0, mWidth, mHeight);
            }
        }

        public void removeRenderCallback(@NonNull IRenderCallback callback) {
            mRenderCallbackMap.remove(callback);
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mSurfaceTexture = surface;
            mIsFormatChanged = false;
            mWidth = 0;
            mHeight = 0;

            ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), surface);
            for (IRenderCallback renderCallback : mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceCreated(surfaceHolder, 0, 0);
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            mSurfaceTexture = surface;
            mIsFormatChanged = true;
            mWidth = width;
            mHeight = height;

            ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), surface);
            for (IRenderCallback renderCallback : mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceChanged(surfaceHolder, 0, width, height);
            }
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            mSurfaceTexture = surface;
            mIsFormatChanged = false;
            mWidth = 0;
            mHeight = 0;

            ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(mWeakRenderView.get(), surface);
            for (IRenderCallback renderCallback : mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceDestroyed(surfaceHolder);
            }

            return mOwnSurfaceTecture;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    //--------------------
    // Accessibility
    //--------------------

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(TextureRenderView.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TextureRenderView.class.getName());
    }
}
