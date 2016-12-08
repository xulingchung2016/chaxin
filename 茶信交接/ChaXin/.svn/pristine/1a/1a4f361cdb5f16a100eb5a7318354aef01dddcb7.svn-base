package com.newbrain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newbrain.chaxin.teazhongchou.TeaZhongChouPoolFragment;

public class MyAdapter_ZhongChouPoolFragment extends FragmentPagerAdapter{
    protected  String[] pagerTitles;
    protected static int[] moneys = {3,5,2};
    private Fragment[] mItems;

    private int mCount = 0;

    public MyAdapter_ZhongChouPoolFragment(FragmentManager fm) {
        super(fm);
    }

    public MyAdapter_ZhongChouPoolFragment(FragmentManager fm,String[] titles) {
        super(fm);
        pagerTitles = titles;
        
        mCount = pagerTitles.length;
    }
    
    @Override
    public Fragment getItem(int position) {
    	if(mItems == null){
    		mItems = new Fragment[getCount()];
    	}
    	if(mItems[position] == null){
    		mItems[position] = new TeaZhongChouPoolFragment(pagerTitles[position % pagerTitles.length],moneys[position % pagerTitles.length]);
    	}
    	return mItems[position];
    }
    
    @Override
    public int getCount() {
    	
        return mCount;
    }

    
    @Override
    public CharSequence getPageTitle(int position) {
      return pagerTitles[position % pagerTitles.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}