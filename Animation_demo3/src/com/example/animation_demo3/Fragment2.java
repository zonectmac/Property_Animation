package com.example.animation_demo3;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 背景颜色变化
 * 
 * @author Administrator
 * 
 */
public class Fragment2 extends Fragment {

	private final int[] mColors = new int[] { 0xFFFF80AB, 0xFFFF4081,
			0xFFFF5177, 0xFFFF7997 };
	private Button btnColor;
	private int mCurrentRed = -1;
	private int mCurrentGreen = -1;
	private int mCurrentBlue = -1;

	public static Fragment2 newInstance() {
		return new Fragment2();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_2, null);
		btnColor = (Button) view.findViewById(R.id.btn_color);
		btnColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// displayResult(btnColor, "#0000ff", "#ff0000");
				startColorChangeAnim();
			}

		});
		return view;
	}

	private void displayResult(final View target, final String start,
			final String end) {
		// 创建ValueAnimator对象，实现颜色渐变
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 100f);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 获取当前动画的进度值，1~100
				float currentValue = (Float) animation.getAnimatedValue();
				Log.d("当前动画值", "current value : " + currentValue);

				// 获取动画当前时间流逝的百分比，范围在0~1之间
				float fraction = animation.getAnimatedFraction();
				// 直接调用evaluateForColor()方法，通过百分比计算出对应的颜色值
				String colorResult = evaluateForColor(fraction, start, end);
				/**
				 * 通过Color.parseColor(colorResult)解析字符串颜色值，传给ColorDrawable，
				 * 创建ColorDrawable对象
				 */
				/*
				 * LinearLayout.LayoutParams params =
				 * (LinearLayout.LayoutParams) target.getLayoutParams();
				 */
				ColorDrawable colorDrawable = new ColorDrawable(Color
						.parseColor(colorResult));
				// 把ColorDrawable对象设置为target的背景
				target.setBackground(colorDrawable);
				target.invalidate();

			}
		});
		valueAnimator.setDuration(6 * 1000);

		// 组装缩放动画
		ValueAnimator animator_1 = ObjectAnimator.ofFloat(target, "scaleX", 1f,
				0.5f);
		ValueAnimator animator_2 = ObjectAnimator.ofFloat(target, "scaleY", 1f,
				0.5f);
		ValueAnimator animator_3 = ObjectAnimator.ofFloat(target, "scaleX",
				0.5f, 1f);
		ValueAnimator animator_4 = ObjectAnimator.ofFloat(target, "scaleY",
				0.5f, 1f);
		AnimatorSet set_1 = new AnimatorSet();
		set_1.play(animator_1).with(animator_2);
		AnimatorSet set_2 = new AnimatorSet();
		set_2.play(animator_3).with(animator_4);
		AnimatorSet set_3 = new AnimatorSet();
		set_3.play(set_1).before(set_2);
		set_3.setDuration(3 * 1000);

		// 组装颜色动画和缩放动画，并启动动画
		AnimatorSet set_4 = new AnimatorSet();
		set_4.play(valueAnimator).with(set_3);
		set_4.start();
	}

	/**
	 * evaluateForColor()计算颜色值并返回
	 */
	private String evaluateForColor(float fraction, String startValue,
			String endValue) {

		String startColor = startValue;
		String endColor = endValue;
		int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
		int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
		int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
		int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
		int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
		int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

		// 初始化颜色的值
		if (mCurrentRed == -1) {
			mCurrentRed = startRed;
		}
		if (mCurrentGreen == -1) {
			mCurrentGreen = startGreen;
		}
		if (mCurrentBlue == -1) {
			mCurrentBlue = startBlue;
		}

		// 计算初始颜色和结束颜色之间的差值
		int redDiff = Math.abs(startRed - endRed);
		int greenDiff = Math.abs(startGreen - endGreen);
		int blueDiff = Math.abs(startBlue - endBlue);
		int colorDiff = redDiff + greenDiff + blueDiff;
		if (mCurrentRed != endRed) {
			mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
					fraction);
		} else if (mCurrentGreen != endGreen) {
			mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
					redDiff, fraction);
		} else if (mCurrentBlue != endBlue) {
			mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
					redDiff + greenDiff, fraction);
		}

		// 将计算出的当前颜色的值组装返回
		String currentColor = "#" + getHexString(mCurrentRed)
				+ getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
		return currentColor;
	}

	/**
	 * 根据fraction值来计算当前的颜色。
	 */
	private int getCurrentColor(int startColor, int endColor, int colorDiff,
			int offset, float fraction) {
		int currentColor;
		if (startColor > endColor) {
			currentColor = (int) (startColor - (fraction * colorDiff - offset));
			if (currentColor < endColor) {
				currentColor = endColor;
			}
		} else {
			currentColor = (int) (startColor + (fraction * colorDiff - offset));
			if (currentColor > endColor) {
				currentColor = endColor;
			}
		}
		return currentColor;
	}

	/**
	 * 将10进制颜色值转换成16进制。
	 */
	private String getHexString(int value) {
		String hexString = Integer.toHexString(value);
		if (hexString.length() == 1) {
			hexString = "0" + hexString;
		}
		return hexString;
	}

	/**
	 * 背景颜色改变
	 */
	public void startColorChangeAnim() {

		ObjectAnimator animator = ObjectAnimator.ofInt(btnColor,
				"backgroundColor", mColors);
		animator.setDuration(3000);
		animator.setEvaluator(new ArgbEvaluator());
		animator.start();
	}
}
