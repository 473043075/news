package com.example.a13797.gznews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13797.gznews.R;
import com.example.a13797.gznews.method.HomeRecyclerAdapter;

public class TabFragment extends android.support.v4.app.Fragment {

    public static android.support.v4.app.Fragment newInstance() {
        TabFragment fragment = new TabFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new HomeRecyclerAdapter());
        return rootView;
    }

}