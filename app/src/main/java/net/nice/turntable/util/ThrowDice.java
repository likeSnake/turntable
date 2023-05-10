package net.nice.turntable.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import net.nice.turntable.R;

import java.util.Random;

public class ThrowDice {

    private static final int DURATION_THROW = 1000;
    private static final int DURATION_ROLL = 750;
    private static final int DURATION_STOP = 250;

    private final Context mContext;
    private final ViewGroup mContainer;
    private final ImageView mDiceView;
    private final Drawable[] mDiceDrawables;
    private final Handler mHandler;

    private boolean mIsAnimating;

    public ThrowDice(Context context, ViewGroup container, ImageView diceView, Drawable[] diceDrawables) {
        mContext = context;
        mContainer = container;
        mDiceView = diceView;
        mDiceDrawables = diceDrawables;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void throwDice() {
        if (mIsAnimating) {
            return;
        }
        mIsAnimating = true;

        // Create the dice view
        ImageView diceView = new ImageView(mContext);
        diceView.setImageDrawable(getRandomDrawable());
        mContainer.addView(diceView);

        // Set the initial position of the dice view
        int[] containerLocation = new int[2];
        mContainer.getLocationOnScreen(containerLocation);
        int containerWidth = mContainer.getWidth();
        int diceSize = mContext.getResources().getDimensionPixelSize(R.dimen.dice_size);
        int initialX = containerWidth / 2 - diceSize / 2;
        int initialY = containerLocation[1] + mContainer.getHeight() - diceSize;
        diceView.setX(initialX);
        diceView.setY(initialY);

        // Generate random values for distance and rotation
        Random random = new Random();
        int distance = mContext.getResources().getDimensionPixelSize(R.dimen.dice_throw_distance) + random.nextInt(200);
        int rotation = random.nextInt(360) + 720;

        // Create the throw animation set
        ObjectAnimator throwAnimator = ObjectAnimator.ofFloat(diceView, View.TRANSLATION_Y, distance);
        throwAnimator.setDuration(DURATION_THROW);
        throwAnimator.setInterpolator(new AccelerateInterpolator());

        // Create the roll animation
        RotateAnimation rollAnimation = new RotateAnimation(0, rotation, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rollAnimation.setDuration(DURATION_ROLL);
        rollAnimation.setInterpolator(new DecelerateInterpolator());
        rollAnimation.setFillAfter(true);
        diceView.startAnimation(rollAnimation);

        // Create the stop animation set
        ObjectAnimator stopAnimator1 = ObjectAnimator.ofFloat(diceView, View.TRANSLATION_Y, -distance / 2);
        stopAnimator1.setDuration(DURATION_STOP);
        stopAnimator1.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator stopAnimator2 = ObjectAnimator.ofFloat(diceView, View.ROTATION, rotation, rotation - 20, rotation + 20, rotation - 10, rotation + 10, rotation - 5, rotation + 5, rotation);
        stopAnimator2.setDuration(DURATION_STOP);
        stopAnimator2.setInterpolator(new DecelerateInterpolator());
        AnimatorSet stopAnimatorSet = new AnimatorSet();
        stopAnimatorSet.playTogether(stopAnimator1, stopAnimator2);

        // Set up the animation listener
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Remove the dice view from the container
                mContainer.removeView(diceView);
                mIsAnimating = false;
            }
        };

        // Start the animation sequence
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(throwAnimator, stopAnimatorSet);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    private Drawable getRandomDrawable() {
        Random random = new Random();
        int index = random.nextInt(mDiceDrawables.length);
        return mDiceDrawables[index];
    }
}