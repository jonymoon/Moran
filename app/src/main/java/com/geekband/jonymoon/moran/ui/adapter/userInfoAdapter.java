package com.geekband.jonymoon.moran.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.geekband.jonymoon.moran.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonly on 2015/11/23 0023.
 */
public class userInfoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserInfo> mUserInfo=new ArrayList<>();

    public userInfoAdapter(Context mContext,List<UserInfo>mUserInfo){
        this.mContext=mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.mUserInfo=mUserInfo;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
