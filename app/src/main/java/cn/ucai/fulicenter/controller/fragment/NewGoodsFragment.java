package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    @BindView(R.id.tvRefresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    public NewGoodsFragment() {
        // Required empty public constructor
    }

    static final int ACTION_DOWNLOAD = 0;
    static final int ACTION_PULL_UP = 1;
    static final int ACTION_PULL_DOWN = 2;
    GridLayoutManager gm;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList = new ArrayList<>();
    IModelNewGoods model;
    int pageId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModelNewGoods();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                int lastPosition = gm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastPosition == mAdapter.getItemCount() - 1)
                    ;
                pageId++;
                downloadList(ACTION_PULL_UP, pageId);
            }
        });
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadList(ACTION_PULL_DOWN, pageId);
            }
        });
    }

    private void initData() {
        pageId = 1;
        downloadList(ACTION_DOWNLOAD, pageId);
    }

    private void downloadList(final int action, int PageId) {
        model.downData(getContext(), I.CAT_ID, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mAdapter.setMore(result != null && result.length > 0);
                if (!mAdapter.isMore()) {
                    if (action == ACTION_PULL_UP) {
                        mAdapter.setFooter("没有更多数据了...");
                    }
                    return;
                }
                mAdapter.setFooter("加载更多数据...");
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                switch (action) {
                    case ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_DOWN:
                        mSrl.setRefreshing(false);
                        mTvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue)
        );
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        mRv.setLayoutManager(gm);
        mRv.setHasFixedSize(true);
        mAdapter = new GoodsAdapter(getContext(), mList);
        mRv.setAdapter(mAdapter);
    }
}
