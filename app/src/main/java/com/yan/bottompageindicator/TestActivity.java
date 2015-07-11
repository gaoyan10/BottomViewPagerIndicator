package com.yan.bottompageindicator;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yan.view.CustomBottomPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private CustomBottomPageIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        ArrayList mListImg = new ArrayList<View>();
        mListImg.add(getImageView(R.drawable.bg_page1, ImageView.ScaleType.CENTER_CROP));
        mListImg.add(getImageView(R.drawable.bg_page2, ImageView.ScaleType.CENTER_CROP));
        mListImg.add(getImageView(R.drawable.bg_page1, ImageView.ScaleType.CENTER_CROP));
        mListImg.add(getImageView(R.drawable.bg_page4, ImageView.ScaleType.CENTER_CROP));
        XTBasePagerAdapter mPagerAdapter = new XTBasePagerAdapter(mListImg);
        mViewPager.setAdapter(mPagerAdapter);

        indicator = (CustomBottomPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private View getImageView(int id, ImageView.ScaleType type) {
        ImageView imgView = new ImageView(this);
        imgView.setImageResource(id);
        // iv.setImageResource(id);

        if (type != null) {
            imgView.setScaleType(type);
        }
        return imgView;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class XTBasePagerAdapter extends PagerAdapter {

        protected List<View> listData;

        public XTBasePagerAdapter(List<View> list) {
            this.listData = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return (this.listData == null) ? 0 : this.listData.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            container.addView(this.listData.get(position));
            return this.listData.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub

            container.removeView(this.listData.get(position));
        }

    }

}
