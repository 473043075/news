package com.example.a13797.gznews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a13797.gznews.R;
import com.example.a13797.gznews.activity.ActivitySearch;
import com.example.a13797.gznews.entity.News;
import com.example.a13797.gznews.method.HomeRecyclerAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    private TabLayout tabLayout = null;
    private ViewPager vp_pager;
    private LinearLayout searchView;
    private Fragment[] mFragmentArrays = new Fragment[10];
    private String[] mTabTitles = new String[10];
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter homeRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);
        vp_pager = (ViewPager) getActivity().findViewById(R.id.tab_viewpager);
        searchView =(LinearLayout) getActivity().findViewById(R.id.homr_layout_search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ActivitySearch.class);
                startActivity(intent);
            }
        });

        initView();
        }
    private void refreshNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();
    }



    private void initView() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(vp_pager);
        mTabTitles[0] = "推荐";
        mTabTitles[1] = "热点";
        mTabTitles[2] = "科技";
        mTabTitles[3] = "体育";
        mTabTitles[4] = "健康";
        mTabTitles[5] = "推荐";
        mTabTitles[6] = "热点";
        mTabTitles[7] = "科技";
        mTabTitles[8] = "体育";
        mTabTitles[9] = "健康";

        mFragmentArrays[0] = TabFragment.newInstance();
        mFragmentArrays[1] = TabFragment.newInstance();
        mFragmentArrays[2] = TabFragment.newInstance();
        mFragmentArrays[3] = TabFragment.newInstance();
        mFragmentArrays[4] = TabFragment.newInstance();
        mFragmentArrays[5] = TabFragment.newInstance();
        mFragmentArrays[6] = TabFragment.newInstance();
        mFragmentArrays[8] = TabFragment.newInstance();
        mFragmentArrays[7] = TabFragment.newInstance();
        mFragmentArrays[9] = TabFragment.newInstance();
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        vp_pager.setAdapter(pagerAdapter);


        RefreshLayout refreshLayout = (RefreshLayout)getActivity().findViewById(R.id.home_smarte_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });





    }
    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }

    public void getBeautyDetail() {                                         //爬取数据添加到newsList数组
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://news.sina.com.cn/").get();
                    Elements content = document.getElementsByClass("p_middle");
                    Elements links = content.select("a");
                    for (Element link : links) {
                        News news = new News(link.text(),link.attr("href"));
                        newsList.add(news);
                        Log.d("网页",""+link.text());
                        Log.d("网页",""+link.attr("href"));
                    }
                    showWeb();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showWeb(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(homeRecyclerAdapter);

            }
        });
    }






}
