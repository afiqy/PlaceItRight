package my.edu.unikl.placeitright;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    int mNoOfTabs;
    public PagerAdapter(FragmentManager fm, int NoOfTabs){
        super(fm);
        this.mNoOfTabs = NoOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                OfferTab offers = new OfferTab();
                return offers;
            case 1:
                NewProductTab newproduct = new NewProductTab();
                return newproduct;
            case 2:
                PopularTab popular = new PopularTab();
                return popular;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
