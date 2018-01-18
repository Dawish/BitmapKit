package com.danxx;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danxx.databinding.ActivitySnapRecyclerBinding;
import com.danxx.view.ShadowDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danxx on 2018/1/15.
 */

public class ActivitySnapRecycler extends AppCompatActivity {

    private List<String> urlList;

    RecyclerView recyclerView;
    LinearSnapHelper linearSnapHelper;
    ActivitySnapRecyclerBinding snapRecyclerBinding;
    TangweiAdapter tangweiAdapter;

    View selectedView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        snapRecyclerBinding =
        DataBindingUtil.setContentView(ActivitySnapRecycler.this, R.layout.activity_snap_recycler);

        urlList = new ArrayList<>(
                Arrays.asList(
                        getResources().getString(R.string.tangwei01),
                        getResources().getString(R.string.tangwei02),
                        getResources().getString(R.string.tangwei03),
                        getResources().getString(R.string.tangwei04),
                        getResources().getString(R.string.tangwei05),
                        getResources().getString(R.string.tangwei06)
                )
        );

        tangweiAdapter = new TangweiAdapter();
        tangweiAdapter.setData(urlList);

        linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(snapRecyclerBinding.recyclerView);

        snapRecyclerBinding.recyclerView.setAdapter(tangweiAdapter);

        snapRecyclerBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = linearSnapHelper.findSnapView(snapRecyclerBinding.recyclerView.getLayoutManager());
                    if(view!=null){
                        if(selectedView!=null){
                            if(selectedView.equals(view)){
                                if(!selectedView.isEnabled()){
                                    selectedView.setSelected(true);
                                }
                            }else {
                                selectedView.setSelected(false);
                                view.setSelected(true);
                                selectedView = view;
                            }
                        }else {
                            selectedView = view;
                            selectedView.setSelected(true);
                        }
                    }
                }
            }
        });

        snapRecyclerBinding.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(snapRecyclerBinding.recyclerView.getChildCount()>0){
                    View firstView = snapRecyclerBinding.recyclerView.getChildAt(0);
                    if(firstView!=null){
                        selectedView = firstView;
                        firstView.setSelected(true);
                    }
                }
            }
        },1000);
    }

    static class TangweiAdapter extends RecyclerView.Adapter<TangweiViewHolder>{

        List<String> url = new ArrayList<>();

        public void setData(List<String> data){
            this.url = data;
        }

        @Override
        public TangweiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item,parent,false);
            return new TangweiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TangweiViewHolder holder, int position) {
            holder.draweeView.setImageURI(url.get(position));
        }

        @Override
        public int getItemCount() {
            return url == null ? 0 : url.size();
        }
    }

    static class TangweiViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        public TangweiViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView;
        }
    }

}
