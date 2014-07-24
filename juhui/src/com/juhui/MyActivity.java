package com.juhui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.juhui.ui.MainFragment;
import com.juhui.ui.LeftMenuFragment;
import com.juhui.ui.RightMenuFragment;

import java.util.HashMap;

public class MyActivity extends SlidingFragmentActivity {

    private SlidingMenu sm;
    private FragmentsManager fragmentManager;


    public enum Fragments {
        coupons, ecard, share
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT_RIGHT);
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadowleft);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_launcher);
        getActionBar().setDisplayShowTitleEnabled(false);
        // buildCustomActionBarTitle();

        setBehindContentView(R.layout.menu_left_fragment);

        sm.setSecondaryMenu(R.layout.menu_right_fragment);
        sm.setSecondaryShadowDrawable(R.drawable.shadowright);


        // set the Content View
        fragmentManager = new FragmentsManager(this, R.id.content_fragment);
        fragmentManager.addFragment(Fragments.coupons.name(), MainFragment.class, null);
        fragmentManager.addFragment(Fragments.ecard.name(), MainFragment.class, null);
        fragmentManager.addFragment(Fragments.share.name(), MainFragment.class, null);

        // fragmentManager.addFragment(Fragments.mycard.name(),
        // EntityCardFragment.class, null);
        // fragmentManager.addFragment(Fragments.freshNews.name(),
        // FreshNewsListFragment.class, null);

        // set the Left View
        FragmentTransaction secondFragmentTransaction = getSupportFragmentManager().beginTransaction();
        secondFragmentTransaction.replace(R.id.menu_left, getLeftMenuFragment(), LeftMenuFragment.class.getName());

        // set the right view
        secondFragmentTransaction.replace(R.id.menu_right, getRightMenuFragment(),
                RightMenuFragment.class.getName());
        secondFragmentTransaction.commit();

        // getSlidingMenu().showContent();
        showFragment(Fragments.coupons);
    }

    public LeftMenuFragment getLeftMenuFragment() {
        LeftMenuFragment fragment = ((LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(
                LeftMenuFragment.class.getName()));
        if (fragment == null) {
            fragment = new LeftMenuFragment();
        }
        return fragment;
    }

    public RightMenuFragment getRightMenuFragment() {
        RightMenuFragment fragment = ((RightMenuFragment) getSupportFragmentManager().findFragmentByTag(
                RightMenuFragment.class.getName()));
        if (fragment == null) {
            fragment = new RightMenuFragment();
        }
        return fragment;
    }

    public void showFragment(Fragments fm) {
        switch (fm) {
            case coupons:
                fragmentManager.changFragment(Fragments.coupons.name());
//                showMenu(false, true);
                break;
            case ecard:
                fragmentManager.changFragment(Fragments.ecard.name());
//                showMenu(false, true);
                break;
            case share:
                fragmentManager.changFragment(Fragments.share.name());
//                showMenu(false, true);
                break;
            // case mycard:
            // fragmentManager.changFragment(Fragments.mycard.name());
            // showMenu(true, false);
            // break;
            // case freshNews:
            // fragmentManager.changFragment(Fragments.freshNews.name());
            // showMenu(false, true);
            // break;
        }
        sm.showContent();
    }


    /**
     * Fragment的管理
     *
     * @author beanu
     */
    public static class FragmentsManager {
        private final FragmentActivity mActivity;
        // private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, FragmentInfo> mTabs = new HashMap<String, FragmentInfo>();
        FragmentInfo mLastFragment;

        static final class FragmentInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            FragmentInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        /**
         * @param activity    依附的主Activity
         * @param containerId 填充Fragment的id
         */
        public FragmentsManager(FragmentActivity activity, int containerId) {
            mActivity = activity;
            mContainerId = containerId;
        }

        public void addFragment(String tag, Class<?> clss, Bundle args) {

            FragmentInfo info = new FragmentInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state. If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
        }

        public void changFragment(String tag) {

            FragmentInfo newTab = mTabs.get(tag);
            if (mLastFragment != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastFragment != null) {
                    if (mLastFragment.fragment != null) {
                        ft.detach(mLastFragment.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastFragment = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();

            }

        }
    }
}
