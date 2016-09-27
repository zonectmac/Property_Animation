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
 * ���Զ���PropertyAni
 * 
 * ���õ����Զ���������ֵ�У� - translationX��translationY----����view������������Ͻ�������X��Y����ƫ�Ƶľ��� -
 * rotation��rotationX��rotationY----����view������֧�����2D��3D��ת -
 * scaleX��scaleY----����view������֧�����2D���� -
 * pivotX��pivotY----����view�����֧��λ�ã����λ��һ�����view��������ĵ㡣Χ�����֧����Խ�����ת�����Ŵ��� -
 * x��y----����view�����������е�����λ�ã�����������Ͻ������translationX��translationYֵ���ۼƺ� -
 * alpha----��ʾview�����͸���ȡ�Ĭ��ֵ��1(��ȫ͸��)��0(��͸��)
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
	// ImageView���󼯺�
	private ArrayList<ImageView> mImViews = new ArrayList<ImageView>();
	private boolean flag = true;// �����������رն����ı��λ

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
		// ����ObjectAnimator���Զ��󣬲����ֱ��Ƕ���Ҫ���õ�View���󡢶������ԡ�����ֵ
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
		aniSet.setInterpolator(new BounceInterpolator());// ����Ч���Ĳ�ֵ��
		aniSet.playTogether(animator0, animator1, animator2, animator3,
				animator4);// ͬʱ����5������
		aniSet.start();

		// ���ñ��λ
		flag = true;

	}

	private void startAnim() {
		// ����ObjectAnimator���Զ��󣬲����ֱ��Ƕ���Ҫ���õ�View���󡢶������ԡ�����ֵ
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

		// 3DЧ��
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
		aniSet.setInterpolator(new BounceInterpolator());// ����Ч���Ĳ�ֵ��
		aniSet.playTogether(animator0, animator1, animator2, animator3,
				animator4);// ͬʱ����5������
		aniSet.start();

		// ���ñ��λ
		flag = false;

	}
}
