package com.onebigfunction.snackattack.order;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.onebigfunction.snackattack.core.Snack;
import com.onebigfunction.snackattack.order.snackview.SnackViewFactory;
import com.onebigfunction.snackattack.order.snackview.SnackViewHolder;

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
    @NonNull
    private final SnackViewFactory.SnackViewType mSnackViewType;

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

    /**
     * Creates a new {@link SnackListAdapter}.
     * @param snackList a list of snacks to display in the adapter.
     */
    SnackListAdapter(@NonNull final List<Snack> snackList,
                     @NonNull final SnackOrderProtocol snackOrderer,
                     @NonNull final SnackViewFactory.SnackViewType snackViewType) {
        mSnackList = snackList;
        mSnackOrderer = snackOrderer;
        mSnackViewType = snackViewType;
        mDisplaySnackList = mSnackList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = SnackViewFactory.createHolder(parent,
                mSnackViewType, mSnackOrderer);

        if (!(viewHolder instanceof SnackViewHolder)) {
            // strong arm the devs into using the right type of viewholder so we crash early instead of crashing
            // on a ClassCastException down below in onBindViewHolder
            throw new AssertionError("ViewHolder must conform to the SnackViewHolder interface.");
        }

        return viewHolder;
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
