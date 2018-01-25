package com.onebigfunction.snackattack.order.snackview;

import android.support.annotation.NonNull;

import com.onebigfunction.snackattack.core.Snack;

/**
 * Represents a view holder that can bind to a {@link Snack} object.
 * Created by gmcquillan on 1/21/18.
 */

public interface SnackViewHolder {
    /**
     * Binds the given {@code snack} to its viewholder.
     *
     * @param snack a snack to bind
     * @param isInCart holds {@code true} if the snack should be represented as part of an order, {@code false} if the
     *                  snack is not part of an order. This is used to reciprocate on the UI whether or not an item is
     *                  in the shopping cart
     */
    void bind(@NonNull final Snack snack, final boolean isInCart);
}
