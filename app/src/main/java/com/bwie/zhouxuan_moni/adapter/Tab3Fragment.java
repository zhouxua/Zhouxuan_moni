package com.bwie.zhouxuan_moni.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.zhouxuan_moni.R;

/**
 * Created by dream on 2017/12/21.
 */

public class Tab3Fragment extends Fragment {
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablelayout_item1,null);
        textView = view.findViewById(R.id.jieshao);

        return view;
    }
}
