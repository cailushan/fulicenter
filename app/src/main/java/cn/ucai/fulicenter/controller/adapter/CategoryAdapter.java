package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

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
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        GroupViewHolder vh = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            vh = new GroupViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (GroupViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext, vh.ivGroupThumb, mGroupBean.get(groupPosition).getImageUrl());
        vh.tvGroupName.setText(mGroupBean.get(groupPosition).getName());
        vh.ivIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildViewHolder vh = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            vh = new ChildViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ChildViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext, vh.ivCategoryChildThumb, getChild(groupPosition, childPosition).getImageUrl());
        vh.tvCategoryChildNaem.setText(getChild(groupPosition, childPosition).getName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupBeen,
                         ArrayList<ArrayList<CategoryChildBean>> childBean) {
        mGroupBean.clear();
        mGroupBean.addAll(groupBeen);
        mChildBean.clear();
        mChildBean.addAll(childBean);
        notifyDataSetChanged();
    }


    static class GroupViewHolder {
        @BindView(R.id.ivGroupThumb)
        ImageView ivGroupThumb;
        @BindView(R.id.tvGroupName)
        TextView tvGroupName;
        @BindView(R.id.ivIndicator)
        ImageView ivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.ivCategoryChildThumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tvCategoryChildNaem)
        TextView tvCategoryChildNaem;
        @BindView(R.id.layout_category_child)
        RelativeLayout layoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
