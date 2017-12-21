package com.bwie.zhouxuan_moni.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.zhouxuan_moni.R;
import com.bwie.zhouxuan_moni.XiangqingActivity;
import com.bwie.zhouxuan_moni.bean.NewsBean;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

/**
 * Created by dream on 2017/12/20.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



 Context context;
 List<NewsBean.RetBean.ListBean> list;
    private ArrayList<String> images;
    private ArrayList<String> title;
    private HomeAdapter homeAdapter;

    public Adapter(Context context, List<NewsBean.RetBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    private final  int TYPE_1 = 0;
    private final  int TYPE_2 = 1;

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_1;
        }else{
            return TYPE_2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_1:
                   View view = LayoutInflater.from(context).inflate(R.layout.item_banner,null,false);
                   MyViewHolder1 holder1 = new MyViewHolder1(view);
                   return holder1;

            case  TYPE_2:
                View view1 = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
                MyViewHolder2 holder2 = new MyViewHolder2(view1);
                return holder2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_1:
                MyViewHolder1 holder1 = (MyViewHolder1) holder;
                images = new ArrayList<>();
                title = new ArrayList<>();
                for (int i=0;i<list.get(0).getChildList().size();i++){
                   images.add(list.get(0).getChildList().get(i).getPic());
                    title.add(list.get(0).getChildList().get(i).getTitle());
               }
                holder1.xBanner.setData(images,title);
                // XBanner适配数据
                holder1.xBanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, View view, int position) {
                        Glide.with(context).load(images.get(position)).into((ImageView) view);
                    }
                });
                holder1.xBanner.setPageTransformer(Transformer.Cube);    //立体旋转
                holder1.xBanner.setPageChangeDuration(1000);
                holder1.xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, int position) {
                        String dataId = list.get(0).getChildList().get(position).getDataId();
                        System.out.println("视频路径1："+list.get(0).getChildList().get(position).getDataId());
                        EventBus.getDefault().postSticky(new MessageEvent(dataId));
                        Intent intent = new Intent(context, XiangqingActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_2:
                MyViewHolder2 holder2 = (MyViewHolder2) holder;
                holder2.rv.setLayoutManager(new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.VERTICAL));
                homeAdapter = new HomeAdapter(context, list);
                holder2.rv.setAdapter(homeAdapter);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder{
       XBanner xBanner;
        public MyViewHolder1(View itemView) {
            super(itemView);
            xBanner = itemView.findViewById(R.id.banner);
        }
    }
    class MyViewHolder2 extends RecyclerView.ViewHolder{
        RecyclerView rv;
        public MyViewHolder2(View itemView) {
            super(itemView);
         rv = itemView.findViewById(R.id.rv);
        }
    }
}
