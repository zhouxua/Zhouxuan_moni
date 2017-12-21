package com.bwie.zhouxuan_moni.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.zhouxuan_moni.R;
import com.bwie.zhouxuan_moni.XiangqingActivity;
import com.bwie.zhouxuan_moni.bean.NewsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Function;

/**
 * Created by dream on 2017/12/20.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.staggerView> {
    private MyOnItemClickListener itemClickListener;
    private List<NewsBean.RetBean.ListBean> list;
    private Context context;
    public void setOnItemClickListener(MyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public HomeAdapter(Context context, List<NewsBean.RetBean.ListBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public staggerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sim, parent, false);
        staggerView staggerView = new staggerView(view);
        return staggerView;
    }

    @Override
    public void onBindViewHolder(final staggerView holder, int position) {

        holder.title.setText(list.get(4).getChildList().get(position).getTitle());
        holder.img.setImageURI(list.get(4).getChildList().get(position).getPic());
        this.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(context, XiangqingActivity.class);
                context.startActivity(intent);
            }
        });
 /*自定义item的点击事件不为null，设置监听事件*/
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }





    }

    @Override
    public int getItemCount() {
        return list.get(4).getChildList().size();
    }

    public static class staggerView extends RecyclerView.ViewHolder {

        TextView title;
        SimpleDraweeView img;


        public staggerView(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            img = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
        }
    }
}
