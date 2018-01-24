package com.onebigfunction.snackattack.addsnack;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.MenuAssistant;
import com.onebigfunction.snackattack.order.OrderActivity;


public final class AddSnackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_snack);
    }

    /**
     * Factory method to create an intent for this activity.
     * @param context context used to create activity
     * @return an intent that can be used to start this activity.
     */
    public static Intent createIntent(@NonNull final Context context) {
        return new Intent(context, AddSnackActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuAssistant.bootstrapMenu(this, menu, R.menu.menu_add_snack_activity);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel_action:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
