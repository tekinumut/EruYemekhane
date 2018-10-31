package com.Mtkn.umutt.eruyemekhane.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.Mtkn.umutt.eruyemekhane.R;
import com.Mtkn.umutt.eruyemekhane.abstracts.ConnectivityStatus;
import com.Mtkn.umutt.eruyemekhane.fragments.Tab1Ogrenci;
import com.Mtkn.umutt.eruyemekhane.fragments.Tab2Personel;

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

        if(!ConnectivityStatus.isConnected(this))
        {
            Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinatorLayout),
                    "İnternete bağlanılamadı. Verileriniz güncel olmayabilir.",3000);
            snackbar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuSettings:
               startActivity(new Intent(this,SettingsActivity.class));
               finish();
               // finish();
                break;
            case R.id.menuAbout:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {

            return 2;
        }
    }

}
