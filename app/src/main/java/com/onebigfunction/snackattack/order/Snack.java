package com.onebigfunction.snackattack.order;

import android.support.annotation.NonNull;

/**
 * A class that represents a snack item.
 * Cannot be inherited.
 *
 * Created by gmcquillan on 1/21/18.
 */

final class Snack {
    private final String mName;
    private final String mDescription;

    // boolean for now. If we added other flags later such as veagan or meat, we could either add more boolean
    // properties or convert this to an integer and use bitmasks while, leaving the public interface as a boolean
    private final boolean mIsVeggie;

    /**
     * Creates a Snack given a {@code name}, {@code description}, and whether or not the snack is vegetarian ({@code
     * isVeggie}).
     *
     * @param name The display name of this snack, for example "Apple"
     *
     * @param description A description of this snack, for example, "A succulent golden delicious Apple, grown in
     *                    Washington State"
     *
     * @param isVeggie {@code true} if this dish is a vegetarian dish, {@code false} if it cannot be considered
     *                             vegetarian.
     */
    public Snack(@NonNull final String name,
                 @NonNull final String description,
                 final boolean isVeggie) {
        mName = name;
        mDescription = description;
        mIsVeggie = isVeggie;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isVeggie() {
        return mIsVeggie;
    }
}
