package com.gdx.wallpaper.setting;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.fragment.CollectionListFragment;
import com.gdx.wallpaper.environment.fragment.EnvironmentListFragment;
import com.gdx.wallpaper.playlist.fragment.PlaylistListFragment;
import com.gdx.wallpaper.transition.fragment.TransitionListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    private static final String POSITION = "Position";

    private List<Fragment> pages;
    private View rootView;
    private TabLayout tabs;
    private ViewPager pager;
    private PagerAdapter adapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pages = new ArrayList<Fragment>();
        pages.add(new PlaylistListFragment());
        pages.add(new EnvironmentListFragment());
        pages.add(new TransitionListFragment());
        pages.add(new CollectionListFragment());
        adapter = new PagerAdapter(getChildFragmentManager(), pages);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabs.post(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    tabs.setupWithViewPager(pager);
                }
            }
        });
        if (savedInstanceState != null) {
            pager.setCurrentItem(savedInstanceState.getInt(POSITION));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting_main, container, false);
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (pager != null) {
            outState.putInt(POSITION, pager.getCurrentItem());
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> pages;

        public PagerAdapter(FragmentManager fragmentManager, List<Fragment> pages) {
            super(fragmentManager);

            this.pages = pages;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(((Pageable) getItem(position)).getTitle());
        }
    }
}
