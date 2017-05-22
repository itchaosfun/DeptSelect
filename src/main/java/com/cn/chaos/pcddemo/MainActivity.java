package com.cn.chaos.pcddemo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText    mEditText;
    private Button      mButton;
    private PopupWindow mPw;
    private List<DeptAdapter>            mAdapterList = new ArrayList<>();
    private Map<Integer, Integer>        mIntegerMap  = new HashMap<>();
    private String                       dept         = "";
    private Map<Integer, List<DeptBean>> mListMap     = new HashMap<>();
    private List<ListView>               mViewList    = new ArrayList<>();
    private int                          index        = 0;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

        //初始化popupwindow
        initPopupWindow();
        //初始化第一个数据
        initFirstLists();

        mAdapterList.add(new DeptAdapter(MainActivity.this, mListMap.get(0)));

        //初始化第一个listview
        initFirstListView(index);
        //设置条目点击事件
        initListener();
    }

    /**
     * 动态设置listview的监听
     */
    private void initListener() {
        for (int i = 0; i < mViewList.size(); i++) {
            ListView listView = mViewList.get(i);
            listView.setOnItemClickListener(this);
        }
    }

    /**
     * 初始化第一个listview
     *
     * @param index
     */
    private void initFirstListView(int index) {
        mViewList.get(index).setAdapter(mAdapterList.get(index));
    }

    /**
     * 初始化第一个集合数据
     */
    private void initFirstLists() {
        List<DeptBean> deptBeen = new ArrayList<>();
        int num = new Random().nextInt(8) + 3;

        for (int i = 0; i < num; i++) {
            deptBeen.add(new DeptBean(i + "", "部门" + i));
        }
        mListMap.put(0, deptBeen);
        mIntegerMap.put(index, -1);
    }

    /**
     * 初始化popupwindow
     */
    private void initPopupWindow() {
        View contentView = View.inflate(MainActivity.this, R.layout.view_pw, null);
        mPw = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPw.setBackgroundDrawable(new BitmapDrawable());

        mLinearLayout = (LinearLayout) contentView.findViewById(R.id.ll_popup);

        addListView(index);
    }

    /**
     * 动态添加listview
     *
     * @param index
     */
    private void addListView(int index) {

        if (mLinearLayout.getChildCount() > index) {
            return;
        }

        ListView listView = new ListView(this);

        ListView.LayoutParams params = new ListView.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(params);
        mLinearLayout.addView(listView);
        mViewList.clear();

        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {

            mViewList.add((ListView) mLinearLayout.getChildAt(i));
        }
        initListener();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.et);
        mButton = (Button) findViewById(R.id.bt);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                mPw.showAsDropDown(mButton);
                break;
        }
    }

    /**
     * @param parent   listview
     * @param view     当前被点击的条目view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        for (int i = 0; i < mAdapterList.size(); i++) {
            if (parent.getAdapter().toString().equals(mAdapterList.get(i).toString())) {
                updateListview(i, position);
            }
        }
    }

    private void updateListview(int index, int position) {

        DeptBean deptBean = (DeptBean) mViewList.get(index).getItemAtPosition(position);
        if (mIntegerMap.size() > index) {
            if (mIntegerMap.get(index) != -1) {
                mListMap.get(index).get(mIntegerMap.get(index)).isCheck = false;
            }
        }
        mIntegerMap.put(index, position);

        if (!deptBean.isCheck) {
            deptBean.isCheck = true;
            mAdapterList.get(index).notifyDataSetChanged();
        }

        addListView(index + 1);

        //初始化下一个listview
        initNextListView(deptBean, index + 1);

        mIntegerMap.put(index + 1, -1);

        //清除后面的listview
        if (mAdapterList.size() > index + 2) {
            for (int i = 0; i < mAdapterList.size(); i++) {
                if (i >= index + 2) {
                    if (mAdapterList.get(i) != null) {
                        List<DeptBean> areaBeen = new ArrayList<>();
                        mListMap.put(i, areaBeen);
                        mAdapterList.set(i, new DeptAdapter(MainActivity.this, mListMap.get(i)));
                        mViewList.get(i).setAdapter(mAdapterList.get(i));
                    }
                }
            }
        }

        dept = deptBean.name;
        mEditText.setText(dept);
    }


    /**
     * 初始化下一个listview
     *
     * @param deptBean
     * @param index
     */
    private void initNextListView(DeptBean deptBean, int index) {
        getNextData(deptBean, index);
        if (mAdapterList.size() > index) {
            mAdapterList.set(index, new DeptAdapter(MainActivity.this, mListMap.get(index)));
        } else {
            mAdapterList.add(index, new DeptAdapter(MainActivity.this, mListMap.get(index)));
        }

        mViewList.get(index).setAdapter(mAdapterList.get(index));
    }

    /**
     * 获取下一条数据
     *
     * @param deptBean
     * @param index
     */
    private void getNextData(DeptBean deptBean, int index) {
        List<DeptBean> mAreaBeanList = new ArrayList<>();
        int num = new Random().nextInt(20);
        mListMap.put(index, mAreaBeanList);
        for (int i = 0; i < num; i++) {
            mAreaBeanList.add(new DeptBean(i + "", "子部门" + i));
        }
        mListMap.put(index, mAreaBeanList);
    }
}
