package com.onebigfunction.snackattack.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.Snack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An adapter to list snacks on screen.
 *
 * Intentionally package private since the {@code order} package is the only package that should need snack list ui
 * functionality.
 *
 * Not designed for inheritance.
 *
 * Created by gmcquillan on 1/21/18.
 */

final class SnackListAdapter extends RecyclerView.Adapter implements SnackFilterProtocol {

    private static final String TAG = SnackListAdapter.class.getSimpleName();

    @NonNull private final List<Snack> mSnackList;
    @NonNull
    private final SnackOrderProtocol mSnackOrderer;

    /**
     * This list contains the filtered result of the full list
     */
    @NonNull private List<Snack> mDisplaySnackList;

    @Override
    public void displayTypesMatching(@FilterType final int filterType) {
        final List<Snack> tmpSnackList = new ArrayList<>();
        if ((filterType & FILTER_TYPE_IS_VEGGIE) == FILTER_TYPE_IS_VEGGIE) {
            Log.d(TAG, "Adding all veggie types");

            for (Snack snack : mSnackList) {
                if (snack.isVeggie()) tmpSnackList.add(snack);
            }
        }

        if ((filterType & FILTER_TYPE_IS_NONVEGGIE) == FILTER_TYPE_IS_NONVEGGIE) {
            Log.d(TAG, "Adding all non-veggie types");

            for (Snack snack : mSnackList) {
                if (!snack.isVeggie()) tmpSnackList.add(snack);
            }
        }

        // alphabetize snacks
        Collections.sort(tmpSnackList);
        mDisplaySnackList = tmpSnackList;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements SnackViewHolder {
        @NonNull private final CheckBox mOrderCheckBox;
        @NonNull private final TextView mMealTypeTextView;
        @NonNull private final Context mContext;
        private Snack mSnack;

        /**
         * Creates a view holder for a snack list item.
         *
         * @param context context to be used for fetching colors and things
         * @param itemView the encapsulating view that holds our list item's subviews
         * @param snackOrderer the {@link SnackOrderProtocol} object to handle placing orders.
         *                     Dev note: orderer is absolutely not a real world.
         */
        private ViewHolder(@NonNull final Context context,
                           @NonNull final View itemView,
                           @NonNull final SnackOrderProtocol snackOrderer) {
            super(itemView);
            mOrderCheckBox = (CheckBox) itemView.findViewById(R.id.order_checkBox);
            mMealTypeTextView = (TextView) itemView.findViewById(R.id.mealType_textView);
            mContext = context;

            mOrderCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean shouldAdd) {
                    if (shouldAdd && !snackOrderer.snackOrderContains(mSnack)) {
                        // only add one of each item... if we need to add quantities later it should be a separate
                        // field, perhaps stored in a map of Snack to Quantity.
                        snackOrderer.addSnackToOrder(mSnack);
                    }
                    else if (!shouldAdd) {
                        // NOTE: explicitly checking for !shouldAdd since our previous condition also checks whether or
                        // snack is in the order.
                        snackOrderer.removeSnackFromOrder(mSnack);
                    }
                }
            });
        }

        @Override
        public void bind(@NonNull final Snack snack, final boolean isInCart) {
            mSnack = snack;
            mOrderCheckBox.setText(snack.getName());
            mOrderCheckBox.setChecked(isInCart);

            if (snack.isVeggie()) {
                mMealTypeTextView.setText(R.string.isVeggie_label);
                mMealTypeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.veggie));
            }
            else {
                mMealTypeTextView.setText(R.string.isNonVeggie_label);
                mMealTypeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.nonVeggie));
            }
        }
    }

    /**
     * Creates a new {@link SnackListAdapter}.
     * @param snackList a list of snacks to display in the adapter.
     */
    SnackListAdapter(@NonNull final List<Snack> snackList,
                     @NonNull final SnackOrderProtocol snackOrderer) {
        mSnackList = snackList;
        mSnackOrderer = snackOrderer;
        mDisplaySnackList = mSnackList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View snackListItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.snack_list_item,
                parent, false);

        return new ViewHolder(parent.getContext(), snackListItemView, mSnackOrderer);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SnackViewHolder viewHolder = (SnackViewHolder) holder;
        final Snack snack = mDisplaySnackList.get(position);

        viewHolder.bind(snack, mSnackOrderer.snackOrderContains(snack));
    }

    @Override
    public int getItemCount() {
        return mDisplaySnackList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
