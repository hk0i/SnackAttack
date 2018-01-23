package com.onebigfunction.snackattack.order;

import com.onebigfunction.snackattack.core.Snack;

import java.util.List;

/**
 * Allows us to retrieve an order of {@link Snack}s.
 * Created by gmcquillan on 1/22/18.
 */

interface ViewOrderProtocol {
    List<Snack> getOrder();
}
