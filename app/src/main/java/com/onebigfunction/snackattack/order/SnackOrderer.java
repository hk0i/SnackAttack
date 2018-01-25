package com.onebigfunction.snackattack.order;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.onebigfunction.snackattack.core.Snack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to handle ordering snack items.
 * Intentionally package private.
 *
 * Created by gmcquillan on 1/24/18.
 */

final class SnackOrderer implements SnackOrderProtocol {
    private static final String TAG = SnackOrderer.class.getSimpleName();

    @NonNull
    private List<Snack> mOrderItems;

    @Nullable
    private final SnackOrderListener mSnackOrderListener;

    /**
     * Interface for listening in on snack order events
     */
    interface SnackOrderListener {

        void onNewOrder();

        /**
         * Triggered when a snack is added to an order
         * @param snack the snack that was added.
         * @param fullOrder the entire order, including the new snack
         */
        void onSnackAdded(Snack snack, List<Snack> fullOrder);

        /**
         * Triggered when a snack is removed from an order
         * @param snack the snack that was removed
         * @param fullOrder the entire order, minus the snack that was removed.
         */
        void onSnackRemoved(Snack snack, List<Snack> fullOrder);
    }

    SnackOrderer(@Nullable final SnackOrderListener snackOrderListener) {
        mOrderItems = new ArrayList<>();
        mSnackOrderListener = snackOrderListener;
    }

    // TODO: possibly move SnackOrderProtocol to separate object.

    @Override
    public void newOrder() {
        mOrderItems = new ArrayList<>();

        if (mSnackOrderListener != null) mSnackOrderListener.onNewOrder();
    }

    /**
     * Adds a snack to the list of items to order
     *
     * @param snack the snack to add to the order.
     */
    @Override
    public void addSnackToOrder(@NonNull final Snack snack) {
        Log.d(TAG, "Adding " + snack.toString() + " to order");
        mOrderItems.add(snack);

        if (mSnackOrderListener != null) mSnackOrderListener.onSnackAdded(snack, mOrderItems);
    }

    /**
     * Removes the given {@code snack} from the list.
     * Operation performs in O(n) time complexity.
     *
     * @param snack snack to find and remove from the list.
     */
    @Override
    public void removeSnackFromOrder(@NonNull Snack snack) {
        Log.d(TAG, "Removing " + snack.toString() + " from order");
        mOrderItems.remove(snack);

        if (mSnackOrderListener != null) mSnackOrderListener.onSnackRemoved(snack, mOrderItems);
    }

    @Override
    public boolean snackOrderContains(@NonNull Snack snack) {
        return mOrderItems.contains(snack);
    }

    /**
     * Returns an immutable copy of the ordered items.
     *
     * @return an immutable copy of the snack order line items.
     */
    @Override
    public List<Snack> getOrder() {
        return Collections.unmodifiableList(mOrderItems);
    }
}
