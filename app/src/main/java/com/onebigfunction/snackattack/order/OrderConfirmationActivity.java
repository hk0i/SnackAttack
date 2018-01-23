package com.onebigfunction.snackattack.order;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.ParcelableAssistant;
import com.onebigfunction.snackattack.core.Snack;

import java.util.List;

/**
 * Activity for confirming an order that has been placed and finalizing the order.
 * Displays a receipt of the ordered items.
 *
 * Not designed for inheritance.
 */
public final class OrderConfirmationActivity extends AppCompatActivity {

    private static final String ORDER_ITEMS_EXTRA = "com.onebigfunction.snackattack.order.orderItems";
    private List<Snack> mOrderList;

    /**
     * Factory function to create an intent for the {@link OrderConfirmationActivity}.
     *
     * Dev note: makes use of {@link ParcelableAssistant#makeParcelableArrayList(List)}; read docs for more details.
     *
     * @param context context used to create the {@link Intent}.
     * @param order a list of {@link Snack} items in the order.
     *
     * @return an {@link Intent} that can be used to start an {@link OrderConfirmationActivity}.
     */
    public static Intent createIntent(@NonNull final Context context,
                                      @NonNull final List<Snack> order) {
        final Intent orderConfirmationIntent = new Intent(context, OrderConfirmationActivity.class);

        orderConfirmationIntent.putParcelableArrayListExtra(ORDER_ITEMS_EXTRA,
                ParcelableAssistant.makeParcelableArrayList(order));

        return orderConfirmationIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        final Intent intent = getIntent();
        mOrderList = intent.getParcelableArrayListExtra(ORDER_ITEMS_EXTRA);

        OrderConfirmationPresenter presenter = new OrderConfirmationPresenter(mOrderList);
        ((TextView) findViewById(R.id.orderList_textView)).setText(presenter.getOrderString());

        Button finishOrderButton = (Button) findViewById(R.id.finishOrder_button);
        finishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
