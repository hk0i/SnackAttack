package com.onebigfunction.snackattack.order;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.ParcelableAssistant;

import java.util.List;

public class OrderConfirmationActivity extends AppCompatActivity {

    private static final String ORDER_ITEMS_EXTRA = "com.onebigfunction.snackattack.order.orderItems";
    private List<Snack> mOrder;

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mOrder = intent.getParcelableArrayListExtra(ORDER_ITEMS_EXTRA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        Button finishOrderButton = (Button) findViewById(R.id.finishOrder_button);
        finishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
