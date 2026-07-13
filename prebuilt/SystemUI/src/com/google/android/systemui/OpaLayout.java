package com.google.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ButtonDispatcher;
import com.android.systemui.statusbar.policy.KeyButtonView;

/* JADX INFO: loaded from: classes.dex */
public class OpaLayout extends FrameLayout implements ButtonDispatcher.ButtonInterface {
    private static final int ANIMATION_STATE_DIAMOND = 1;
    private static final int ANIMATION_STATE_NONE = 0;
    private static final int ANIMATION_STATE_OTHER = 3;
    private static final int ANIMATION_STATE_RETRACT = 2;
    private static final int COLLAPSE_ANIMATION_DURATION_BG = 100;
    private static final int COLLAPSE_ANIMATION_DURATION_RY = 83;
    private static final int DIAMOND_ANIMATION_DURATION = 200;
    private static final float DIAMOND_DOTS_SCALE_FACTOR = 0.8f;
    private static final float DIAMOND_HOME_SCALE_FACTOR = 0.625f;
    private static final int DOTS_RESIZE_DURATION = 200;
    private static final int HALO_ANIMATION_DURATION = 100;
    private static final float HALO_SCALE_FACTOR = 0.47619048f;
    private static final int HOME_REAPPEAR_ANIMATION_OFFSET = 33;
    private static final int HOME_REAPPEAR_DURATION = 150;
    private static final int HOME_RESIZE_DURATION = 83;
    private static final int LINE_ANIMATION_DURATION_X = 275;
    private static final int LINE_ANIMATION_DURATION_Y = 133;
    private static final int MIN_DIAMOND_DURATION = 100;
    private static final int RETRACT_ANIMATION_DURATION = 300;
    private static final String TAG = "OpaLayout";
    private int mAnimationState;
    private View mBlue;
    private View mBottom;
    private final Runnable mCheckLongPress;
    private final Interpolator mCollapseInterpolator;
    private final ArraySet<Animator> mCurrentAnimators;
    private final Interpolator mDiamondInterpolator;
    private final Interpolator mDotsFullSizeInterpolator;
    private final Interpolator mFastOutSlowInInterpolator;
    private View mGreen;
    private View mHalo;
    private KeyButtonView mHome;
    private final Interpolator mHomeDisappearInterpolator;
    private boolean mIsPressed;
    private boolean mIsVertical;
    private View mLeft;
    private boolean mLongClicked;
    private boolean mOpaEnabled;
    private View mRed;
    private final Runnable mRetract;
    private final Interpolator mRetractInterpolator;
    private View mRight;
    private long mStartTime;
    private View mTop;
    private View mWhite;
    private View mYellow;

    public OpaLayout(Context context) {
        super(context);
        this.mFastOutSlowInInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mHomeDisappearInterpolator = new PathInterpolator(DIAMOND_DOTS_SCALE_FACTOR, 0.0f, 1.0f, 1.0f);
        this.mCollapseInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        this.mDotsFullSizeInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mRetractInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mDiamondInterpolator = new PathInterpolator(0.2f, 0.0f, 0.2f, 1.0f);
        this.mCheckLongPress = new Runnable() { // from class: com.google.android.systemui.OpaLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (!OpaLayout.this.mIsPressed) {
                    return;
                }
                OpaLayout.this.mLongClicked = true;
            }
        };
        this.mRetract = new Runnable() { // from class: com.google.android.systemui.OpaLayout.2
            @Override // java.lang.Runnable
            public void run() {
                OpaLayout.this.cancelCurrentAnimation();
                OpaLayout.this.startRetractAnimation();
            }
        };
        this.mAnimationState = ANIMATION_STATE_NONE;
        this.mCurrentAnimators = new ArraySet<>();
    }

    public OpaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mFastOutSlowInInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mHomeDisappearInterpolator = new PathInterpolator(DIAMOND_DOTS_SCALE_FACTOR, 0.0f, 1.0f, 1.0f);
        this.mCollapseInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        this.mDotsFullSizeInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mRetractInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mDiamondInterpolator = new PathInterpolator(0.2f, 0.0f, 0.2f, 1.0f);
        this.mCheckLongPress = new Runnable() { // from class: com.google.android.systemui.OpaLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (!OpaLayout.this.mIsPressed) {
                    return;
                }
                OpaLayout.this.mLongClicked = true;
            }
        };
        this.mRetract = new Runnable() { // from class: com.google.android.systemui.OpaLayout.2
            @Override // java.lang.Runnable
            public void run() {
                OpaLayout.this.cancelCurrentAnimation();
                OpaLayout.this.startRetractAnimation();
            }
        };
        this.mAnimationState = ANIMATION_STATE_NONE;
        this.mCurrentAnimators = new ArraySet<>();
    }

    public OpaLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mFastOutSlowInInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mHomeDisappearInterpolator = new PathInterpolator(DIAMOND_DOTS_SCALE_FACTOR, 0.0f, 1.0f, 1.0f);
        this.mCollapseInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        this.mDotsFullSizeInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mRetractInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mDiamondInterpolator = new PathInterpolator(0.2f, 0.0f, 0.2f, 1.0f);
        this.mCheckLongPress = new Runnable() { // from class: com.google.android.systemui.OpaLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (!OpaLayout.this.mIsPressed) {
                    return;
                }
                OpaLayout.this.mLongClicked = true;
            }
        };
        this.mRetract = new Runnable() { // from class: com.google.android.systemui.OpaLayout.2
            @Override // java.lang.Runnable
            public void run() {
                OpaLayout.this.cancelCurrentAnimation();
                OpaLayout.this.startRetractAnimation();
            }
        };
        this.mAnimationState = ANIMATION_STATE_NONE;
        this.mCurrentAnimators = new ArraySet<>();
    }

    public OpaLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mFastOutSlowInInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mHomeDisappearInterpolator = new PathInterpolator(DIAMOND_DOTS_SCALE_FACTOR, 0.0f, 1.0f, 1.0f);
        this.mCollapseInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        this.mDotsFullSizeInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mRetractInterpolator = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
        this.mDiamondInterpolator = new PathInterpolator(0.2f, 0.0f, 0.2f, 1.0f);
        this.mCheckLongPress = new Runnable() { // from class: com.google.android.systemui.OpaLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (!OpaLayout.this.mIsPressed) {
                    return;
                }
                OpaLayout.this.mLongClicked = true;
            }
        };
        this.mRetract = new Runnable() { // from class: com.google.android.systemui.OpaLayout.2
            @Override // java.lang.Runnable
            public void run() {
                OpaLayout.this.cancelCurrentAnimation();
                OpaLayout.this.startRetractAnimation();
            }
        };
        this.mAnimationState = ANIMATION_STATE_NONE;
        this.mCurrentAnimators = new ArraySet<>();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBlue = findViewById(R.id.blue);
        this.mRed = findViewById(R.id.red);
        this.mYellow = findViewById(R.id.yellow);
        this.mGreen = findViewById(R.id.green);
        this.mWhite = findViewById(R.id.white);
        this.mHalo = findViewById(R.id.halo);
        this.mHome = (KeyButtonView) findViewById(R.id.home_button);
        setOpaEnabled(true);
    }

    @Override // android.view.View
    public void setOnLongClickListener(final View.OnLongClickListener l) {
        this.mHome.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.google.android.systemui.OpaLayout$-void_setOnLongClickListener_android_view_View$OnLongClickListener_l_LambdaImpl0
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View arg0) {
                return this.val$this.m1725com_google_android_systemui_OpaLayout_lambda$1(l, arg0);
            }
        });
    }

    /* JADX INFO: renamed from: -com_google_android_systemui_OpaLayout_lambda$1, reason: not valid java name */
    /* synthetic */ boolean m1725com_google_android_systemui_OpaLayout_lambda$1(View.OnLongClickListener l, View v) {
        l.onLongClick(this.mHome);
        return true;
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener l) {
        this.mHome.setOnTouchListener(l);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mOpaEnabled) {
            return false;
        }
        int action = ev.getAction();
        switch (action) {
            case ANIMATION_STATE_NONE /* 0 */:
                if (!this.mCurrentAnimators.isEmpty()) {
                    if (this.mAnimationState != 2) {
                        return false;
                    }
                    endCurrentAnimation();
                }
                this.mStartTime = SystemClock.elapsedRealtime();
                this.mLongClicked = false;
                this.mIsPressed = true;
                startDiamondAnimation();
                removeCallbacks(this.mCheckLongPress);
                postDelayed(this.mCheckLongPress, ViewConfiguration.getLongPressTimeout());
                return false;
            case 1:
            case ANIMATION_STATE_OTHER /* 3 */:
                if (this.mAnimationState == 1) {
                    long targetTime = 100 - (SystemClock.elapsedRealtime() - this.mStartTime);
                    removeCallbacks(this.mRetract);
                    postDelayed(this.mRetract, targetTime);
                    removeCallbacks(this.mCheckLongPress);
                    return false;
                }
                boolean doRetract = this.mIsPressed && !this.mLongClicked;
                this.mIsPressed = false;
                if (doRetract) {
                    this.mRetract.run();
                }
                return false;
            case 2:
            default:
                return false;
        }
    }

    @Override // com.android.systemui.statusbar.phone.ButtonDispatcher.ButtonInterface
    public void setImageResource(int resId) {
        ((ImageView) this.mWhite).setImageResource(resId);
    }

    @Override // com.android.systemui.statusbar.phone.ButtonDispatcher.ButtonInterface
    public void setImageDrawable(Drawable drawable) {
        ((ImageView) this.mWhite).setImageDrawable(drawable);
    }

    @Override // com.android.systemui.statusbar.phone.ButtonDispatcher.ButtonInterface
    public void abortCurrentGesture() {
        this.mHome.abortCurrentGesture();
    }

    private void startDiamondAnimation() {
        this.mCurrentAnimators.clear();
        this.mCurrentAnimators.addAll((ArraySet<? extends Animator>) getDiamondAnimatorSet());
        this.mAnimationState = 1;
        startAll(this.mCurrentAnimators);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRetractAnimation() {
        this.mCurrentAnimators.clear();
        this.mCurrentAnimators.addAll((ArraySet<? extends Animator>) getRetractAnimatorSet());
        this.mAnimationState = 2;
        startAll(this.mCurrentAnimators);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLineAnimation() {
        this.mCurrentAnimators.clear();
        this.mCurrentAnimators.addAll((ArraySet<? extends Animator>) getLineAnimatorSet());
        this.mAnimationState = ANIMATION_STATE_OTHER;
        startAll(this.mCurrentAnimators);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCollapseAnimation() {
        this.mCurrentAnimators.clear();
        this.mCurrentAnimators.addAll((ArraySet<? extends Animator>) getCollapseAnimatorSet());
        this.mAnimationState = ANIMATION_STATE_OTHER;
        startAll(this.mCurrentAnimators);
    }

    private Animator getScaleAnimatorX(View v, float factor, int duration, Interpolator interpolator) {
        RenderNodeAnimator anim = new RenderNodeAnimator(ANIMATION_STATE_OTHER, factor);
        anim.setTarget(v);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        return anim;
    }

    private Animator getScaleAnimatorY(View v, float factor, int duration, Interpolator interpolator) {
        RenderNodeAnimator anim = new RenderNodeAnimator(4, factor);
        anim.setTarget(v);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        return anim;
    }

    private Animator getDeltaAnimatorX(View v, Interpolator interpolator, float deltaX, int duration) {
        RenderNodeAnimator anim = new RenderNodeAnimator(8, v.getX() + deltaX);
        anim.setTarget(v);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        return anim;
    }

    private Animator getDeltaAnimatorY(View v, Interpolator interpolator, float deltaY, int duration) {
        RenderNodeAnimator anim = new RenderNodeAnimator(9, v.getY() + deltaY);
        anim.setTarget(v);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        return anim;
    }

    private Animator getTranslationAnimatorX(View v, Interpolator interpolator, int duration) {
        RenderNodeAnimator anim = new RenderNodeAnimator(ANIMATION_STATE_NONE, 0.0f);
        anim.setTarget(v);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        return anim;
    }

    private Animator getTranslationAnimatorY(View v, Interpolator interpolator, int duration) {
        RenderNodeAnimator anim = new RenderNodeAnimator(1, 0.0f);
        anim.setTarget(v);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        return anim;
    }

    private void startAll(ArraySet<Animator> animators) {
        for (int i = animators.size() - 1; i >= 0; i--) {
            animators.valueAt(i).start();
        }
    }

    private float getPxVal(int id) {
        return getResources().getDimensionPixelOffset(id);
    }

    private ArraySet<Animator> getDiamondAnimatorSet() {
        ArraySet<Animator> animators = new ArraySet<>();
        animators.add(getDeltaAnimatorY(this.mTop, this.mDiamondInterpolator, -getPxVal(R.dimen.opa_diamond_translation), 200));
        animators.add(getScaleAnimatorX(this.mTop, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mTop, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getDeltaAnimatorY(this.mBottom, this.mDiamondInterpolator, getPxVal(R.dimen.opa_diamond_translation), 200));
        animators.add(getScaleAnimatorX(this.mBottom, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mBottom, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getDeltaAnimatorX(this.mLeft, this.mDiamondInterpolator, -getPxVal(R.dimen.opa_diamond_translation), 200));
        animators.add(getScaleAnimatorX(this.mLeft, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mLeft, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getDeltaAnimatorX(this.mRight, this.mDiamondInterpolator, getPxVal(R.dimen.opa_diamond_translation), 200));
        animators.add(getScaleAnimatorX(this.mRight, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mRight, DIAMOND_DOTS_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorX(this.mWhite, DIAMOND_HOME_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mWhite, DIAMOND_HOME_SCALE_FACTOR, 200, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorX(this.mHalo, HALO_SCALE_FACTOR, 100, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mHalo, HALO_SCALE_FACTOR, 100, this.mFastOutSlowInInterpolator));
        getLongestAnim(animators).addListener(new AnimatorListenerAdapter() { // from class: com.google.android.systemui.OpaLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                OpaLayout.this.mCurrentAnimators.clear();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                OpaLayout.this.startLineAnimation();
            }
        });
        return animators;
    }

    private ArraySet<Animator> getRetractAnimatorSet() {
        ArraySet<Animator> animators = new ArraySet<>();
        animators.add(getTranslationAnimatorX(this.mRed, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getTranslationAnimatorY(this.mRed, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getScaleAnimatorX(this.mRed, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorY(this.mRed, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getTranslationAnimatorX(this.mBlue, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getTranslationAnimatorY(this.mBlue, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getScaleAnimatorX(this.mBlue, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorY(this.mBlue, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getTranslationAnimatorX(this.mGreen, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getTranslationAnimatorY(this.mGreen, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getScaleAnimatorX(this.mGreen, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorY(this.mGreen, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getTranslationAnimatorX(this.mYellow, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getTranslationAnimatorY(this.mYellow, this.mRetractInterpolator, RETRACT_ANIMATION_DURATION));
        animators.add(getScaleAnimatorX(this.mYellow, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorY(this.mYellow, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorX(this.mWhite, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorY(this.mWhite, 1.0f, RETRACT_ANIMATION_DURATION, this.mRetractInterpolator));
        animators.add(getScaleAnimatorX(this.mHalo, 1.0f, RETRACT_ANIMATION_DURATION, this.mFastOutSlowInInterpolator));
        animators.add(getScaleAnimatorY(this.mHalo, 1.0f, RETRACT_ANIMATION_DURATION, this.mFastOutSlowInInterpolator));
        getLongestAnim(animators).addListener(new AnimatorListenerAdapter() { // from class: com.google.android.systemui.OpaLayout.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                OpaLayout.this.mCurrentAnimators.clear();
                OpaLayout.this.mAnimationState = OpaLayout.ANIMATION_STATE_NONE;
            }
        });
        return animators;
    }

    private ArraySet<Animator> getCollapseAnimatorSet() {
        Animator deltaAnimatorX;
        Animator deltaAnimatorX2;
        Animator deltaAnimatorX3;
        Animator deltaAnimatorX4;
        ArraySet<Animator> animators = new ArraySet<>();
        if (this.mIsVertical) {
            deltaAnimatorX = getDeltaAnimatorY(this.mRed, this.mCollapseInterpolator, -getPxVal(R.dimen.opa_line_x_collapse_ry), 83);
        } else {
            deltaAnimatorX = getDeltaAnimatorX(this.mRed, this.mCollapseInterpolator, getPxVal(R.dimen.opa_line_x_collapse_ry), 83);
        }
        animators.add(deltaAnimatorX);
        animators.add(getScaleAnimatorX(this.mRed, 1.0f, 200, this.mDotsFullSizeInterpolator));
        animators.add(getScaleAnimatorY(this.mRed, 1.0f, 200, this.mDotsFullSizeInterpolator));
        if (this.mIsVertical) {
            deltaAnimatorX2 = getDeltaAnimatorY(this.mBlue, this.mCollapseInterpolator, -getPxVal(R.dimen.opa_line_x_collapse_bg), 100);
        } else {
            deltaAnimatorX2 = getDeltaAnimatorX(this.mBlue, this.mCollapseInterpolator, getPxVal(R.dimen.opa_line_x_collapse_bg), 100);
        }
        animators.add(deltaAnimatorX2);
        animators.add(getScaleAnimatorX(this.mBlue, 1.0f, 200, this.mDotsFullSizeInterpolator));
        animators.add(getScaleAnimatorY(this.mBlue, 1.0f, 200, this.mDotsFullSizeInterpolator));
        if (this.mIsVertical) {
            deltaAnimatorX3 = getDeltaAnimatorY(this.mYellow, this.mCollapseInterpolator, getPxVal(R.dimen.opa_line_x_collapse_ry), 83);
        } else {
            deltaAnimatorX3 = getDeltaAnimatorX(this.mYellow, this.mCollapseInterpolator, -getPxVal(R.dimen.opa_line_x_collapse_ry), 83);
        }
        animators.add(deltaAnimatorX3);
        animators.add(getScaleAnimatorX(this.mYellow, 1.0f, 200, this.mDotsFullSizeInterpolator));
        animators.add(getScaleAnimatorY(this.mYellow, 1.0f, 200, this.mDotsFullSizeInterpolator));
        if (this.mIsVertical) {
            deltaAnimatorX4 = getDeltaAnimatorY(this.mGreen, this.mCollapseInterpolator, getPxVal(R.dimen.opa_line_x_collapse_bg), 100);
        } else {
            deltaAnimatorX4 = getDeltaAnimatorX(this.mGreen, this.mCollapseInterpolator, -getPxVal(R.dimen.opa_line_x_collapse_bg), 100);
        }
        animators.add(deltaAnimatorX4);
        animators.add(getScaleAnimatorX(this.mGreen, 1.0f, 200, this.mDotsFullSizeInterpolator));
        animators.add(getScaleAnimatorY(this.mGreen, 1.0f, 200, this.mDotsFullSizeInterpolator));
        Animator homeScaleX = getScaleAnimatorX(this.mWhite, 1.0f, HOME_REAPPEAR_DURATION, this.mFastOutSlowInInterpolator);
        Animator homeScaleY = getScaleAnimatorY(this.mWhite, 1.0f, HOME_REAPPEAR_DURATION, this.mFastOutSlowInInterpolator);
        Animator haloScaleX = getScaleAnimatorX(this.mHalo, 1.0f, HOME_REAPPEAR_DURATION, this.mFastOutSlowInInterpolator);
        Animator haloScaleY = getScaleAnimatorY(this.mHalo, 1.0f, HOME_REAPPEAR_DURATION, this.mFastOutSlowInInterpolator);
        homeScaleX.setStartDelay(33L);
        homeScaleY.setStartDelay(33L);
        haloScaleX.setStartDelay(33L);
        haloScaleY.setStartDelay(33L);
        animators.add(homeScaleX);
        animators.add(homeScaleY);
        animators.add(haloScaleX);
        animators.add(haloScaleY);
        getLongestAnim(animators).addListener(new AnimatorListenerAdapter() { // from class: com.google.android.systemui.OpaLayout.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                OpaLayout.this.mCurrentAnimators.clear();
                OpaLayout.this.mAnimationState = OpaLayout.ANIMATION_STATE_NONE;
            }
        });
        return animators;
    }

    private ArraySet<Animator> getLineAnimatorSet() {
        ArraySet<Animator> animators = new ArraySet<>();
        if (this.mIsVertical) {
            animators.add(getDeltaAnimatorY(this.mRed, this.mFastOutSlowInInterpolator, getPxVal(R.dimen.opa_line_x_trans_ry), LINE_ANIMATION_DURATION_X));
            animators.add(getDeltaAnimatorX(this.mRed, this.mFastOutSlowInInterpolator, getPxVal(R.dimen.opa_line_y_translation), LINE_ANIMATION_DURATION_Y));
            animators.add(getDeltaAnimatorY(this.mBlue, this.mFastOutSlowInInterpolator, getPxVal(R.dimen.opa_line_x_trans_bg), LINE_ANIMATION_DURATION_X));
            animators.add(getDeltaAnimatorY(this.mYellow, this.mFastOutSlowInInterpolator, -getPxVal(R.dimen.opa_line_x_trans_ry), LINE_ANIMATION_DURATION_X));
            animators.add(getDeltaAnimatorX(this.mYellow, this.mFastOutSlowInInterpolator, -getPxVal(R.dimen.opa_line_y_translation), LINE_ANIMATION_DURATION_Y));
            animators.add(getDeltaAnimatorY(this.mGreen, this.mFastOutSlowInInterpolator, -getPxVal(R.dimen.opa_line_x_trans_bg), LINE_ANIMATION_DURATION_X));
        } else {
            animators.add(getDeltaAnimatorX(this.mRed, this.mFastOutSlowInInterpolator, -getPxVal(R.dimen.opa_line_x_trans_ry), LINE_ANIMATION_DURATION_X));
            animators.add(getDeltaAnimatorY(this.mRed, this.mFastOutSlowInInterpolator, getPxVal(R.dimen.opa_line_y_translation), LINE_ANIMATION_DURATION_Y));
            animators.add(getDeltaAnimatorX(this.mBlue, this.mFastOutSlowInInterpolator, -getPxVal(R.dimen.opa_line_x_trans_bg), LINE_ANIMATION_DURATION_X));
            animators.add(getDeltaAnimatorX(this.mYellow, this.mFastOutSlowInInterpolator, getPxVal(R.dimen.opa_line_x_trans_ry), LINE_ANIMATION_DURATION_X));
            animators.add(getDeltaAnimatorY(this.mYellow, this.mFastOutSlowInInterpolator, -getPxVal(R.dimen.opa_line_y_translation), LINE_ANIMATION_DURATION_Y));
            animators.add(getDeltaAnimatorX(this.mGreen, this.mFastOutSlowInInterpolator, getPxVal(R.dimen.opa_line_x_trans_bg), LINE_ANIMATION_DURATION_X));
        }
        animators.add(getScaleAnimatorX(this.mWhite, 0.0f, 83, this.mHomeDisappearInterpolator));
        animators.add(getScaleAnimatorY(this.mWhite, 0.0f, 83, this.mHomeDisappearInterpolator));
        animators.add(getScaleAnimatorX(this.mHalo, 0.0f, 83, this.mHomeDisappearInterpolator));
        animators.add(getScaleAnimatorY(this.mHalo, 0.0f, 83, this.mHomeDisappearInterpolator));
        getLongestAnim(animators).addListener(new AnimatorListenerAdapter() { // from class: com.google.android.systemui.OpaLayout.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                OpaLayout.this.startCollapseAnimation();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                OpaLayout.this.mCurrentAnimators.clear();
            }
        });
        return animators;
    }

    public void setOpaEnabled(boolean enabled) {
        boolean enabled2 = !enabled ? UserManager.isDeviceInDemoMode(getContext()) : true;
        Log.i(TAG, "Setting opa enabled to " + enabled2);
        this.mOpaEnabled = enabled2;
        int visibility = enabled2 ? ANIMATION_STATE_NONE : 4;
        this.mBlue.setVisibility(visibility);
        this.mRed.setVisibility(visibility);
        this.mYellow.setVisibility(visibility);
        this.mGreen.setVisibility(visibility);
        this.mHalo.setVisibility(visibility);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelCurrentAnimation() {
        if (this.mCurrentAnimators.isEmpty()) {
            return;
        }
        for (int i = this.mCurrentAnimators.size() - 1; i >= 0; i--) {
            Animator a = this.mCurrentAnimators.valueAt(i);
            a.removeAllListeners();
            a.cancel();
        }
        this.mCurrentAnimators.clear();
        this.mAnimationState = ANIMATION_STATE_NONE;
    }

    private void endCurrentAnimation() {
        if (this.mCurrentAnimators.isEmpty()) {
            return;
        }
        for (int i = this.mCurrentAnimators.size() - 1; i >= 0; i--) {
            Animator a = this.mCurrentAnimators.valueAt(i);
            a.removeAllListeners();
            a.end();
        }
        this.mCurrentAnimators.clear();
        this.mAnimationState = ANIMATION_STATE_NONE;
    }

    private Animator getLongestAnim(ArraySet<Animator> animators) {
        long longestDuration = Long.MIN_VALUE;
        Animator longestAnim = null;
        for (int i = animators.size() - 1; i >= 0; i--) {
            Animator a = animators.valueAt(i);
            if (a.getTotalDuration() > longestDuration) {
                longestAnim = a;
                longestDuration = a.getTotalDuration();
            }
        }
        return longestAnim;
    }

    @Override // com.android.systemui.statusbar.phone.ButtonDispatcher.ButtonInterface
    public void setVertical(boolean vertical) {
        this.mIsVertical = vertical;
        if (this.mIsVertical) {
            this.mTop = this.mGreen;
            this.mBottom = this.mBlue;
            this.mRight = this.mYellow;
            this.mLeft = this.mRed;
            return;
        }
        this.mTop = this.mRed;
        this.mBottom = this.mYellow;
        this.mLeft = this.mBlue;
        this.mRight = this.mGreen;
    }

    @Override // com.android.systemui.statusbar.phone.ButtonDispatcher.ButtonInterface
    public void setCarMode(boolean carMode) {
        setOpaEnabled(!carMode);
    }
}
