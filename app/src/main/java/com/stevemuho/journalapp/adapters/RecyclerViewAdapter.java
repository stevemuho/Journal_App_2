package com.stevemuho.journalapp.adapters;

/**
 * Created by muho on 01/7/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stevemuho.journalapp.R;
import com.stevemuho.journalapp.db.JournalModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<JournalModel> journalListModelList;
    private View.OnLongClickListener longClickListener;
    private SimpleDateFormat dateFormat;

    public RecyclerViewAdapter(List<JournalModel> journalListModelList, View.OnLongClickListener longClickListener) {
        this.journalListModelList = journalListModelList;
        this.longClickListener = longClickListener;

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        JournalModel journalModel = journalListModelList.get(position);
        holder.itemTextView.setText(journalModel.getItemTitle());
        holder.nameTextView.setText(journalModel.getItemDesc());
        holder.dateTextView.setText(dateFormat.format(journalModel.getDate()));
        holder.itemView.setTag(journalModel);
        holder.itemView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return journalListModelList.size();
    }

    public void addItems(List<JournalModel> bucketListModelList) {
        this.journalListModelList = bucketListModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder  {
        private TextView itemTextView;
        private TextView nameTextView;
        private TextView dateTextView;

        RecyclerViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.itemTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
            dateTextView = view.findViewById(R.id.dateTextView);

        }

    }
}