package com.dl.mybaseproject.demo11;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dl.mybaseproject.R;
import com.dl.mybaseproject.Utils;

import java.util.List;

public class SmileLatout extends RelativeLayout {

    private static final String TAG = SmileLatout.class.getSimpleName();

    private FrameLayout flContent;

    private ImageView ivRotationIcon;

    private Context mContext;

    private boolean isExpentEmoji = false;//判断表情框是否打开

    /**
     * 初始化动画是否在执行
     */
    private boolean mOnAnimationRunning = false;

    private long duration = 300;

    private List<Integer> mImageRes;

    private int itemViewHeight = 46;

    public SmileLatout(@NonNull Context context) {
        this(context, null);
    }

    public SmileLatout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmileLatout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.smile_view, null);
        flContent = view.findViewById(R.id.fl_content);
        ivRotationIcon = view.findViewById(R.id.iv_icon);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(view, lp);

        ivRotationIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpentEmoji) {
                    moveDown(mImageRes.size() - 1, ivRotationIcon, null);
                } else {
                    moveUP(ivRotationIcon);
                }
            }
        });
    }

    public void setView(List<Integer> imageRes) {
        if (imageRes == null || imageRes.size() == 0) return;
        this.mImageRes = imageRes;
        View view;
        for (int j = 0; j < mImageRes.size(); j++) {
            view = View.inflate(mContext, R.layout.smile_item_view, null);
            bindData(j, view);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, Utils.covertDp2Px(mContext, itemViewHeight));
            lp.gravity = Gravity.BOTTOM;
            flContent.addView(view, lp);
        }
    }

    private void bindData(int j, View view) {
        ImageView ivEmgoy = view.findViewById(R.id.iv_emgoy);
        ivEmgoy.setImageResource(mImageRes.get(j));
        if (j != mImageRes.size() - 1) {
            view.setAlpha(0.0f);
        } else {
            view.setAlpha(1.0f);
        }

        ivEmgoy.setTag(j);
        ivEmgoy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAnimationRunning) return;
                int position = (int) v.getTag();
                Log.e(TAG, "position  " + position);

                if (isExpentEmoji) {
                    moveDown(position, ivRotationIcon, onEmojiClickListenering);
                } else {
                    if (onEmojiClickListenering != null) {
                        onEmojiClickListenering.onEmojiClick(mImageRes.get(position));
                    }
                }
            }
        });
    }

    private void moveDown(int position, ImageView iv_icon, SmileLatout.onEmojiClickListenering onEmojiClickListenering) {
        if (mOnAnimationRunning) return;
        AnimatorSet.Builder play = null;
        AnimatorSet animatorSet = new AnimatorSet();
        int childCount = flContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = flContent.getChildAt(i);
            ValueAnimator animatorTranslationX = ObjectAnimator.ofInt(Utils.covertDp2Px(mContext, (childCount - 1 - i) * itemViewHeight), 0);
            play = animatorSet.play(animatorTranslationX);
            animatorTranslationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                    layoutParams.bottomMargin = animatedValue;
                    childAt.setLayoutParams(layoutParams);
                }
            });

            if (i != position) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(childAt, "alpha", 1.0f, 0f);
                if (play != null) {
                    play.with(alphaAnimator);
                }
            }
        }

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(iv_icon, "rotation", 180, 0);
        if (play != null) {
            play.with(rotationAnimator);
        }

        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mOnAnimationRunning = true;
                if (onEmojiClickListenering != null) {
                    onEmojiClickListenering.onEmojiClick(mImageRes.get(position));
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mOnAnimationRunning = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                updateSmialLatoutHeight();
                isExpentEmoji = false;
                mOnAnimationRunning = false;
                Integer remove = mImageRes.remove(position);
                mImageRes.add(mImageRes.size(), remove);
                notifyDataSetChanged();
            }
        });
        animatorSet.start();
    }

    private void notifyDataSetChanged() {
        int childCount = flContent.getChildCount();
        if (childCount == 0) return;
        for (int i = 0; i < childCount; i++) {
            View childAt = flContent.getChildAt(i);
            ((ImageView) childAt.findViewById(R.id.iv_emgoy)).setImageResource(mImageRes.get(i));
            if (i != 2) {
                childAt.setAlpha(0.0f);
            } else {
                childAt.setAlpha(1.0f);
            }
        }
    }


    private void moveUP(ImageView iv_icon) {
        if (mOnAnimationRunning) return;
        AnimatorSet.Builder play = null;
        AnimatorSet animatorSet = new AnimatorSet();
        int childCount = flContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = flContent.getChildAt(i);
            ValueAnimator animatorTranslationX = ObjectAnimator.ofInt(0, Utils.covertDp2Px(mContext, (childCount - 1 - i) * itemViewHeight));
            play = animatorSet.play(animatorTranslationX);
            animatorTranslationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                    layoutParams.bottomMargin = animatedValue;
                    childAt.setLayoutParams(layoutParams);
                }
            });

            if (i != childCount - 1) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(childAt, "alpha", 0f, 1.0f);
                if (play != null) {
                    play.with(alphaAnimator);
                }
            }
        }

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(iv_icon, "rotation", 0, 180);
        if (play != null) {
            play.with(rotationAnimator);
        }

        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mOnAnimationRunning = true;
                updateSmialLatoutHeight();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mOnAnimationRunning = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isExpentEmoji = true;
                mOnAnimationRunning = false;
            }
        });
        animatorSet.start();
    }

    public void updateSmialLatoutHeight() {
        ViewGroup.LayoutParams layoutParams1 = SmileLatout.this.getLayoutParams();
        layoutParams1.height = isExpentEmoji ? Utils.covertDp2Px(mContext, 50) : Utils.covertDp2Px(mContext, 150);
        SmileLatout.this.setLayoutParams(layoutParams1);
    }

    public onEmojiClickListenering onEmojiClickListenering;

    public interface onEmojiClickListenering {
        void onEmojiClick(int imgRes);
    }

    public void setOnEmojiClickListenering(onEmojiClickListenering mOnEmojiClickListenering) {
        this.onEmojiClickListenering = mOnEmojiClickListenering;
    }
}
