package com.example.animation_demo3;

import java.util.ArrayList;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 属性动画PropertyAni
 * 
 * 常用的属性动画的属性值有： - translationX、translationY----控制view对象相对其左上角坐标在X、Y轴上偏移的距离 -
 * rotation、rotationX、rotationY----控制view对象绕支点进行2D和3D旋转 -
 * scaleX、scaleY----控制view对象绕支点进行2D缩放 -
 * pivotX、pivotY----控制view对象的支点位置，这个位置一般就是view对象的中心点。围绕这个支点可以进行旋转和缩放处理 -
 * x、y----描述view对象在容器中的最终位置，是最初的左上角坐标和translationX、translationY值的累计和 -
 * alpha----表示view对象的透明度。默认值是1(完全透明)、0(不透明)
 * 
 * Created by wondertwo on 2016/3/11.
 */
public class Fragment1 extends Fragment implements OnClickListener {
	private static Context mContext;

	public static Fragment1 newInstance(Context context) {
		mContext = context;
		return new Fragment1();
	}

	private int[] images = new int[] { R.id.iv_1, R.id.iv_2, R.id.iv_3,
			R.id.iv_4, R.id.iv_5, };
	// ImageView对象集合
	private ArrayList<ImageView> mImViews = new ArrayList<ImageView>();
	private boolean flag = true;// 启动动画、关闭动画的标记位

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_1, null);
		for (int i = 0; i < images.length; i++) {
			ImageView iv_1 = (ImageView) view.findViewById(images[i]);
			iv_1.setOnClickListener(this);
			mImViews.add(iv_1);
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_5:
			if (flag) {
				startAnim();
			} else {
				endAnim();
			}
			Toast.makeText(mContext, "" + 123, Toast.LENGTH_SHORT).show();
			break;

		default:
			// Toast.makeText(mContext, "" + v.getId(),
			// Toast.LENGTH_SHORT).show();
			break;
		}

	}

	private void endAnim() {
		// 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
		ObjectAnimator animator0 = ObjectAnimator.ofFloat(mImViews.get(0),
				"alpha", 0.5F, 1F);
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImViews.get(1),
				"translationY", 210F, 0);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImViews.get(2),
				"translationX", 170F, 0);
		ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImViews.get(3),
				"translationY", -210F, 0);
		ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImViews.get(4),
				"translationX", -170F, 0);
		AnimatorSet aniSet = new AnimatorSet();
		aniSet.setDuration(4000);
		aniSet.setInterpolator(new BounceInterpolator());// 弹跳效果的插值器
		aniSet.playTogether(animator0, animator1, animator2, animator3,
				animator4);// 同时启动5个动画
		aniSet.start();

		// 重置标记位
		flag = true;

	}

	private void startAnim() {
		// 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
		ObjectAnimator animator0 = ObjectAnimator.ofFloat(mImViews.get(0),
				"alpha", 1f, 0.5f);
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImViews.get(1),
				"translationY", 210f);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImViews.get(2),
				"translationX", 170f);
		ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImViews.get(3),
				"translationY", -210f);
		// ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImViews.get(4),
		// "translationX", -170f);

		// 3D效果
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("rotationX",
				0, 360);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("rotationY",
				0, 180);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("rotationZ",
				0, -90);
		ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(
				mImViews.get(4), pvhX, pvhY, pvhZ);

		AnimatorSet aniSet = new AnimatorSet();
		aniSet.setDuration(4000);
		aniSet.setInterpolator(new BounceInterpolator());// 弹跳效果的插值器
		aniSet.playTogether(animator0, animator1, animator2, animator3,
				animator4);// 同时启动5个动画
		aniSet.start();

		// 重置标记位
		flag = false;

	}
}
