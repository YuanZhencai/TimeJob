package com.wcs.connection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Project: TimeJob</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2013 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yuanzhencai@wcs-global.com">Yuan</a>
 */
public class DBConnection implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
    
    
    private static BasicDataSource dataSource = null;
    private static DataSourceConnectionFactory dataSourceFactory = null;
    
    private static class SingletonHolder {
        final static DBConnection INSTNACE = new DBConnection();
    }
    
    private DBConnection() {
        logger.info("DBConnection.DBConnection()");
        String driver = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://10.228.190.163:50000/Y_TIH";
        String userName = "Yuan";
        String passWord = "19900115";
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        
        dataSource.setInitialSize(10);
//        dataSource.setMaxActive(1000);
//        dataSource.setMaxIdle(20);
//        dataSource.setMinIdle(10);
        
        dataSourceFactory = new DataSourceConnectionFactory(dataSource);
        logger.info("连接数据库成功");
    }
    
    public static DBConnection getInstance() {
        return SingletonHolder.INSTNACE;
    }
    
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSourceFactory.createConnection();
        } catch (SQLException e) {
            logger.error("连接数据库失败", e);
        }
        return conn;
    }
    
    
    public static void main(String[] args) {
        try {
            Connection c1 = DBConnection.getInstance().getConnection();
            logger.info("c1:" +c1.toString());
            c1.close();
            Connection c2 = DBConnection.getInstance().getConnection();
            logger.info("c2:" +c2.toString());
            c2.close();
            Connection c3 = DBConnection.getInstance().getConnection();
            logger.info("c3:" +c3.toString());
            c3.close();
        } catch (Exception e) {
            logger.error("",e);
        }
    }

}
