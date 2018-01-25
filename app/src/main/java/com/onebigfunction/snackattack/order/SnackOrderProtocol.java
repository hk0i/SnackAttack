package com.onebigfunction.snackattack.order;

import android.support.annotation.NonNull;

import com.onebigfunction.snackattack.core.Snack;

/**
 * Defines a protocol by which snacks can be ordered and viewed.
 *
 * Created by gmcquillan on 1/22/18.
 */
interface SnackOrderProtocol extends ViewOrderProtocol {
    void newOrder();
    void addSnackToOrder(@NonNull final Snack snack);
    void removeSnackFromOrder(@NonNull final Snack snack);
    boolean snackOrderContains(@NonNull final Snack snack);
}
