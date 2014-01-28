package com.wcs.activemq.connection;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class MQFactory {

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param args
     */
    public static void main(String[] args) {

        //galssfish sun web
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");// host
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");// EJB　Port
//        props.setProperty("java.naming.provider.url", "http://localhost:3700");
        Connection connection = null;
        MessageProducer producer = null;
        try {
            Context context = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/ConnectionFactory");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("unsQueue");
            producer = session.createProducer(queue);
            MapMessage message = session.createMapMessage();
            message.setString("stock", "MQ JMS MESSAGE");
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != producer) {
                    producer.close();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
