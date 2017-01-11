package cn.ucai.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter.R;


/**
 * Created by LCH on 2017/1/11.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;

    public FooterViewHolder(View itemView) {
        super(itemView);
        tvFooter = (TextView) itemView.findViewById(R.id.tvfooter);
    }
}
