package com.example.animation_demo3;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * С�����䶯����ǿ��XBallsFallActivity��������С������ʱ��ѹ�⡢�ص�����
 * 
 * @author Administrator
 * 
 */
public class Fragment4 extends Fragment {
	private static Context mContext;
	static final float BALL_SIZE = 50f;// С��ֱ��
	static final float FULL_TIME = 1000;// ����ʱ��

	public static Fragment4 newInstance(Context context) {
		mContext = context;
		return new Fragment4();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_4, null);
		RelativeLayout xContainer = (RelativeLayout) view
				.findViewById(R.id.xcontainer);

		// ����Ҫ��ʾ��view���
		xContainer.addView(new XBallView(mContext));
		return view;
	}

	/**
	 * �Զ��嶯�����XBallView
	 */
	public class XBallView extends View implements
			ValueAnimator.AnimatorUpdateListener {

		public final ArrayList<XShapeHolder> balls = new ArrayList<XShapeHolder>();// ����balls�������洢XShapeHolder����

		public XBallView(Context context) {
			super(context);
			setBackgroundColor(Color.WHITE);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// ����ACTION_UP�¼�
			if (event.getAction() != MotionEvent.ACTION_DOWN
					&& event.getAction() != MotionEvent.ACTION_MOVE) {
				return false;
			}
			// ��ACTION_DOWN�¼�����������С��
			XShapeHolder newBall = addBall(event.getX(), event.getY());
			// ����С�����䶯����ʼʱY����
			float startY = newBall.getY();
			// ����С�����䶯������ʱ��Y���꣬����Ļ�߶ȼ�ȥstartY
			float endY = getHeight() - BALL_SIZE;
			// ��ȡ��Ļ�߶�
			float h = (float) getHeight();
			float eventY = event.getY();
			// ���㶯������ʱ��
			int duration = (int) (FULL_TIME * ((h - eventY) / h));

			/**
			 * ���濪ʼ����С������䣬�ŵ�ѹ�⣬���������Զ���
			 */
			// ����С�����䶯��
			ValueAnimator fallAni = ObjectAnimator.ofFloat(newBall, "y",
					startY, endY);
			// ���ö�������ʱ��
			fallAni.setDuration(duration);
			// ���ü��ٲ�ֵ��
			fallAni.setInterpolator(new AccelerateInterpolator());
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			fallAni.addUpdateListener(this);

			// ����С��ѹ�⶯��������С��x�������ư������
			ValueAnimator squashshAni1 = ObjectAnimator.ofFloat(newBall, "x",
					newBall.getX(), newBall.getX() - BALL_SIZE / 2);
			squashshAni1.setDuration(duration / 4);
			squashshAni1.setRepeatCount(1);
			squashshAni1.setRepeatMode(ValueAnimator.REVERSE);
			squashshAni1.setInterpolator(new DecelerateInterpolator());
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			squashshAni1.addUpdateListener(this);

			// ����С��ѹ�⶯��������С���ȼӱ�
			ValueAnimator squashshAni2 = ObjectAnimator
					.ofFloat(newBall, "width", newBall.getWidth(),
							newBall.getWidth() + BALL_SIZE);
			squashshAni2.setDuration(duration / 4);
			squashshAni2.setRepeatCount(1);
			squashshAni2.setRepeatMode(ValueAnimator.REVERSE);
			squashshAni2.setInterpolator(new DecelerateInterpolator());
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			squashshAni2.addUpdateListener(this);

			// ����С�����춯���� ����С���y�������ư����߶�
			ValueAnimator stretchAni1 = ObjectAnimator.ofFloat(newBall, "y",
					endY, endY + BALL_SIZE / 2);
			stretchAni1.setDuration(duration / 4);
			stretchAni1.setRepeatCount(1);
			stretchAni1.setRepeatMode(ValueAnimator.REVERSE);
			stretchAni1.setInterpolator(new DecelerateInterpolator());
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			stretchAni1.addUpdateListener(this);

			// ����С�����춯���� ����С��ĸ߶ȼ���
			ValueAnimator stretchAni2 = ObjectAnimator.ofFloat(newBall,
					"height", newBall.getHeight(), newBall.getHeight()
							- BALL_SIZE / 2);
			stretchAni2.setDuration(duration / 4);
			stretchAni2.setRepeatCount(1);
			stretchAni2.setRepeatMode(ValueAnimator.REVERSE);
			stretchAni2.setInterpolator(new DecelerateInterpolator());
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			stretchAni2.addUpdateListener(this);

			// ����С���𶯻�
			ValueAnimator bounceAni = ObjectAnimator.ofFloat(newBall, "y",
					endY, startY);
			bounceAni.setDuration(duration);
			bounceAni.setInterpolator(new DecelerateInterpolator());
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			bounceAni.addUpdateListener(this);

			// ����AnimatorSet����˳�򲥷�[���䡢ѹ��&���졢����]����
			AnimatorSet set = new AnimatorSet();
			// ��squashshAni1֮ǰ����fallAni
			set.play(fallAni).before(squashshAni1);
			/**
			 * ����С����ʱѹ�⣬����ȼӱ���x�������ƣ��߶ȼ��룬y��������
			 * ��˲���squashshAni1��ͬʱ��Ҫ����squashshAni2��stretchAni1��stretchAni2
			 */
			set.play(squashshAni1).with(squashshAni2);
			set.play(squashshAni1).with(stretchAni1);
			set.play(squashshAni1).with(stretchAni2);
			// ��stretchAni2֮�󲥷�bounceAni
			set.play(bounceAni).after(stretchAni2);

			// newBall����Ľ�������������alpha����ֵ1--->0
			ObjectAnimator fadeAni = ObjectAnimator.ofFloat(newBall, "alpha",
					1f, 0f);
			// ���ö�������ʱ��
			fadeAni.setDuration(250);
			// ���addUpdateListener����������ValueAnimator����ֵ�ı�ʱ�ἤ���¼���������
			fadeAni.addUpdateListener(this);

			// ΪfadeAni���ü���
			fadeAni.addListener(new AnimatorListenerAdapter() {
				// ��������
				@Override
				public void onAnimationEnd(Animator animation) {
					// ��������ʱ���ö���������ShapeHolderɾ��
					balls.remove(((ObjectAnimator) (animation)).getTarget());
				}
			});

			// �ٴζ���һ��AnimatorSet�������ϣ�����϶���
			AnimatorSet aniSet = new AnimatorSet();
			// ָ����fadeAni֮ǰ����set��������
			aniSet.play(set).before(fadeAni);

			// ��ʼ���Ŷ���
			aniSet.start();

			return true;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			for (XShapeHolder xShapeHolder : balls) {
				canvas.save();
				canvas.translate(xShapeHolder.getX(), xShapeHolder.getY());
				xShapeHolder.getShape().draw(canvas);
				canvas.restore();
			}
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			// ָ���ػ����
			this.invalidate();
		}

		/**
		 * addBall()��������XShapeHolder����ShapeHolder�������С��
		 */
		private XShapeHolder addBall(float x, float y) {
			// ����һ����Բ
			OvalShape circle = new OvalShape();
			// ������Բ���
			circle.resize(BALL_SIZE, BALL_SIZE);
			// ����Բ��װ��Drawable����
			ShapeDrawable drawble = new ShapeDrawable(circle);
			// ����XShapeHolder����
			XShapeHolder holder = new XShapeHolder(drawble);
			// ����holder����
			holder.setX(x - BALL_SIZE / 2);
			holder.setY(y - BALL_SIZE / 2);

			// ���������ϵ�ARGB��ɫ
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			// ��red��green��blue������ɫ�������ϳ�ARGB��ɫ
			int color = Color.rgb(red, green, blue);
			// ��red��green��blue������ɫ���������4�õ���ֵ��ϳ�ARGB��ɫ
			int darkColor = Color.rgb(red, green, blue);

			// ����Բ�ν���Ч��
			RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
					BALL_SIZE, color, darkColor, Shader.TileMode.CLAMP);

			// ��ȡdrawble�����Ļ���
			Paint paint = drawble.getPaint();
			paint.setShader(gradient);

			// ΪXShapeHolder�������û���
			holder.setPaint(paint);
			balls.add(holder);
			return holder;
		}
	}
}
