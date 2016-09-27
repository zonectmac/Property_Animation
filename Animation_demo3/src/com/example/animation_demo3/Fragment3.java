package com.example.animation_demo3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 微软开机加载动画
 * 
 * @author Administrator
 * 
 */
public class Fragment3 extends Fragment {
	private LinearLayout mPoint_1;
	private LinearLayout mPoint_2;
	private LinearLayout mPoint_3;
	private LinearLayout mPoint_4;
	private Button start_ani;

	public static Fragment3 newInstance() {
		return new Fragment3();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_3, null);
		mPoint_1 = (LinearLayout) view.findViewById(R.id.ll_point_circle_1);
		mPoint_2 = (LinearLayout) view.findViewById(R.id.ll_point_circle_2);
		mPoint_3 = (LinearLayout) view.findViewById(R.id.ll_point_circle_3);
		mPoint_4 = (LinearLayout) view.findViewById(R.id.ll_point_circle_4);

		start_ani = (Button) view.findViewById(R.id.start_ani_2);
		start_ani.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				h.sendEmptyMessage(0);
			}
		});
		return view;
	}

	private Handler h = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				beginPropertyAni();
				h.sendEmptyMessageDelayed(0, 2000);
				break;

			default:
				break;
			}

		};
	};

	/**
	 * 开启动画
	 */
	private void beginPropertyAni() {
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(mPoint_1, "rotation",
				0, 360);
		animator1.setDuration(2000);
		animator1.setInterpolator(new AccelerateDecelerateInterpolator());

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mPoint_2, "rotation",
				0, 360);
		animator2.setStartDelay(150);
		animator2.setDuration(2000 + 150);
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());

		ObjectAnimator animator3 = ObjectAnimator.ofFloat(mPoint_3, "rotation",
				0, 360);
		animator3.setStartDelay(150 * 2);
		animator3.setDuration(2000 + 150 * 2);
		animator3.setInterpolator(new AccelerateDecelerateInterpolator());

		ObjectAnimator animator4 = ObjectAnimator.ofFloat(mPoint_4, "rotation",
				0, 360);
		animator4.setStartDelay(150 * 3);
		animator4.setDuration(2000 + 150 * 3);
		animator4.setInterpolator(new AccelerateDecelerateInterpolator());

		AnimatorSet set = new AnimatorSet();

		set.play(animator1).with(animator2).with(animator3).with(animator4);
		set.start();
	}
}
