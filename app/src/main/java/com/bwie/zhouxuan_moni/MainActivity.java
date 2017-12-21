package com.bwie.zhouxuan_moni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bwie.zhouxuan_moni.adapter.Adapter;
import com.bwie.zhouxuan_moni.bean.NewsBean;
import com.bwie.zhouxuan_moni.presenter.Ipresenter;
import com.bwie.zhouxuan_moni.view.Iview;

public class MainActivity extends AppCompatActivity implements Iview{
    Ipresenter ipresenter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipresenter = new Ipresenter(this);
        ipresenter.getUrl("http://api.svipmovie.com");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void getData(NewsBean newsBean) {
        NewsBean.RetBean ret = newsBean.getRet();
        Adapter adapter = new Adapter(MainActivity.this, newsBean.getRet().getList());
      recyclerView.setAdapter(adapter);
    }
}
