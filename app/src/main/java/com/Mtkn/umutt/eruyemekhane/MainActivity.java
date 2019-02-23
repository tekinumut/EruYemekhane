package com.Mtkn.umutt.eruyemekhane;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.Mtkn.umutt.eruyemekhane.abstracts.ConnectivityStatus;
import com.Mtkn.umutt.eruyemekhane.fragments.Tab1Ogrenci;
import com.Mtkn.umutt.eruyemekhane.fragments.Tab2Personel;
import com.Mtkn.umutt.eruyemekhane.fragments.Tab3About;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager =findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setOffscreenPageLimit(3);

        if(!ConnectivityStatus.isConnected(this))
        {
            Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinatorLayout),
                    "İnternete bağlanılamadı. Verileriniz güncel olmayabilir.",3000);
            snackbar.show();
        }
    }

     class SectionsPagerAdapter extends FragmentPagerAdapter {

       private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    return new Tab1Ogrenci();
                case 1:
                    return new Tab2Personel();
                case 2:
                    return new Tab3About();
                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {

            return 3;
        }
    }

}
