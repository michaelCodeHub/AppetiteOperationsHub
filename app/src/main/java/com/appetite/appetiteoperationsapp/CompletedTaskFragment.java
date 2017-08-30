package com.appetite.appetiteoperationsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appetite.appetiteoperationsapp.Database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */

public class CompletedTaskFragment extends Fragment {

    String trackID = "0";
    boolean loading = true;
    boolean referesh = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    RelativeLayout overLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBaar;

    LinearLayoutManager mLayoutManager;

    SwipeRefreshLayout swipeRefreshLayout;

    private static final int ITEM_COUNT = 10;

    private List<TaskInfo> mContentItems = new ArrayList<>();
    private View.OnClickListener listner;

    int position;

    private TextView noitems;

    public static CompletedTaskFragment newInstance() {
        return new CompletedTaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBaar = (ProgressBar) view.findViewById(R.id.progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUI();
            }
        });

        swipeRefreshLayout.setEnabled(false);

        overLayout = (RelativeLayout) view.findViewById(R.id.overLayout);
        overLayout.setVisibility(View.GONE);
        overLayout.setOnClickListener(listner);

        mAdapter = new TaskAdapter(getActivity(), mContentItems);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                if (dy > 0) //check for scroll down
//                {
//                    visibleItemCount = mLayoutManager.getChildCount();
//                    totalItemCount = mLayoutManager.getItemCount();
//                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
//                    if (loading && referesh) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount / 2) {
//                            referesh = false;
////                            setProgress();
//                            updateUI();
//                        }
//                    }
//                }
//            }
//        });

        noitems = (TextView) view.findViewById(R.id.textView19);
        new Sansation().overrideFonts(getActivity(), view.findViewById(R.id.textView19));

        updateUI();
    }

    public void updateUI()
    {
        Database database = new Database(getActivity());
        database.open();
        Cursor c = database.getCompletedTasks();

        if (c.getCount() > 0)
        {
            while (c.moveToNext()) {
                mContentItems.add(new TaskInfo(c.getString(1), c.getString(2), c.getString(11) , c.getString(12), c.getString(13),
                        c.getString(14) , c.getString(7) ,  c.getString(4),  c.getString(5),  c.getString(6),  c.getString(3)));
                mAdapter.notifyDataSetChanged();
            }
            removeProgress();
            referesh = true;
            loading = true;
        }
        c.close();
        if(mContentItems.size()<=0)
        {
            noitems.setVisibility(View.VISIBLE);
        }
        else
        {
            noitems.setVisibility(View.GONE);
        }
        database.close();
    }

    public void setProgress()
    {
        progressBaar.setVisibility(View.VISIBLE);
    }

    public void removeProgress()
    {
        progressBaar.setVisibility(View.GONE);
    }
}
