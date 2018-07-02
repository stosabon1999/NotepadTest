package ru.production.ssobolevsky.notepadtest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pro on 28.06.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<MyNote> mNotes = new ArrayList<>();
    private Context mContext;
    private int mColor;

    public MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mTitle.setText(mNotes.get(position).getTitle());
        if (mColor != 0) {
            holder.mTitle.setTextColor(mColor);
        }
        holder.mSubtitle.setText(mNotes.get(position).getSubtitle());
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(EditActivity.newIntent(mContext, holder.mTitle.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setData(List<MyNote> list) {
        mNotes = list;
        notifyDataSetChanged();
    }

    public void setPrefs(int color) {
        mColor = color;
        notifyDataSetChanged();
    }
}
