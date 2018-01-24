package com.onebigfunction.snackattack.addsnack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.core.MenuAssistant;
import com.onebigfunction.snackattack.core.Snack;
import com.onebigfunction.snackattack.order.OrderActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class AddSnackActivity extends AppCompatActivity {
    private static final String ADD_SNACK_RESULT_EXTRA = "com.onebigfunction.snackattack.addsnack.snackResult";
    private static final String TAG = AddSnackActivity.class.getSimpleName();

    private EditText mSnackNameEditText;
    private EditText mSnackDescriptionEditText;
    private RadioButton mIsVeggie;
    private View mInvalidSnackNameTextView;
    private View mInvalidSnackDescriptionTextView;

    /**
     * Factory method to create an intent for this activity.
     * @param context context used to create activity
     * @return an intent that can be used to start this activity.
     */
    public static Intent createIntent(@NonNull final Context context) {
        return new Intent(context, AddSnackActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_snack);

        mSnackNameEditText = (EditText) findViewById(R.id.snackName_editText);
        mSnackDescriptionEditText = (EditText) findViewById(R.id.snackDescription_editText);
        mIsVeggie = (RadioButton) findViewById(R.id.isVeggie_radioButton);
        mInvalidSnackNameTextView = findViewById(R.id.snackName_validationLabel);
        mInvalidSnackDescriptionTextView = findViewById(R.id.snackDescription_validationLabel);

        findViewById(R.id.add_snack_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewSnackValid()) {
                    final Intent resultIntent = new Intent();
                    resultIntent.putExtra(ADD_SNACK_RESULT_EXTRA, new Snack(mSnackNameEditText.getText().toString(),
                            mSnackDescriptionEditText.getText().toString(), mIsVeggie.isChecked()));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private boolean isNewSnackValid() {
        final String snackName = mSnackNameEditText.getText().toString();
        final String snackDescription = mSnackDescriptionEditText.getText().toString();

        boolean isValid = true;

        if (TextUtils.isEmpty(snackName)) {
            mInvalidSnackNameTextView.setVisibility(View.VISIBLE);
            isValid = false;
        }
        else {
            mInvalidSnackNameTextView.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(snackDescription)) {
            mInvalidSnackDescriptionTextView.setVisibility(View.VISIBLE);
            isValid = false;
        }
        else {
            mInvalidSnackDescriptionTextView.setVisibility(View.GONE);
        }

        return isValid;
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
