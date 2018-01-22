package com.onebigfunction.snackattack.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.onebigfunction.snackattack.R;

import java.util.List;

/**
 * An adapter to list snacks on screen.
 * Intentionally package private since the {@code order} package is the only package that should need snack list ui
 * functionality.
 * Created by gmcquillan on 1/21/18.
 */

class SnackListAdapter extends RecyclerView.Adapter {
    @NonNull
    private final List<Snack> mSnackList;

    private static class ViewHolder extends RecyclerView.ViewHolder implements SnackViewHolder {
        @NonNull private final CheckBox mOrderCheckBox;
        @NonNull private final TextView mMealTypeTextView;
        @NonNull private final Context mContext;

        private ViewHolder(@NonNull final View itemView, @NonNull final Context context) {
            super(itemView);
            mOrderCheckBox = (CheckBox) itemView.findViewById(R.id.order_checkBox);
            mMealTypeTextView = (TextView) itemView.findViewById(R.id.mealType_textView);
            mContext = context;
        }

        @Override
        public void bind(@NonNull final Snack snack) {
            mOrderCheckBox.setText(snack.getName());
            if (snack.isVeggie()) {
                mMealTypeTextView.setText(R.string.isVeggie_label);
                // TODO: create a class to abstract getColor for compatibility or find an app compat way of getting it
                mMealTypeTextView.setTextColor(mContext.getColor(R.color.veggie));
            }
            else {
                mMealTypeTextView.setText(R.string.isNonVeggie_label);
                mMealTypeTextView.setTextColor(mContext.getColor(R.color.nonVeggie));
            }
        }
    }

    /**
     * Creates a new {@link SnackListAdapter}.
     * @param snackList a list of snacks to display in the adapter.
     */
    SnackListAdapter(@NonNull final List<Snack> snackList) {
        mSnackList = snackList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View snackListItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.snack_list_item,
                parent, false);

        return new ViewHolder(snackListItemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SnackViewHolder viewHolder = (SnackViewHolder) holder;
        viewHolder.bind(mSnackList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSnackList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
