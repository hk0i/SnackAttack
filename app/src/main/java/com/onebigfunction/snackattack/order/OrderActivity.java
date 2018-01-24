package com.onebigfunction.snackattack.order;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.addsnack.AddSnackActivity;
import com.onebigfunction.snackattack.core.MenuAssistant;
import com.onebigfunction.snackattack.core.ParcelableAssistant;
import com.onebigfunction.snackattack.core.Snack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Activity for placing a snack order.
 * Not designed for inheritance.
 */
public final class OrderActivity extends AppCompatActivity {

    private static final String SNACK_LIST_EXTRA = "com.onebigfunction.snackattack.order.OrderActivity.snackList";
    private static final String TAG = OrderActivity.class.getSimpleName();

    private static final int ORDER_CONFIRMATION_REQUEST = 1;
    private static final int NEW_SNACK_REQUEST = 2;

    private List<Snack> mSnackList;
    private boolean mShowVeggie = true;
    private boolean mShowNonVeggie = true;
    private SnackListAdapter mSnackListAdapter;
    private RecyclerView mSnackListRecyclerView;

    /**
     * Creates an intent for the Order screen.
     *
     * Dev note: makes use of {@link ParcelableAssistant#makeParcelableArrayList(List)}; read docs for more details.
     *
     * @param context context to use to create intent
     * @param snackList a list of snacks to display as a menu.
     * @return returns an intent to start the Order Activity.
     */
    public static Intent createIntent(@NonNull final Context context,
                                      @NonNull final List<Snack> snackList) {
        final Intent orderActivityIntent = new Intent(context, OrderActivity.class);
        orderActivityIntent.putParcelableArrayListExtra(SNACK_LIST_EXTRA,
                ParcelableAssistant.makeParcelableArrayList(snackList));

        return orderActivityIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuAssistant.bootstrapMenu(this, menu, R.menu.menu_order_activity);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_snack:
                startActivityForResult(AddSnackActivity.createIntent(this), NEW_SNACK_REQUEST);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mSnackListRecyclerView = (RecyclerView) findViewById(R.id.snackList_recyclerView);
        mSnackListRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSnackListRecyclerView.setLayoutManager(layoutManager);

        if (mSnackList == null) {
            // create the default snacklist if snacklist is empty
            Log.d(TAG, "No snack list, creating new...");
            mSnackList = createDefaultSnackList();
        }

        mSnackListAdapter = new SnackListAdapter(mSnackList);
        mSnackListRecyclerView.setAdapter(mSnackListAdapter);

        final Button placeOrderButton = (Button) findViewById(R.id.order_button);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressWarnings("UnnecessaryLocalVariable") // trying to restrict functionality of the order handler.
                final ViewOrderProtocol orderViewer = mSnackListAdapter;

                final List<Snack> order = orderViewer.getOrder();
                Log.d(TAG, "Submitting order: " + Arrays.toString(order.toArray()));

                startActivityForResult(OrderConfirmationActivity.createIntent(OrderActivity.this, order),
                        ORDER_CONFIRMATION_REQUEST);
            }
        });

        ((CheckBox) findViewById(R.id.isVeggie_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean shouldShowVeggie) {
                mShowVeggie = shouldShowVeggie;
                updateSnackFilter();
            }
        });

        ((CheckBox) findViewById(R.id.isNonVeggie_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean shouldShowNonVeggie) {
                mShowNonVeggie = shouldShowNonVeggie;
                updateSnackFilter();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSnackListAdapter = new SnackListAdapter(mSnackList);
        mSnackListRecyclerView.setAdapter(mSnackListAdapter);
    }

    private void updateSnackFilter() {
        @SnackFilterProtocol.FilterType int snackFilter = 0;
        if (mShowNonVeggie) snackFilter |= SnackFilterProtocol.FILTER_TYPE_IS_NONVEGGIE;
        if (mShowVeggie) snackFilter |= SnackFilterProtocol.FILTER_TYPE_IS_VEGGIE;

        mSnackListAdapter.displayTypesMatching(snackFilter);
    }

    private List<Snack> createDefaultSnackList() {
        List<Snack> defaultList = new ArrayList<>();
        defaultList.add(new Snack("Apple", "Succulent red apple, grown in Washington State", true));
        defaultList.add(new Snack("French fries", "Flavor packed seasoned french fries", true));
        defaultList.add(new Snack("Carrots", "Lovely orange carrots fresh from the farm", true));
        defaultList.add(new Snack("Banana", "Chiquita Banana, insane flavor!", true));
        defaultList.add(new Snack("Milkshake", "Home made vanilla milk shake.", true));

        defaultList.add(new Snack("Cheeseburger", "Flame broiled to perfection", false));
        defaultList.add(new Snack("Hamburger", "Flame broiled to perfection", false));
        defaultList.add(new Snack("Hot dog", "Boiled ball park style hot dog.", false));

        Collections.sort(defaultList);

        return defaultList;
    }
}
