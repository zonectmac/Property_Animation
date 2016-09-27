package com.example.animation_demo3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private Fragment[] fragment = new Fragment[] { Fragment1.newInstance(this),
			Fragment2.newInstance(), Fragment3.newInstance(),
			Fragment4.newInstance(this) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
				fragment));
	}

	private static class PagerAdapter extends FragmentStatePagerAdapter {
		private Fragment[] fragments;

		public PagerAdapter(FragmentManager fm, Fragment[] fragment) {
			super(fm);
			this.fragments = fragment;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments[arg0];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.length;
		}

	}
}
