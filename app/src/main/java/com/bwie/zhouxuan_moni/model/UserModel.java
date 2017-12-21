package com.bwie.zhouxuan_moni.model;

import android.text.AndroidCharacter;

import com.bwie.zhouxuan_moni.api.ApiService;
import com.bwie.zhouxuan_moni.bean.NewsBean;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dream on 2017/12/20.
 */

public class UserModel implements Imodel {

    OnFinish onFinish;

    public UserModel(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public interface OnFinish{
        void OnFinished(NewsBean newsBean);
    }
    @Override
    public void getUrl(String url) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<NewsBean> url1 = apiService.getUrl();
         url1.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {

                        onFinish.OnFinished(newsBean);
                    }
                });
    }
}
