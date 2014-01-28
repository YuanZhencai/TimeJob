package com.wcs.connection.mongodb;

import java.net.UnknownHostException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDB {
    private static Logger logger = LoggerFactory.getLogger(MongoDB.class);

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            Mongo m = new Mongo("localhost", 27017);
            DB db = m.getDB("test");
            Set<String> tableNames = db.getCollectionNames();
            for (String tableName : tableNames) {
                logger.info("[tableName]" + tableName);
                DBCollection table = db.getCollection(tableName);
                DBCursor datas = table.find();
                for (DBObject data : datas) {
                    for (String key : data.keySet()) {
                        logger.info("    [" + key + "]" + data.get(key));
                    }
                }
            }
            DBCollection fooTable = db.getCollection("foo");
            DBObject q = new BasicDBObject("name","yuanzhencai");
            logger.info("[foo]" + fooTable.find(q).toArray());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }

}
