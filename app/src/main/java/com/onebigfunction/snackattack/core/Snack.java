package com.onebigfunction.snackattack.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * A class that represents a snack item.
 * Cannot be inherited.
 *
 * Created by gmcquillan on 1/21/18.
 */

public final class Snack implements Parcelable, Comparable<Snack> {
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

    private Snack(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mIsVeggie = in.readByte() != 0;
    }

    public static final Creator<Snack> CREATOR = new Creator<Snack>() {
        @Override
        public Snack createFromParcel(Parcel in) {
            return new Snack(in);
        }

        @Override
        public Snack[] newArray(int size) {
            return new Snack[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeByte((byte) (mIsVeggie ? 1 : 0));
    }

    /**
     * @return the name of the snack, i.e., "Red Apple"
     */
    public String getName() {
        return mName;
    }

    /**
     * @return a description of the snack, i.e., "A succulent red apple"
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @return {@code true} if this snack is vegetarian, {@code false} if it is not.
     */
    public boolean isVeggie() {
        return mIsVeggie;
    }

    @Override
    public int compareTo(@NonNull Snack snack) {
        return getName().compareTo(snack.getName());
    }

    @Override
    public String toString() {
        return "Snack (" + this.hashCode() + ") {\n"
                + "\tname: " + getName() + "\n"
                + "\tdescription: " + getDescription() + "\n"
                + "\tisVeggie: " + isVeggie() + "\n"
                + "}";
    }
}
