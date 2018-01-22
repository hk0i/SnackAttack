package com.onebigfunction.snackattack.order;

import android.support.annotation.NonNull;

/**
 * Represents a view holder that can bind to a {@link Snack} object.
 * Intentionally package private since no other package requires SnackViewHolders.
 * Created by gmcquillan on 1/21/18.
 */

interface SnackViewHolder {
    void bind(@NonNull final Snack snack);
}
