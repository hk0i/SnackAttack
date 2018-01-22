package com.onebigfunction.snackattack.order;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.onebigfunction.snackattack.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private static final String SNACK_LIST_EXTRA = "com.onebigfunction.snackattack.order.OrderActivity.snackList";
    private static final String TAG = OrderActivity.class.getSimpleName();
    private List<Snack> mSnackList;

    /**
     * Creates an intent for the Order screen.
     *
     * @param context context to use to create intent
     * @param snackList a list of snacks.
     *                  Note: must be an {@link ArrayList} specifically for the {@link Parcelable} implementation.
     * @return returns an intent to start the Order Activity.
     */
    public static Intent createIntent(@NonNull final Context context,
                                      @NonNull final ArrayList<Snack> snackList) {
        final Intent orderActivityIntent = new Intent(context, OrderActivity.class);
        orderActivityIntent.putParcelableArrayListExtra(SNACK_LIST_EXTRA, snackList);

        return orderActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        RecyclerView snackListRecyclerView = (RecyclerView) findViewById(R.id.snackList_recyclerView);
        snackListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        snackListRecyclerView.setLayoutManager(layoutManager);

        if (mSnackList == null) {
            // create the default snacklist if snacklist is empty
            Log.d(TAG, "No snack list, creating new...");
            mSnackList = createDefaultSnackList();
        }

        snackListRecyclerView.setAdapter(new SnackListAdapter(mSnackList));
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
