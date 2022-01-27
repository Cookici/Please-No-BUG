package com.example.mytext;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;



public class FragmentTwo extends Fragment {
    private RecyclerView recyclerView;
    private SmartRefreshLayout srfresh;
    private Handler mHandler;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initData() {


        }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_two, container, false);



        ArrayList<GoodsEntity> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setContent("内容" + i);
            goodsEntity.setTitle(i + "");
            data.add(goodsEntity);
        }

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            MyAdapter myAdapter = new MyAdapter(data);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


            srfresh.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
            srfresh.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));

            srfresh.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    //展示延时2秒
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            initData();
                            myAdapter.refresh(data);//刷新加载数据
                            refreshlayout.finishRefresh();
                        }
                    }, 2000);

                }
            });

            srfresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(final RefreshLayout refreshlayout) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            initData();
                            myAdapter.refresh(data);
                            refreshlayout.finishLoadMore();
                        }
                    }, 2000);

                }
            });
            srfresh.setEnableLoadMore(true);
            srfresh.autoRefresh();


            return view;
        }

    }
