package com.onebigfunction.snackattack.order;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Interface to provide list filtering using bitmasking.
 *
 * Created by gmcquillan on 1/22/18.
 */

interface SnackFilterProtocol {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FILTER_TYPE_IS_VEGGIE, FILTER_TYPE_IS_NONVEGGIE})
    @interface FilterType {}

    int FILTER_TYPE_IS_VEGGIE = 1 << 1;
    int FILTER_TYPE_IS_NONVEGGIE = 1 << 2;

    void displayTypesMatching(@FilterType final int filterType);
}
