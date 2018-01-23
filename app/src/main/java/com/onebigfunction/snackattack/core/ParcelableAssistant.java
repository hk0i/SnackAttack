package com.onebigfunction.snackattack.core;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides a set of utility methods for parceling objects.
 *
 * Not designed for inheritance.
 *
 * Created by gmcquillan on 1/22/18.
 */

public final class ParcelableAssistant {
    /**
     * Converts the given {@code list} into an {@link ArrayList} if it is not already so that it can be passed to
     * {@link Intent#putParcelableArrayListExtra(String, ArrayList)}. If the input {@code list} is already an
     * {@link ArrayList}, no action is taken.
     *
     * Dev note: if conversion occurs this is an O(n) operation which will result in a new list occupying O(n) extra
     * space in memory. If the given {@code list} is already an {@link ArrayList} this is an O(1) operation which
     * consumes no extra memory.
     *
     * @param list the list to make parcelable.
     * @param <T> type of list
     *
     * @return an {@link ArrayList} version of the provided {@code list}.
     */
    public static <T> ArrayList<T> makeParcelableArrayList(final List<T> list) {
        if (list instanceof ArrayList) {
            return (ArrayList<T>) list;
        }

        ArrayList<T> arrayList = new ArrayList<>();
        Collections.copy(list, arrayList);

        return arrayList;
    }
}
