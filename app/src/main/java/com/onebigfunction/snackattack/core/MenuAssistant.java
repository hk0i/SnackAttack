package com.onebigfunction.snackattack.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuInflater;

import com.onebigfunction.snackattack.R;
import com.onebigfunction.snackattack.order.OrderActivity;

/**
 * {@link Menu} helper class to help with various menu related tasks.
 * Not designed for inheritance.
 *
 * Created by gmcquillan on 1/23/18.
 */

public final class MenuAssistant {
    public static void bootstrapMenu(@NonNull final Activity activity,
                                     @NonNull final Menu menu,
                                     @MenuRes final int menuId) {
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(menuId, menu);
        tintMenuItems(menu, activity);
    }

    public static void tintMenuItems(@NonNull final Menu menu,
                                     @NonNull final Activity orderActivity) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable icon = menu.getItem(i).getIcon();
            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTint(icon, orderActivity.getResources().getColor(R.color.textColorOnDark));
            }
        }
    }
}
