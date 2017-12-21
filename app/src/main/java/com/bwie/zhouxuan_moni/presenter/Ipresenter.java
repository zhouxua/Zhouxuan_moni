package com.bwie.zhouxuan_moni.presenter;

import com.bwie.zhouxuan_moni.bean.NewsBean;
import com.bwie.zhouxuan_moni.model.UserModel;
import com.bwie.zhouxuan_moni.view.Iview;

/**
 * Created by dream on 2017/12/20.
 */

public class Ipresenter implements UserModel.OnFinish{
    public final Iview iview;
    public final UserModel userModel;

    public Ipresenter(Iview iview) {
        this.iview = iview;
        userModel = new UserModel(this);
    }

    public void getUrl(String url){

        userModel.getUrl(url);
    }

    public void OnFinished(NewsBean newsBean) {
        iview.getData(newsBean);
    }
}
