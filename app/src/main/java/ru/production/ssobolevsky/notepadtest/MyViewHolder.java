package ru.production.ssobolevsky.notepadtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by pro on 28.06.2018.
 */

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView mTitle;
    TextView mSubtitle;

    public MyViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mSubtitle = itemView.findViewById(R.id.tv_subtitle);
    }


}
