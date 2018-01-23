package com.onebigfunction.snackattack.order;

import android.support.annotation.NonNull;

import com.onebigfunction.snackattack.core.Snack;

import java.util.List;

/**
 * Handles all user-facing text transformations for the {@link OrderConfirmationActivity}.
 *
 * Intentionally package private.
 * Not designed for inheritance.
 *
 * Created by gmcquillan on 1/22/18.
 */

final class OrderConfirmationPresenter {
    @NonNull
    private final List<Snack> mOrderList;

    OrderConfirmationPresenter(@NonNull final List<Snack> orderList) {
        mOrderList = orderList;
    }

    String getOrderString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Snack snack : mOrderList) {
            stringBuilder.append("• ")
                    .append(snack.getName())
                    .append("\n");
        }

        return stringBuilder.toString();
    }
}
