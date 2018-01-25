package com.onebigfunction.snackattack.order.snackview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.Snack;
import com.onebigfunction.snackattack.order.SnackOrderProtocol;

/**
 * ViewHolder for a more ornate CardView-like view.
 *
 * Not designed for inheritance.
 * Intentionally package private.
 *
 * Created by gmcquillan on 1/24/18.
 */

final class CardSnackViewHolder extends RecyclerView.ViewHolder implements SnackViewHolder {

    @NonNull private final TextView mSnackNameTextView;
    @NonNull private final CheckBox mOrderCheckBox;
    @NonNull private final TextView mSnackDescriptionTextView;
    @NonNull private final ImageView mSnackImageView;

    @NonNull final View mRootView;
    private final TextView mSnackIsVeggieTextView;

    private Snack mSnack;

    CardSnackViewHolder(@NonNull final View rootView,
                        @NonNull final SnackOrderProtocol snackOrderer) {
        super(rootView);
        // store references to all child views
        mSnackImageView = (ImageView) rootView.findViewById(R.id.snack_imageView);
        mSnackNameTextView = (TextView) rootView.findViewById(R.id.snackName_textView);
        mSnackIsVeggieTextView = (TextView) rootView.findViewById(R.id.isVeggie_textView);
        mSnackDescriptionTextView = (TextView) rootView.findViewById(R.id.snackDescription_textView);
        mOrderCheckBox = (CheckBox) rootView.findViewById(R.id.order_checkBox);
        mRootView = rootView;

        // set up event listeners
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOrderCheckBox.isChecked()) {
                    // if the box was already checked, remove the snack
                    snackOrderer.removeSnackFromOrder(mSnack);
                }
                else {
                    // if the box was not checked, add the snack
                    snackOrderer.addSnackToOrder(mSnack);
                }

                mOrderCheckBox.setChecked(!mOrderCheckBox.isChecked());
            }
        });
    }

    @Override
    public void bind(@NonNull Snack snack, boolean isInCart) {
        mSnack = snack;
        mSnackNameTextView.setText(snack.getName());
        mSnackDescriptionTextView.setText(snack.getDescription());
        mOrderCheckBox.setChecked(isInCart);

        final Context context = mRootView.getContext();

        if (snack.isVeggie()) {
            mSnackIsVeggieTextView.setText(R.string.this_meal_is_vegetarian);
            mSnackIsVeggieTextView.setTextColor(ContextCompat.getColor(context, R.color.veggie));
        }
        else {
            mSnackIsVeggieTextView.setText(R.string.this_meal_is_not_vegetarian);
            mSnackIsVeggieTextView.setTextColor(ContextCompat.getColor(context, R.color.nonVeggie));
        }

        if (mSnackImageView.getDrawable() == null) {
            @DrawableRes int backgroundImage;
            // NOTE: this is a quick hack for demonstration purposes.
            // Ideally, the actual code would read image names from a field in the `Snack` class.
            switch(snack.getName()) {
                default: // fallthrough
                case "Apple":
                    backgroundImage = R.drawable.apple;
                    break;

                case "Banana":
                    backgroundImage = R.drawable.banana;
                    break;

                case "Carrots":
                    backgroundImage = R.drawable.carrots;
                    break;

                case "Cheeseburger":
                    backgroundImage = R.drawable.cheeseburger;
                    break;

                case "French fries":
                    backgroundImage = R.drawable.fries;
                    break;

                case "Hamburger":
                    backgroundImage = R.drawable.plain_burger;
                    break;

                case "Hot dog":
                    backgroundImage = R.drawable.hotdog;
                    break;

                case "Milkshake":
                    backgroundImage = R.drawable.milkshake;
                    break;

                case "Veggieburger":
                    backgroundImage = R.drawable.quinoa_burger;
                    break;

            }

            mSnackImageView.setBackground(context.getDrawable(backgroundImage));
        }
    }
}
