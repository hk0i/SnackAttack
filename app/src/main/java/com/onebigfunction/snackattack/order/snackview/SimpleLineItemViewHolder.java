package com.onebigfunction.snackattack.order.snackview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.Snack;
import com.onebigfunction.snackattack.order.SnackOrderProtocol;

/**
 * ViewHolder for a simple snack line item with a name, checkbox and veggie status.
 * Not designed for inheritance.
 * Intentionally package private.
 *
 * Created by gmcquillan on 1/24/18.
 */
final class SimpleLineItemViewHolder extends RecyclerView.ViewHolder implements SnackViewHolder {
    @NonNull
    private final CheckBox mOrderCheckBox;
    @NonNull
    private final TextView mMealTypeTextView;
    @NonNull
    private final Context mContext;
    private Snack mSnack;

    /**
     * Creates a view holder for a snack list item.
     *
     * @param context      context to be used for fetching colors and things
     * @param rootView     the encapsulating view that holds our list item's subviews
     * @param snackOrderer the {@link SnackOrderProtocol} object to handle placing orders.
     *                     Dev note: orderer is absolutely not a real world.
     */
    SimpleLineItemViewHolder(@NonNull final Context context,
                             @NonNull final View rootView,
                             @NonNull final SnackOrderProtocol snackOrderer) {
        super(rootView);
        mOrderCheckBox = (CheckBox) rootView.findViewById(R.id.order_checkBox);
        mMealTypeTextView = (TextView) rootView.findViewById(R.id.mealType_textView);
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
