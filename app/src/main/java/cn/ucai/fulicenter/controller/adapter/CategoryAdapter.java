package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBean;

    public CategoryAdapter(Context context,
                           ArrayList<ArrayList<CategoryChildBean>> childBean, ArrayList<CategoryGroupBean> groupBean) {
        mContext = context;
        mGroupBean = new ArrayList<>();
        mGroupBean.addAll(groupBean);
        mChildBean = new ArrayList<>();
        mChildBean.addAll(childBean);
    }

    ArrayList<ArrayList<CategoryChildBean>> mChildBean;


    @Override
    public int getGroupCount() {
        return mGroupBean != null ? mGroupBean.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildBean != null && mChildBean.get(groupPosition) != null ? mChildBean.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        if (mGroupBean != null && mGroupBean.get(groupPosition) != null) {
            return mGroupBean.get(groupPosition);
        }

        return null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mChildBean != null && mChildBean.get(groupPosition) != null) {
            return mChildBean.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_category_group, null);
        return inflate;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_category_child, null);
        return inflate;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
