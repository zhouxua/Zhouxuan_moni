package com.bwie.zhouxuan_moni.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.zhouxuan_moni.R;
import com.bwie.zhouxuan_moni.XiangqingActivity;
import com.bwie.zhouxuan_moni.api.ApiService;
import com.bwie.zhouxuan_moni.bean.XiangqingBean;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dream on 2017/12/21.
 */

public class Tab2Fragment extends Fragment {
    String url="";
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void ononMoonStickyEvent(MessageEvent messageEvent){
        url = messageEvent.getMessage();
        System.out.println("视频路径："+url);
    }
    TextView jieshao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.tablelayout_item1,null);
   jieshao = view.findViewById(R.id.jieshao);
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://api.svipmovie.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<XiangqingBean> url1 = apiService.getXiangQing(url);
        url1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<XiangqingBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(XiangqingBean xiangqingBean) {
                        String description = xiangqingBean.getRet().getDescription();
                        jieshao.setText(description);
                    }
                });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}