package com.dronetech.groundcontrol.util;

import dji.sdk.Products.DJIAircraft;
import dji.sdk.base.DJIBaseProduct;

/**
 * Created by Nicolaas on 7/2/16.
 */
public class DJIApiUtil {

    public String getAircraftConnectionStatus(DJIBaseProduct product) {
        String status = null;
        boolean ret = false;
        if (product != null) {
            if (product.isConnected()) {
                //The product is connected
                status = " Connected";
                ret = true;
            } else {
                if (product instanceof DJIAircraft) {
                    DJIAircraft aircraft = (DJIAircraft) product;
                    if (aircraft.getRemoteController() != null && aircraft.getRemoteController().isConnected()) {
                        // The product is not connected, but the remote controller is connected
                        status = "only RC Connected";
                        ret = true;
                    }
                }
            }
        }
        if (!ret) {
            // The product or the remote controller are not connected.
            status ="Disconnected";
        }
        return status;
    }
}
