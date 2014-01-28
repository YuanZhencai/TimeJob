package com.wcs.jco;

import java.util.HashMap;

import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class JCOProvider implements DestinationDataProvider {
    private HashMap<String, Properties> secureDBStorage = new HashMap<String, Properties>();
    private DestinationDataEventListener eL = null;

    public void changePropertiesForABAP_AS(String destName, Properties properties) {
        synchronized (secureDBStorage) {
            if (properties == null) {
                if (secureDBStorage.remove(destName) != null) eL.deleted(destName);
            } else {
                secureDBStorage.put(destName, properties);
                eL.updated(destName); // create or updated
            }
        }
    }

    @Override
    public Properties getDestinationProperties(String destinationName) {
        // read the destination from DB
        Properties p = secureDBStorage.get(destinationName);

        if (p != null) { return p; }

        return null;
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
        this.eL = eventListener;
    }

    @Override
    public boolean supportsEvents() {
        return true;
    }

}
