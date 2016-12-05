package com.hcmut.social.provider;

import com.squareup.otto.Bus;

/**
 * Created by John on 11/26/2016.
 */

public class BusProvider {
    private static final Bus BUS = new Bus();


    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
