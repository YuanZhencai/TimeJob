package com.wcs.activemq;

import java.text.DecimalFormat;

import javax.jms.MapMessage;
import javax.jms.Message;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

public class Sender {
    private static Logger logger = Logger.getLogger(Sender.class);

    public static void sendEmail(Message message) {
        MapMessage map = (MapMessage) message;
        String stock;
        try {
//            stock = map.getString("stock");
//            double price = map.getDouble("price");
//            double offer = map.getDouble("offer");
//            boolean up = map.getBoolean("up");
//            DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
//            String body = stock + "\t" + df.format(price) + "\t" + df.format(offer) + "\t" + (up ? "up" : "down");
//            HtmlEmail email = new HtmlEmail();
//            email.setHostName("mail.wcs-global.com");
//            email.setAuthentication("yuanzhencai", "wcsglobal2012");
//            email.addTo("yuanzhencai@wcs-global.com");
//            email.setFrom("yuanzhencai@wcs-global.com");
//            email.setSubject("subject");
//            email.setCharset("GB2312");
//            email.setHtmlMsg(body);
//            email.send();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param args
     */
    public static void main(String[] args) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.wcs-global.com");
        email.setAuthentication("yuanzhencai", "wcsglobal2012");
        try {
            email.addTo("yuanzhencai@wcs-global.com");
            email.setFrom("yuanzhencai@wcs-global.com");
            email.setSubject("subject");
            email.setCharset("GB2312");
            email.setHtmlMsg("body");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

}
