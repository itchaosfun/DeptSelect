package com.cn.chaos.pcddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 2016/8/21.
 */
public class DeptAdapter extends BaseAdapter {

    private  Context mContext;
    private  List<DeptBean> mDatas = new ArrayList<>();

    public DeptAdapter(Context context, List<DeptBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public DeptBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_listview, null);//该方法会导致布局文件的第一层布局的布局参数失效
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview, parent, false);//该方法会导致布局文件的第一层布局的布局参数有效
        }
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        DeptBean areaBean = mDatas.get(position);
        if (areaBean.isCheck) {
            convertView.setBackgroundResource(R.drawable.choose_item_selected);
        } else {
            convertView.setBackgroundResource(android.R.color.transparent);
        }
        tv_name.setText(areaBean.name);
        return convertView;
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }
}
