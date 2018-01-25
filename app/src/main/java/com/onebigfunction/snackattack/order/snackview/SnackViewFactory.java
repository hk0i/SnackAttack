package com.onebigfunction.snackattack.order.snackview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.order.SnackOrderProtocol;

/**
 * Factory class designed to inflate snack list item views and create their view holders.
 *
 * Not designed for inheritance.
 *
 * Created by gmcquillan on 1/24/18.
 */

public final class SnackViewFactory {
    public enum SnackViewType {
        LIST_ITEM,
        CARD_VIEW
    }

    public static RecyclerView.ViewHolder createHolder(@NonNull final ViewGroup root,
                                                       @NonNull final SnackViewType snackViewType,
                                                       @NonNull final SnackOrderProtocol snackOrder) {
        final Context context = root.getContext();

        switch (snackViewType) {
            case LIST_ITEM:
                final View snackListItemView = LayoutInflater.from(context).inflate(R.layout.snack_list_item,
                        root, false);
                return new SimpleLineItemViewHolder(context, snackListItemView, snackOrder);

            case CARD_VIEW:
                final View snackListCardView = LayoutInflater.from(context).inflate(R.layout.snack_list_card_item,
                        root, false);
                return new CardSnackViewHolder(snackListCardView, snackOrder);

            default:
                throw new AssertionError("No view/holder pair set for SnackViewType: " + snackViewType.toString());
        }
    }
}
