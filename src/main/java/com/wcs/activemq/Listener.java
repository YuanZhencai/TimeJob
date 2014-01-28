package com.wcs.activemq;

import java.text.DecimalFormat;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

public class Listener implements MessageListener {
    
    private static Logger logger = Logger.getLogger(Listener.class);

    public void onMessage(Message message) {
        try {
            MapMessage map = (MapMessage) message;
            String stock = map.getString("stock");
//            double price = map.getDouble("price");
//            double offer = map.getDouble("offer");
//            boolean up = map.getBoolean("up");
            DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
            logger.info("[stock]" + stock);
//            logger.info(stock + "\t" + df.format(price) + "\t" + df.format(offer) + "\t" + (up ? "up" : "down"));
            Sender.sendEmail(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
