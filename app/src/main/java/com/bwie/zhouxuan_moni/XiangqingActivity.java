package com.bwie.zhouxuan_moni;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bwie.zhouxuan_moni.adapter.MessageEvent;

import com.bwie.zhouxuan_moni.adapter.Tab2Fragment;
import com.bwie.zhouxuan_moni.adapter.Tab3Fragment;
import com.bwie.zhouxuan_moni.api.ApiService;
import com.bwie.zhouxuan_moni.bean.XiangqingBean;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XiangqingActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list;
    private MyAdapter adapter;
    private
    String[] titles = {"简介","评论"};
    String url="";
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void ononMoonStickyEvent(MessageEvent messageEvent){
        url = messageEvent.getMessage();
        System.out.println("视频路径："+url);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        EventBus.getDefault().register(this);

/*View rootView = getLayoutInflater().from(this).inflate(R.layout.simple_player_view_player, null);
        setContentView(rootView);*/
//        String url = Environment.getExternalStorageDirectory()
//                .getAbsolutePath() + "/local2/adc.mp4";
        System.out.println("视频路径："+url);

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
                        String hdurl = xiangqingBean.getRet().getHDURL();
                        new PlayerView(XiangqingActivity.this)
                                .setTitle("百度影音")
                                .setScaleType(PlayStateParams.fitparent)
                                .hideMenu(true)
                                .forbidTouch(false)
                                .setPlaySource(hdurl)
                                .startPlay();
                    }
                });


        //实例化
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
//页面，数据源
        list = new ArrayList<>();
        list.add(new Tab2Fragment());
       list.add(new Tab3Fragment());
//ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
//绑定
        tabLayout.setupWithViewPager(viewPager);
    }
    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount()
        {
            return list.size();
        }
        //重写这个方法，将设置每个Tab的标题
        @Override
        public
        CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
