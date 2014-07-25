package com.juhui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.pulltorefresh.PullToRefreshListView;
import com.beanu.arad.support.loadmore.LoadMoreFragment;
import com.juhui.R;
import com.juhui.dao.MainListDao;
import com.juhui.ui.adapter.MainListAdapter;

/**
 * 首页
 * Created by beanu on 14-7-24.
 */
public class MainFragment extends LoadMoreFragment implements INetResult {

    private static String TYPEID = "typeId";
    private static String POSITION = "position";

    private ProgressBar progressBar;
    private TextView noinfo_txt;

    private PullToRefreshListView listView;
    private MainListAdapter mAdapter;

    private MainListDao dao;


    public static MainFragment newInstance(String typeId, int position) {
        Bundle args = new Bundle();
        args.putString(TYPEID, typeId);
        args.putInt(POSITION, position);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new MainListDao(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        noinfo_txt = (TextView) view.findViewById(R.id.main_no_info);
        listView = (PullToRefreshListView) view.findViewById(R.id.main_listview);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MainListAdapter(getActivity(), dao.getECardList(), this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ShopDetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item", dao.getECardList().get(position - 1));
                intent.putExtras(b);
                startActivity(intent);

            }
        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                if (!isSelector)
//                    dao.pulltorefresh(new IDataListener<String>() {
//
//                        @Override
//                        public void onSuccess(String result) {
//                            mAdapter.notifyDataSetChanged();
//                            listView.onRefreshComplete();
//                            showContent(true);
//                        }
//
//                        @Override
//                        public void onFailure(String result, Throwable t, String strMsg) {
//                            listView.onRefreshComplete();
//                            showContent(true);
//                            noinfo_txt.setVisibility(View.VISIBLE);
//                        }
//                    });

            }
        });

        listView.setOnScrollListener(this);
//        if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
//            listView.setRefreshing(false);
//            showContent(false);
//        } else {
//            showContent(true);
//            if (dao.getECardList().size() == 0)
//                noinfo_txt.setVisibility(View.VISIBLE);
//        }
    }

    //*********网路请求处理************
    @Override
    public void onSuccess(int requestCode) {

    }

    @Override
    public void onFaild(String errorNo, String errorMessage) {

    }


    //*********加载更多************

    @Override
    protected void loadMore() {

    }

    @Override
    protected BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected boolean hasMore() {
        return false;
    }

    @Override
    protected boolean isError() {
        return false;
    }

}
